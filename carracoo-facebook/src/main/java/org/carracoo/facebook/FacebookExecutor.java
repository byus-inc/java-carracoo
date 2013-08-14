package org.carracoo.facebook;

import org.carracoo.rest.*;
import org.carracoo.rest.http.RestHttpExecutor;
import org.carracoo.rest.http.RestHttpRequest;
import org.carracoo.rest.http.RestHttpService;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookExecutor extends RestHttpExecutor {
	public FacebookExecutor(RestHttpService service, RestRequest request){
		super(service,request);
	}

	@Override
	protected RestBody createResponseBody(RestContentType contentType, byte[] contentBytes){
		return new FacebookBody(contentType,contentBytes);
	}

	@Override
	protected RestResponse createResponse(RestStatus status, RestHeaders headers, RestBody body) throws RestException {
		return new FacebookResponse(request,status,headers,body);
	}
}
