package org.carracoo.rest.http;

import org.carracoo.rest.RestException;
import org.carracoo.rest.RestRequest;
import org.carracoo.utils.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHttpServiceTest {
	private static final RestHttpService http = new RestHttpService("http://local.byus.com");

	@Test
	public void testHttpRequest(){

	}

	@Test
	public void testRestHttpService() throws IOException, RestException {
		RestHttpRequest request =
			http.get("http://23.23.70.231:9200/yp/business/_search")
				.param("pretty","true")
		;

		RestHttpResponse response = request.call();
		System.out.println(response);

	}

	@Test
	public void testRestHttpServiceWithBody() throws IOException, RestException {
		RestHttpRequest request =
			http.get("http://23.23.70.231:9200/yp/business/_search")
				.param("pretty","true")
				.body("{'query':{'term':{'id':5589282}}}".replace('\'','"'))
		;

		RestHttpResponse response = request.call();
		System.out.println(response);

	}

	@Test
	public void testRestHttpServiceWithHeaders() throws IOException, RestException {
		RestHttpRequest request =
			http.get("http://23.23.70.231:9200/yp/business/_search")
				.param("pretty","true")
				.header("X-Hello-World","herder")
				.body("{'query':{'term':{'id':5589282}}}".replace('\'','"'))
		;

		RestHttpResponse response = request.call();
		System.out.println(response);

	}
}
