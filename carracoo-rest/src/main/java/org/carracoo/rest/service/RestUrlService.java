package org.carracoo.rest.service;

import org.carracoo.rest.RestRequest;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/11/13
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestUrlService extends RestHttpService {

	private final String base;

	public RestUrlService(String base){
		this.base       = base;
	}


	@Override
	protected URL parseUrl(RestRequest request) throws MalformedURLException {
		return new URL(base+request.action());
	}

}
