package org.carracoo.rest.http;

import org.carracoo.rest.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHttpResponse extends RestResponse {
	public RestHttpResponse(
		RestRequest request,
		RestStatus status,
		RestHeaders headers,
		RestBody body
	) {
		super(request, status, headers, body);
	}
}
