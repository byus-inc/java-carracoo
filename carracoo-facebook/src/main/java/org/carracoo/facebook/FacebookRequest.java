package org.carracoo.facebook;

import org.carracoo.json.JSON;
import org.carracoo.rest.RestContentType;
import org.carracoo.rest.RestException;
import org.carracoo.rest.RestParams;
import org.carracoo.rest.http.RestHttpRequest;
import org.carracoo.rest.http.RestHttpResponse;
import org.carracoo.rest.http.RestHttpService;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookRequest extends RestHttpRequest {
	public FacebookRequest(FacebookClient service){
		super(service);
	}

	@Override
	public FacebookResponse call() throws RestException {
		return (FacebookResponse)super.call();
	}

	@Override
	public FacebookRequest action(String value) {
		return (FacebookRequest) super.action(value);
	}

	@Override
	public FacebookRequest method(String value) {
		return (FacebookRequest) super.method(value);
	}

	@Override
	public FacebookRequest param(String key, Object value) {
		return (FacebookRequest) super.param(key, value);
	}

	@Override
	public FacebookRequest header(String key, Object value) {
		return (FacebookRequest) super.header(key, value);
	}

	public Map toMap() throws RestException {
		FacebookResponse response = call();
		RestContentType type    = response.body().type();
		String content          = response.body().text();
		if(type.is(RestContentType.APPLICATION_JSON)){
			return JSON.decode(content);
		}else
		if(type.isText()){
			return RestParams.parse(content);
		}else{
			throw new RestException("Cant parse body to map");
		}
	}
}
