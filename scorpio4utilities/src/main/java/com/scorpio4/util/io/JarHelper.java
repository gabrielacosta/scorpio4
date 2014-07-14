package com.scorpio4.util.io;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.jar.*;
import java.net.*;

public class JarHelper {
    private static final Logger log = LoggerFactory.getLogger(JarHelper.class);

	public static File toLocalFile(URL url, File dir) throws IOException {
		String filename = url.getFile().substring(url.getFile().lastIndexOf(File.separator));
		return new File(dir, filename);
	}

	public static File toLocalDir(URL url, File dir) throws IOException {
		File file = toLocalFile(url, dir);
		return new File(file.getParentFile(), stripExtension(file.getName()) );
	}

	public static String stripExtension(String txt) {
		if (txt==null) return null;
		int ix = txt.lastIndexOf(".");
		if (ix>0) return txt.substring(0,ix);
		return txt;
	}

	public static List install(URL url, File dir) throws IOException {
		File file = toLocalFile(url, dir);
		if (!file.exists()) return extract(url, dir);
		return null;
	}

	public static List extract(URL url, File dir) throws IOException {
		File file = toLocalFile(url, dir);
		if (file.exists()) file.delete();
		else file.getParentFile().mkdirs();
		copy( url.openStream(), new FileOutputStream(file) );
		return extract(file, dir);
	}

	public static List extract(File file, File dir) throws IOException {
		File destDir = new File(file.getParentFile(), stripExtension(file.getName()) );
		destDir.mkdirs();
		return extract(new JarFile(file), destDir);
	}

	public static List extract(JarFile jarFile, File destDir) throws IOException {
		Enumeration<JarEntry> entries = jarFile.entries();
		List copied = new ArrayList();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			File file = new File(destDir, entry.getName());

			if (entry.getName().startsWith("META-INF")) {
				// do nothing
			} else if (entry.getName().startsWith(".")) {
				// do nothing
			} else if (entry.getName().equals("com")) {
				// do nothing
			} else if (entry.getName().equals("net")) {
				// do nothing
			} else if (entry.getName().equals("org")) {
				// do nothing
			} else if (entry.getName().endsWith(".class")) {
				// do nothing
			} else if (entry.isDirectory()) {
				file.mkdirs();
			} else {
				file.getParentFile().mkdirs();
				copy(jarFile.getInputStream(entry), new FileOutputStream(file));
				copied.add(file);
			}
		}
		return copied;
	}

	public static int copy(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		while ((bytesRead = is.read(buffer)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.flush();
		os.close();
		is.close();
		return bytesRead;
	}

    public static Properties loadProperties(String file) {
        try {
            Properties properties = new Properties();
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            if (in!=null) properties.load(in);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }

}
