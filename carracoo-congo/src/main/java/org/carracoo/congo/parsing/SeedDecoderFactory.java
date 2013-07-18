package org.carracoo.congo.parsing;

import com.mongodb.DBDecoder;
import com.mongodb.DBDecoderFactory;
import com.mongodb.DBEncoder;
import com.mongodb.DBEncoderFactory;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;
import org.carracoo.seeds.lang.Corn;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedDecoderFactory implements DBDecoderFactory {

	private final Seeds seeds;
	private final SeedView view;
	private final Class<? extends Corn> model;

	public SeedDecoderFactory(Seeds seeds, SeedView view, Class<? extends Corn> model){
		this.seeds = seeds;
		this.view = view;
		this.model = model;
	}

	@Override
	public DBDecoder create() {
		return new SeedDecoder(seeds,view,model);
	}
}
