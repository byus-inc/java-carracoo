package org.carracoo.rest.service;

import org.carracoo.json.JSON;
import org.carracoo.rest.*;
import org.carracoo.rest.requests.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHttpService extends RestService {


	@Override
	public <T> T execute(RestRequest request,Class<T> type) throws IOException, RestException {
		return parse(call(
			parseMethod(request),
			parseUrl(request),
			parseHeaders(request),
			parseBody(request)
		),type);
	}

	protected String parseMethod(RestRequest request) throws MalformedURLException {
		if(request instanceof RestGetRequest){
			return "GET";
		}else
		if(request instanceof RestPostRequest){
			return "POST";
		}else
		if(request instanceof RestPutRequest){
			return "PUT";
		}else
		if(request instanceof RestDeleteRequest){
			return "DELETE";
		}else
		if(request instanceof RestHeadRequest){
			return "HEAD";
		}else{
			throw new UnsupportedOperationException();
		}
	}

	protected URL parseUrl(RestRequest request) throws MalformedURLException {
		return new URL(request.action());
	}

	protected RestHeaders parseHeaders(RestRequest request) throws MalformedURLException {
		return request.headers();
	}

	protected RestBody parseBody(RestRequest request) throws MalformedURLException {
		final RestBody body;
		final Object   target;
		if(request instanceof RestPostRequest){
			target = ((RestPostRequest)request).body();
		}else
		if(request instanceof RestDeleteRequest){
			target = ((RestDeleteRequest)request).body();
		}else
		if(request instanceof RestPutRequest){
			target = ((RestPutRequest)request).body();
		}else {
			target = null;
		}
		if(target != null ){
			body = serializeBody(target);
		}else{
			body = null;
		}
		return body;
	}

	protected RestBody serializeBody(Object target) throws MalformedURLException {
		if(target instanceof RestBody){
			return ((RestBody)target);
		}
		final byte[] bytes;
		final String type;
		if(target instanceof CharSequence){
			bytes = target.toString().getBytes();
			type  = "text/plain";
		}else
		if(target instanceof byte[]){
			bytes = ((byte[])target);
			type  = "application/octet-stream";
		}else{
			bytes = JSON.encode(target);
			type  = "application/json";
		}
		if(bytes!=null && bytes.length>0){
			return new RestBody(type,bytes);
		}else{
			return null;
		}
	}

	protected <T> T parse(RestResponse response, Class<T> type) throws IOException, RestException {
		if(!response.status().success){
			throw new RestException(response);
		}
		if(RestResponse.class.isAssignableFrom(type)){
			return (T) response;
		}else
		if(InputStream.class.isAssignableFrom(type)){
			return (T) response.stream();
		}else
		if(String.class.isAssignableFrom(type)){
			return (T) response.text();
		}else{

			if(response.is("application/json")){
				try{
					return JSON.decode(response.text());
				}catch (Exception ex){
					throw new RestException("Invalid json data \n"+response.text(),ex);
				}
			}else{
				return (T) response.text();
			}
		}
	}

	protected RestResponse call(String method, URL url, RestHeaders headers, RestBody body) throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		initializeConnection(con);
		initializeMethod(con,method);
		initializeHeaders(con,headers);
		initializeBody(con, body);
		finalizeConnection(con);
		return new RestResponse(con);
	}

	protected void initializeConnection(HttpURLConnection con){}
	protected void initializeMethod(HttpURLConnection con, String method) throws ProtocolException {
		con.setRequestMethod(method);
	}

	protected void initializeHeaders(HttpURLConnection con, RestHeaders headers){
		if(headers!=null && headers.size()>0){
			for(Map.Entry<String,Object> entry : headers.entrySet()){
				String key = entry.getKey();
				Object val = entry.getValue();
				if(val instanceof List){
					List<Object> list = ((List)val);
					for(Object item:list){
						con.addRequestProperty(key, item.toString());
					}
				}else{
					con.addRequestProperty(key, val.toString());
				}
			}
		}
	}

	protected void initializeBody(HttpURLConnection con, RestBody body) throws IOException {
		if(body !=null){
		    con.setDoOutput(true);
			con.setRequestProperty("Content-Type",body.contentType());
			con.setRequestProperty("Content-Length",body.contentLength());
			OutputStream out = con.getOutputStream();
			out.write(body.contentBytes());
			out.flush();
			out.close();
		}
	}

	protected void finalizeConnection(HttpURLConnection con){}
}
