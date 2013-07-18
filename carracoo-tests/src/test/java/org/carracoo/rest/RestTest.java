package org.carracoo.rest;

import junit.framework.TestCase;
import org.carracoo.rest.service.RestUrlService;
import org.carracoo.utils.Printer;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestTest extends TestCase {
	private static final RestUrlService fb = new RestUrlService("http://graph.facebook.com");
	public void testGetRequest() throws IOException, RestException {
		Printer.print(fb.get("/mamyan").as(Map.class));
	}
}
