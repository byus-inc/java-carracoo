package org.carracoo.elastic.requests;

import org.carracoo.elastic.ElasticClient;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 7:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexRequest extends ElasticRequest {
	public IndexRequest(ElasticClient client){
		super(client);
	}
}
