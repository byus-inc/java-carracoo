package org.carracoo.elastic.requests;

import org.carracoo.elastic.ElasticClient;
import org.carracoo.rest.http.RestHttpRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 7:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class ElasticRequest extends RestHttpRequest {
	public ElasticRequest(ElasticClient client){
		super(client);
	}
}
