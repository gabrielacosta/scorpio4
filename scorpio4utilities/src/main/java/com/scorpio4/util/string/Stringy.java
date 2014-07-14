package com.scorpio4.util.string;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Scorpio4 (c) 2010-2013
 * @author lee
 * Date: 12/02/13
 * Time: 10:57 PM
 * <p/>
 * This code does something useful
 */
public class Stringy {
	private static final Logger log = LoggerFactory.getLogger(Stringy.class);

	public static String toString(Reader reader) throws IOException {
		StringBuffer buffer = new StringBuffer();
		char[] chars = new char[1024];
		int len = 0;
		while( (len = reader.read(chars))>0) {
			buffer.append(chars, 0 , len);
		}
		return buffer.toString();
	}

	public static String toHash(String string, String algo) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algo);
			byte[] digest = md.digest(string.getBytes());
			StringBuilder stringy = new StringBuilder();
			for(byte bcode: digest) {
				stringy.append(String.format("%02x", bcode));
			}
			return stringy.toString();
		} catch (NoSuchAlgorithmException e) {
			log.debug("No "+algo+" algorithm found for: "+string);
			return null;
		}
	}
}
