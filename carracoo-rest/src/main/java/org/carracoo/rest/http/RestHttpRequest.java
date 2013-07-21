package org.carracoo.rest.http;

import org.carracoo.rest.RestException;
import org.carracoo.rest.RestRequest;
import org.carracoo.rest.RestResponse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHttpRequest extends RestRequest {

	public RestHttpRequest(RestHttpService service){
		super(service);
	}

	@Override
	public RestHttpRequest action(String value) {
		return (RestHttpRequest) super.action(value);
	}

	@Override
	public RestHttpRequest method(String value) {
		return (RestHttpRequest) super.method(value);
	}

	@Override
	public RestHttpRequest param(String key, Object value) {
		return (RestHttpRequest) super.param(key, value);
	}

	@Override
	public RestHttpRequest header(String key, Object value) {
		return (RestHttpRequest) super.header(key, value);
	}

	@Override
	public RestHttpRequest body(Object value){
		return (RestHttpRequest) super.body(value);
	}

	@Override
	public RestHttpRequest body(Object value,String type){
		return (RestHttpRequest) super.body(value,type);
	}

	@Override
	public RestHttpResponse response() {
		return (RestHttpResponse) super.response();
	}

	@Override
	public RestHttpResponse call() throws RestException {
		return (RestHttpResponse) super.call();
	}
}
