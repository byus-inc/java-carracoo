package org.carracoo.rest;

import org.carracoo.utils.ANSI;
import org.carracoo.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class RestRequest {
	public static final Pattern PARAM = Pattern.compile("%([A-Za-z0-9]+)");
	public RestRequest(RestService service) {
		this.service    = service;
	}

	private final RestService service;

	private String      method;
	public  String      method(){
		return method;
	}
	public  RestRequest method(String value){
		this.method = value; return this;
	}

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

	private RestBody          body;
	public  RestBody          body(){
		return body;
	}

	public  RestRequest     body(Object value){
		if(value instanceof RestBody){
			this.body = (RestBody)value;
		}else{
			this.body = new RestBody(value);
		}
		return this;
	}

	public  RestRequest     body(Object value, String type){
		this.body = new RestBody(RestContentType.valueOf(type),value); return this;
	}

	private RestResponse response;
	public  RestResponse response(){
		return response;
	}

	public RestResponse call() throws RestException {
		return response = service.executor(this).execute();
	}

	public String url(){
		if(action!=null && action.length()>0){
			Matcher m = PARAM.matcher(action);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				if(params.containsKey(m.group(1))){
					m.appendReplacement(sb, StringUtils.urlEncode(params.get(m.group(1)).toString()));
				}else
				if(params.containsKey(m.group(0))){
					m.appendReplacement(sb, StringUtils.urlEncode(params.get(m.group(0)).toString()));
				}
			}
			m.appendTail(sb);

			sb.append(params.toString());

			return sb.toString();

		}else{
			return "/";
		}
	}

	public RestRequest print(){
		System.out.println(toString());
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ANSI.append(sb, method(), ANSI.Color.MAGENTA);
		sb.append(" ");
		sb.append(url());
		if(headers().size()>0){
			sb.append("\n");
			sb.append(headers());
		}
		if(body()!=null){
			sb.append("\n");
			sb.append(body());
		}
		sb.append("\n");
		return sb.toString();
	}
}
