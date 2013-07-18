package org.carracoo.rest;

import org.carracoo.rest.requests.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class RestService {

	abstract public <T> T execute(RestRequest request,Class<T> type) throws IOException, RestException;

	public RestGetRequest get(String path){
		return new RestGetRequest(this).action(path);
	}

	public RestPostRequest post(String path){
		return new RestPostRequest(this).action(path);
	}

	public RestPutRequest put(String path){
		return new RestPutRequest(this).action(path);
	}

	public RestDeleteRequest delete(String path){
		return new RestDeleteRequest(this).action(path);
	}

	public RestHeadRequest head(String path){
		return new RestHeadRequest(this).action(path);
	}
}
