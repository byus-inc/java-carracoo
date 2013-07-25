package org.carracoo.beans.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanDecodingException extends Exception {

	public BeanDecodingException(String message, Throwable cause){
		super(message,cause);
	}

	public BeanDecodingException(String message){
		super(message);
	}
}
