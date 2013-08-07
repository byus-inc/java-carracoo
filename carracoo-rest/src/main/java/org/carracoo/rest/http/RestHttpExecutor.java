package org.carracoo.rest.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.carracoo.rest.*;
import org.carracoo.utils.IO;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHttpExecutor implements RestExecutor {

	public static class HttpRequest extends HttpEntityEnclosingRequestBase {
		private final String method;
		public HttpRequest(String method){
			this.method = method;
		}
		@Override
		public String getMethod() {
			return method;
		}
	}

	private final static  Logger logger = Logger.getLogger(RestHttpExecutor.class.getSimpleName());

	final protected RestHttpService service;
	final protected RestRequest request;

	public RestHttpExecutor(RestHttpService service, RestRequest request){
		this.service = service;
		this.request = request;
	}

	@Override
	public RestResponse execute() throws RestException {
		try{
			return parseResponse(
				service.execute(parseRequest(request))
			);
		}catch(IOException ex){
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			return createResponse(
				RestStatus.CONNECTION_FAILED, new RestHeaders(),
				createResponseBody(
					RestContentType.TEXT_PLANE,
					sw.toString().getBytes()
				)
			);
		}
	}

	protected HttpRequest parseRequest(RestRequest request) {
		HttpRequest  req = new HttpRequest(request.method());
		req.setURI(URI.create(request.url()));
		req.setHeaders(parseRequestHeaders(request));
		req.setEntity(parseRequestBody(request));
		return req;
	}

	protected Header[] parseRequestHeaders(RestRequest request){
		RestHeaders headers = request.headers();
		List<Header> result = new ArrayList<Header>();
		if(headers!=null && headers.size()>0){
			for(Map.Entry<String,Object> entry : headers.entrySet()){
				String key = entry.getKey();
				Object val = entry.getValue();
				if(val instanceof List){
					List<Object> list = ((List)val);
					for(Object item:list){
						result.add(new BasicHeader(key, item.toString()));
					}
				}else{
					result.add(new BasicHeader(key, val.toString()));
				}
			}
		}
		Header[] resHeaders = new Header[result.size()];
		result.toArray(resHeaders);
		return resHeaders;
	}

	protected HttpEntity parseRequestBody(RestRequest request){
		RestBody body = request.body();
		HttpEntity result = null;
		if(body !=null){
			ByteArrayEntity entity = new ByteArrayEntity(body.bytes());
			entity.setContentType(body.type().toString());
			result = entity;
		}
		return result;
	}


	protected RestResponse parseResponse(HttpResponse res) throws RestException,IOException {

		RestStatus   status     = parseResponseStatus(res);
		RestHeaders  headers    = parseResponseHeaders(res);
		RestBody     body       = parseResponseBody(res);
		RestResponse response   = createResponse(status,headers,body);

		return response;
	}

	protected RestStatus parseResponseStatus(HttpResponse res) {
		return RestStatus.valueOf(res.getStatusLine().getStatusCode());
	}

	protected RestHeaders parseResponseHeaders(HttpResponse res){
		RestHeaders headers = new RestHeaders();
		for (Header header:res.getAllHeaders()){
			if(header.getName()!=null){
				String  key = header.getName();
				String  val = header.getValue();
				headers.put(key,val);
			}
		}
		return headers;
	}

	protected RestBody parseResponseBody(HttpResponse res) throws IOException {
		HttpEntity ent = res.getEntity();
		RestContentType contentType = RestContentType.valueOf(
			ent.getContentType().getValue()
		);
		byte[] bytes = null;
		InputStream stream = ent.getContent();
		if(stream!=null){
			bytes = IO.readInputStreamBytes(stream);
		}
		return createResponseBody(contentType,bytes);
	}

	protected RestBody createResponseBody(RestContentType contentType, byte[] contentBytes){
		if(contentType!=null){
			return new RestBody(contentType,contentBytes);
		}else {
			return null;
		}
	}

	protected RestResponse createResponse(
		RestStatus status,
		RestHeaders headers,
		RestBody body
	) throws RestException {
		return new RestHttpResponse(request,status,headers,body);
	}

}
