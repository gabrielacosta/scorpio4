package com.scorpio4.util.io;
/*
 *   Scorpio4 - Apache Licensed
 *   Copyright (c) 2009-2014 Lee Curtis, All Rights Reserved.
 *
 *
 */

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Scorpio4 (c) 2010-2013
 * @author lee
 * Date: 12/02/13
 * Time: 6:27 PM
 * <p/>
 * This code does something useful
 */
public class StreamCopy {
	private static final Logger log = LoggerFactory.getLogger(StreamCopy.class);

	public StreamCopy(InputStream input, OutputStream output) throws IOException {
		log.debug("Copying binary stream ...");
		copy(input,output);
		input.close();
	}

    public StreamCopy(InputStream input, File output) throws IOException {
        log.debug("Streaming binary to: ${output.absolutePath}");
        FileOutputStream fos = new FileOutputStream(output);
        copy(input,fos);
        fos.close();
        input.close();
    }

	public StreamCopy(File input, OutputStream output) throws IOException {
		log.debug("Streaming binary: : ${input.absolutePath}");
		InputStream inStream = new FileInputStream(input);
		if (input.exists()) copy(inStream,output);
		inStream.close();
	}

	public static void copy( Reader input, Writer output, int buffer_size) throws IOException {
		char[] buffer = new char[buffer_size+16];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			output.flush();
		}
		output.flush();
	}

	public static void copy(InputStream input, OutputStream output) throws IOException {
		IOUtils.copy(input, output);
		output.flush();
	}

    public void process(InputStream input, OutputStream output) throws IOException {
        IOUtils.copy(input, output);
        output.flush();
    }

	public static String toString(InputStream input) throws IOException {
		return toString(input, "UTF-8");
	}

	public static String toString(InputStream inputStream, String encoding) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer, encoding);
		return writer.toString();
	}
}
