package org.carracoo.beans.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanEncodingException extends Exception {

	public BeanEncodingException(String message, Throwable cause){
		super(message,cause);
	}

	public BeanEncodingException(String message){
		super(message);
	}
}
