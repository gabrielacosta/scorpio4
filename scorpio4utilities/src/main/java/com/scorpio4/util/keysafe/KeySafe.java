package com.scorpio4.util.keysafe;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * scorpio4 (c) 2013
 * Module: com.scorpio4.security.keysafe
 * @author lee
 * Date  : 3/11/2013
 * Time  : 12:21 PM
 *
 * Provides encryption and decryption of Strings or Map<String,String>
 *
 */

public class KeySafe {

    private char[] PASSWORD = null;
    private byte[] SALT = { (byte) 0xfe, (byte) 0x19, (byte) 0x32, (byte) 0x02, (byte) 0xfe, (byte) 0x91, (byte) 0x56, (byte) 0xbb };

    private SecretKeyFactory keyFactory = null;
    private SecretKey key = null;

    private KeySafe() {
    }

    public KeySafe(String pass) throws KeySafeException {
        unlock(pass);
    }

    public void unlock(String pass) throws KeySafeException {
        try {
            this.PASSWORD = pass.toCharArray();
            this.keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            this.key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
            sanityCheck();
        } catch (InvalidKeySpecException e) {
            throw new KeySafeException("Invalid Key",e);
        } catch (NoSuchAlgorithmException e) {
            throw new KeySafeException("No Algorithm",e);
        }
    }

    private void sanityCheck() throws KeySafeException {
        if (PASSWORD==null) throw new KeySafeException("Not Unlocked: No password");
        if (keyFactory==null) throw new KeySafeException("Not Unlocked: No secret key factory");
        if (key==null) throw new KeySafeException("Not Unlocked: No secret key");
    }

    public Map encrypt(Map<String,String> src) throws KeySafeException {
        return encrypt(src, false);
    }


    public Map encrypt(Map<String,String> src, boolean keysToo) throws KeySafeException {
        sanityCheck();
        Map dst = new HashMap();
        for(String key:src.keySet()) {
            dst.put( keysToo?encrypt(key):key, encrypt(src.get(key)));
        }
        return dst;
    }

    public Map decrypt(Map<Object,Object> src) throws KeySafeException {
        return decrypt(src, false);
    }

    public Map decrypt(Map<Object,Object> src, boolean keysToo) throws KeySafeException {
        sanityCheck();
        Map dst = new HashMap();
        for(Object key:src.keySet()) {
            dst.put( keysToo?decrypt(key.toString()):key, decrypt((String)src.get(key)));
        }
        return dst;
    }

    public String encrypt(String property) throws KeySafeException {
        sanityCheck();
        try {
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
        } catch (IOException e) {
            throw new KeySafeException("IO Exception",e);
        } catch (NoSuchAlgorithmException e) {
            throw new KeySafeException("No Such Algorithm Exception",e);
        } catch (InvalidKeyException e) {
            throw new KeySafeException("Invalid Key Exception",e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new KeySafeException("Invalid Algorithm Parameter Exception",e);
        } catch (NoSuchPaddingException e) {
            throw new KeySafeException("No Such Padding Exception",e);
        } catch (BadPaddingException e) {
            throw new KeySafeException("Bad Padding Exception",e);
        } catch (IllegalBlockSizeException e) {
            throw new KeySafeException("Illegal Block Size Exception",e);
        }
    }

    private String base64Encode(byte[] bytes) {
        // NB: This class is internal - should use another impl
        return new BASE64Encoder().encode(bytes);
    }

    public String decrypt(String property) throws KeySafeException {
        sanityCheck();
        try {
            Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
        } catch (IOException e) {
            throw new KeySafeException("IO Exception",e);
        } catch (NoSuchAlgorithmException e) {
            throw new KeySafeException("No Such Algorithm Exception",e);
        } catch (InvalidKeyException e) {
            throw new KeySafeException("Invalid Key Exception",e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new KeySafeException("Invalid Algorithm Parameter Exception",e);
        } catch (NoSuchPaddingException e) {
            throw new KeySafeException("No Such Padding Exception",e);
        } catch (BadPaddingException e) {
            throw new KeySafeException("Bad Padding Exception",e);
        } catch (IllegalBlockSizeException e) {
            throw new KeySafeException("Illegal Block Size Exception",e);
        }
    }

    private byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal - should use another impl
        return new BASE64Decoder().decodeBuffer(property);
    }

    public Properties load(File file) throws IOException, KeySafeException {
        Properties locked = new Properties();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            locked.store(fileOutputStream, "KeyStore: "+file.getName());
            fileOutputStream.close();
        } else {
            FileInputStream fileInputStream = new FileInputStream(file);
            locked.load(fileInputStream);
            fileInputStream.close();
        }
        Properties unlocked = new Properties();
        unlocked.putAll(decrypt(locked));
        return unlocked;
    }

}
