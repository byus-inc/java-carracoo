package org.carracoo.elastic;

import org.carracoo.elastic.requests.ElasticRequest;
import org.carracoo.elastic.requests.IndexRequest;
import org.carracoo.elastic.requests.SearchRequest;
import org.carracoo.rest.http.RestHttpRequest;
import org.carracoo.rest.http.RestHttpService;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/18/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElasticClient extends RestHttpService {

	protected final String base;

	public ElasticClient(String base){
		this.base = base;
	}

	public SearchRequest search(){
		return new SearchRequest(this);
	}

	public IndexRequest index(){
		return new IndexRequest(this);
	}

}
