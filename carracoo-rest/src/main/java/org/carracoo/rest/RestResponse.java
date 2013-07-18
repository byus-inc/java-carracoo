package org.carracoo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestResponse {

	public RestResponse(HttpURLConnection connection) throws IOException {
		this.status = RestStatus.valueOf(connection.getResponseCode());
		if(this.status.success){
			this.stream = connection.getInputStream();
		}else{
			this.stream = connection.getErrorStream();
		}
		headers.putAll(connection.getHeaderFields());
		contentType = contentType();
	}

	private final String contentType;
	public String contentType(){
		return contentType;
	}

	private final RestStatus status;
	public RestStatus status(){
		return status;
	}

	private final InputStream stream;
	public InputStream stream(){
		return stream;
	}

	private final RestHeaders   headers = new RestHeaders();
	public  RestResponse        header(String key, Object value){
		headers.put(key,value); return this;
	}
	public  RestHeaders         headers(){
		return headers;
	}
	private String text;
	public  String text() {
		if(text==null){
			try{
				StringBuffer buffer = new StringBuffer();
				int ch;
				while ((ch = stream.read()) != -1){
					buffer.append((char)ch);
				}
				text = buffer.toString();
			}catch (Exception ex){
				text = "";
			}
		}
		return text;
	}

	public Boolean is(String contentType) {
		return contentType.equals(contentType);
	}
}
