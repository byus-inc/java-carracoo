package org.carracoo.beans.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 2:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanException extends Exception {
	public BeanException(String message){
		super(message);
	}
	public BeanException(String message,Throwable cause){
		super(message, cause);
	}
}
