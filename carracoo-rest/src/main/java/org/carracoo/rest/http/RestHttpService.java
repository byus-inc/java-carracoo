package org.carracoo.rest.http;

import org.carracoo.rest.RestExecutor;
import org.carracoo.rest.RestRequest;
import org.carracoo.rest.RestService;
import org.carracoo.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHttpService implements RestService {

	protected final String base;

	public RestHttpService(){
		this.base = null;
	}

	public RestHttpService(String base){
		this.base = base;
	}

	@Override
	public synchronized RestExecutor executor(RestRequest request) {
		return new RestHttpExecutor(this,request);
	}

	public synchronized RestHttpRequest get(String path){
		return new RestHttpRequest(this)
			.method("GET")
			.action(getUrl(path))
		;
	}
	public synchronized RestHttpRequest post(String path){
		return new RestHttpRequest(this)
			.method("POST")
			.action(getUrl(path))
		;
	}
	public synchronized RestHttpRequest put(String path){
		return new RestHttpRequest(this)
			.method("PUT")
			.action(getUrl(path))
		;
	}
	public synchronized RestHttpRequest delete(String path){
		return new RestHttpRequest(this)
			.method("DELETE")
			.action(getUrl(path))
		;
	}
	public synchronized RestHttpRequest head(String path){
		return new RestHttpRequest(this)
			.method("HEAD")
			.action(getUrl(path))
		;
	}
	private synchronized String getUrl(String path){
		String url = path;
		if(url!=null && !(url.startsWith("http") || url.startsWith("https"))){
			if(!url.startsWith("/")){
				url = "/"+url;
			}
			url = (base + url);
		}
		return url==null?base:url;
	}
}
