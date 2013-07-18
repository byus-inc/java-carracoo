package org.carracoo.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class RestRequest {

	public RestRequest(RestService service) {
		this.service    = service;
	}

	private final RestService service;

	private String      action;
	public  String      action(){
		return action;
	}
	public  RestRequest action(String value){
		this.action = value; return this;
	}



	private final RestParams    params = new RestParams();
	public  RestRequest         param(String key, Object value){
		params.put(key,value); return this;
	}
	public  RestParams          params(){
		return params;
	}

	private final RestHeaders   headers = new RestHeaders();
	public  RestRequest         header(String key, Object value){
		headers.put(key,value); return this;
	}
	public  RestHeaders         headers(){
		return headers;
	}

	public <T> T as(Class<T> type) throws IOException,RestException {
		return service.execute(this,type);
	}

}
