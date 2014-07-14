package com.scorpio4.util.io;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */

import java.io.File;

public class FileHelper {

	public static String localize(File root, File file) {
		return toRelative(root,file);
	}

	public static String toRelative(File root, File file) {
		if (root==null || file == null || !file.getAbsolutePath().startsWith(root.getAbsolutePath())) return null;
		if (root.equals(file)) return ".";
		return file.getAbsolutePath().substring(root.getAbsolutePath().length()+1);
	}

	public static String toExtension(String txt) {
		if (txt==null) return null;
		int ix = txt.lastIndexOf(".");
		if (ix>0) return txt.substring(ix+1);
		return null;
	}

	public static String stripExtension(String txt) {
		if (txt==null) return null;
		int ix = txt.lastIndexOf(".");
		if (ix>0) return txt.substring(0,ix);
		return txt;
	}

	public static File fromQID(File root, String uuid) {
		if (uuid.startsWith("urn:")) uuid = uuid.substring(4);
		uuid = uuid.replace(":",File.separator);
		return new File(root, uuid);
	}

	public static String toQID(File root, File file) {
		return toQID(localize(root, file));
	}

	public static String toQID(String txt) {
		if (txt==null) return null;
		return stripExtension(txt).replace("\\/","::");
	}
}
