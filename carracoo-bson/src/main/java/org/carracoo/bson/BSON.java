/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.bson;

/**
 *
 * @author Sergey
 */
public class BSON {
	
	public static Object decode(byte[] data) {
		return new BsonDecoder().decode(data);
	}

	public static byte[] encode(Object value) {
		return new BsonEncoder().encode(value);
	}
}
