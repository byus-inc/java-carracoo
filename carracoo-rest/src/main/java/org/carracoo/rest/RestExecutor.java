package org.carracoo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RestExecutor {
	public RestResponse execute() throws RestException;
}
