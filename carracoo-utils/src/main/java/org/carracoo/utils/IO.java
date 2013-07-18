/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.utils;

import java.io.*;

/**
 * @author Sergey
 */
public class IO {

	public static synchronized String readInputStream(InputStream in) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result;
		while ((result = in.read()) != -1) {
			byte b = (byte) result;
			buf.write(b);
		}
		return buf.toString();
	}

	public static synchronized byte[] readInputStreamBytes(InputStream in) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result;
		while ((result = in.read()) != -1) {
			byte b = (byte) result;
			buf.write(b);
		}
		return buf.toByteArray();
	}

	public static byte[] readSized(InputStream in) throws IOException{
		int size = readInt(in);
		byte[] bytes = new byte[size];
		ByteUtils.writeInt(bytes,0,size);
		for(int i=4;i<size;i++){
			bytes[i]=(byte)in.read();
		}
		return bytes;
	}

	public static int readInt(InputStream in) throws IOException{
		return (
			(in.read() & 0xff) +
			((in.read() & 0xff) << 8) +
			((in.read() & 0xff) << 16) +
			(in.read() << 24)
		);
	}

	public static void writeFile(InputStream stream, String path) {
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(path));
			while ((read = stream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
