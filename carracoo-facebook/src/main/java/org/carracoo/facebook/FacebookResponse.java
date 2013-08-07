package org.carracoo.facebook;

import org.carracoo.rest.RestBody;
import org.carracoo.rest.RestHeaders;
import org.carracoo.rest.RestRequest;
import org.carracoo.rest.RestStatus;
import org.carracoo.rest.http.RestHttpResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookResponse extends RestHttpResponse {
	public FacebookResponse(
		RestRequest request,
		RestStatus status,
		RestHeaders headers,
		RestBody body
	) {
		super(request, status, headers, body);
	}

	@Override
	public FacebookBody body() {
		return (FacebookBody) super.body();
	}
}
