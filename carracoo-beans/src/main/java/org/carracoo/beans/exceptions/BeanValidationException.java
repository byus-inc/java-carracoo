package org.carracoo.beans.exceptions;

import org.carracoo.beans.BeanException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanValidationException extends BeanException {

	public BeanValidationException(String message){
		super(message);
	}

	public BeanValidationException(String message,Throwable cause){
		super(message,cause);
	}

}
