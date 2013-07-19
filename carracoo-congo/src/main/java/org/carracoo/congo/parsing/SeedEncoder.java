package org.carracoo.congo.parsing;

import com.mongodb.DBEncoder;
import org.bson.BSONObject;
import org.bson.io.OutputBuffer;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;
import org.carracoo.seeds.lang.Bean;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedEncoder implements DBEncoder {

	private final Seeds seeds;
	private final SeedView view;
	private final Class<? extends Bean> model;

	public SeedEncoder(Seeds seeds, SeedView view, Class<? extends Bean> model){
		this.seeds = seeds;
		this.view = view;
		this.model = model;
	}

	@Override
	public int writeObject(OutputBuffer buf, BSONObject o) {
		if(SeedObject.class.isAssignableFrom(o.getClass())){
			SeedObject seed = (SeedObject)o;
			byte[] bytes = seeds.encode(view,seed.target());
			buf.write(bytes);
			return bytes.length;
		}else
		if(Map.class.isAssignableFrom(o.getClass())){
			byte[] bytes = seeds.encode(view,o);
			buf.write(bytes);
			return bytes.length;
		}else{
			throw new UnsupportedOperationException();
		}
	}
}
