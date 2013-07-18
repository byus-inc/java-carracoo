package org.carracoo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/16/13
 * Time: 3:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestException extends Exception {
	private final RestResponse response;

	public RestResponse getResponse(){
		return response;
	}

	public RestException(RestResponse response){
		super(response.text());
		this.response = response;
	}

	public RestException(String message, Throwable cause){
		super(message,cause);
		this.response = null;
	}
}
