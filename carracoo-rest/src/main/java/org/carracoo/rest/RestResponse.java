package org.carracoo.rest;

import org.carracoo.utils.ANSI;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestResponse {

	private static final Logger logger = Logger.getLogger(RestResponse.class.getSimpleName());

	public RestResponse (
		RestRequest request,
		RestStatus status,
		RestHeaders headers,
		RestBody body
	){
		this.request     = request;
		this.status      = status;
		this.body        = body;
		if(headers==null){
			this.headers = new RestHeaders(0);
		}else{
			this.headers = headers;
		}
	}

	final
	private RestRequest request;
	public  RestRequest request(){
		return request;
	}

	final
	private RestStatus status;
	public  RestStatus status(){
		return status;
	}

	final
	private RestBody body;
	public  RestBody body(){
		return body;
	}

	public  <T> T body(Class<T> type) {
		return null;
	}

	final
	private RestHeaders   headers;
	public  RestResponse  header(String key, Object value){
		headers.put(key,value); return this;
	}
	public  RestHeaders   headers(){
		return headers;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ANSI.append(sb, "REQUEST", ANSI.Color.CYAN);
		sb.append(" ").append(request);
		ANSI.append(sb, "RESPONSE", ANSI.Color.CYAN);
		sb.append(" ");
		ANSI.append(sb, status.code+" "+status.message, status.success ? ANSI.Color.GREEN:ANSI.Color.RED);
		sb.append("\n");
		sb.append(headers);
		sb.append("\n");
		sb.append(body);
		sb.append("\n");
		ANSI.append(sb, "END", ANSI.Color.CYAN);
		sb.append("\n");
		return sb.toString();    //To change body of overridden methods use File | Settings | File Templates.
	}
}
