package org.carracoo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RestService {
	public RestExecutor executor(RestRequest request);
}
