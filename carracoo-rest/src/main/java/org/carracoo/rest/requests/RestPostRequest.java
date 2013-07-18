package org.carracoo.rest.requests;

import org.carracoo.rest.RestRequest;
import org.carracoo.rest.RestService;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestPostRequest extends RestRequest {
	public RestPostRequest(RestService service) {
		super(service);
	}

	@Override
	public RestPostRequest action(String value) {
		super.action(value);return this;
	}

	private Object          body;
	public  Object          body(){
		return body;
	}
	public  RestPostRequest body(Object value){
		this.body = value; return this;
	}

}
