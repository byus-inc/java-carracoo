package org.carracoo.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanMappingException extends BeanException {

	public BeanMappingException(String message,Throwable cause){
		super(message,cause);
	}

	public BeanMappingException(String message){
		super(message);
	}
}
