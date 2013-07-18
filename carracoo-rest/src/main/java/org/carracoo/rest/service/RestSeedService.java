package org.carracoo.rest.service;

import org.carracoo.rest.RestResponse;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/11/13
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestSeedService extends RestUrlService {

	private final Seeds seeds;
	private final SeedView view;

	public RestSeedService(String base, Seeds seeds, SeedView view){
		super(base);
		this.seeds = seeds;
		this.view  = view;
	}

	@Override
	protected <T> T parse(RestResponse response, Class<T> type) throws IOException {
		if(InputStream.class.isAssignableFrom(type)){
			return (T) response.stream();
		}else
		if(String.class.isAssignableFrom(type)){
			return (T) response.text();
		}else{
			return seeds.decode(view,response.text().getBytes(),type);
		}
	}
}
