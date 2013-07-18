package org.carracoo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/12/13
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestBody {

	private final String contentType;
	public  String contentType(){
		return contentType;
	}

	private final byte[] contentBytes;
	public  byte[] contentBytes(){
		return contentBytes;
	}

	public String contentLength(){
		return String.valueOf(contentBytes.length);
	}

	public RestBody(String contentType, byte[] contentBytes){
		this.contentType  = contentType;
		this.contentBytes = contentBytes;
	}

}
