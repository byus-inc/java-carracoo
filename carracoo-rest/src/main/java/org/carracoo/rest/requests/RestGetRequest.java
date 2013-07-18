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
public class RestGetRequest extends RestRequest {
	public RestGetRequest(RestService service) {
		super(service);
	}

	@Override
	public RestGetRequest action(String value) {
		super.action(value);return this;
	}
}
