package org.carracoo.rest.http;

import org.carracoo.rest.*;
import org.carracoo.utils.IO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
	private final static  Logger logger = Logger.getLogger(RestHttpExecutor.class.getSimpleName());

	final protected RestRequest request;
	public RestHttpExecutor(RestHttpService service, RestRequest request){
		this.request = request;
	}

	@Override
	public RestResponse execute() throws RestException {
		return call(
			parseMethod(request),
			parseUrl(request),
			parseRequestHeaders(request),
			parseBody(request)
		);
	}

	protected String parseMethod(RestRequest request) throws RestException {
		if(request.method()!=null){
			return request.method();
		}else {
			throw new RestException("method not specified");
		}
	}

	protected URL parseUrl(RestRequest request) throws RestException {
		String url = request.url();
		try {
			return new URL(url);
		}catch (MalformedURLException ex){
			throw new RestException("Invalid Url "+url,ex);
		}
	}

	protected RestHeaders parseRequestHeaders(RestRequest request) throws RestException {
		return request.headers();
	}

	protected RestBody parseBody(RestRequest request) throws RestException {
		return request.body();
	}

	protected RestResponse call(String method, URL url, RestHeaders headers, RestBody body) throws RestException {
		try{
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			initializeConnection(con);
			initializeMethod(con,method);
			initializeHeaders(con,headers);
			initializeBody(con, body);
			finalizeConnection(con);
			return parseResult(con);
		}catch(IOException ex){
			RestHeaders hdr = parseResponseHeaders(null);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			return createResponse(
				RestStatus.CONNECTION_FAILED, hdr,
				RestContentType.TEXT_PLANE,
				sw.toString().getBytes()
			);
		}
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
			con.setRequestProperty("Content-Type",body.type().toString());
			con.setRequestProperty("Content-Length",body.length());
			OutputStream out = con.getOutputStream();
			out.write(body.bytes());
			out.flush();
			out.close();
		}
	}

	protected void finalizeConnection(HttpURLConnection con){}

	protected RestResponse parseResult(HttpURLConnection con) throws RestException,IOException {

		RestStatus status = RestStatus.valueOf(con.getResponseCode());


		RestHeaders     headers          = parseResponseHeaders(con);
		RestContentType contentType      = RestContentType.valueOf(con.getContentType());
		String          contentEncoding  = con.getContentEncoding();
		Integer         contentLength    = con.getContentLength();

		if(contentEncoding!=null){
			contentType.encoding(contentEncoding);
		}
		byte[] bytes = null;

		if(contentLength != 0){
			InputStream stream  = null;
			try {
				stream = con.getInputStream();
			}catch (IOException ex){
				stream = con.getErrorStream();
			}
			try {
				if(stream!=null){
					bytes = IO.readInputStreamBytes(stream);
				}
			}catch (IOException ex){
				logger.log(Level.WARNING,"Cant read from stream",ex);
			}
		}
		return createResponse(status,headers,contentType,bytes);
	}

	protected RestHeaders parseResponseHeaders(HttpURLConnection con){
		RestHeaders headers = new RestHeaders();
		if(con==null){
			return headers;
		}
		for (Map.Entry<String,List<String>> entry:con.getHeaderFields().entrySet()){
			if(entry.getKey()!=null){
				String       key = entry.getKey();
				List<String> val = entry.getValue();
				if(val.size()>1){
					headers.put(key,val);
				}else{
					headers.put(key,val.get(0));
				}
			}
		}
		return headers;
	}

	protected RestResponse createResponse(
		RestStatus status,
		RestHeaders headers,
		RestContentType contentType,
		byte[] contentBytes
	) throws RestException {
		RestBody body =null;
		if(contentType!=null){
			body = new RestBody(contentType,contentBytes);
		}
		return new RestHttpResponse(request,status,headers,body);
	}

}
