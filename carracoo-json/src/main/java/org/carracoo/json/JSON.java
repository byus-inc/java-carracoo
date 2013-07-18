/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.json;

import java.io.InputStream;

/**
 *
 * @author Sergey
 */
public class JSON {

	public static <T> T decode(byte[] data) {
		return (T) new JsonDecoder().decode(data);
	}

	public static <T> T decode(String data) {
		return (T) new JsonDecoder().decode(data);
	}

	public static <T> T decode(InputStream data) {
		return (T) new JsonDecoder().decode(data);
	}

	public static byte[] encode(Object value) {
		return new JsonEncoder().encode(value);
	}
}
