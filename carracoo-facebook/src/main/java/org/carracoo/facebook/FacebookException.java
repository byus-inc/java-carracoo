package org.carracoo.facebook;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookException extends Exception {

	public FacebookException(String message){
		super(message);
	}

	public FacebookException(String message,Throwable throwable){
		super(message,throwable);
	}

}

