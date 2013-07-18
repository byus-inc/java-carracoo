package org.carracoo.congo.parsing;

import com.mongodb.*;
import org.bson.BSONCallback;
import org.bson.BSONObject;
import org.bson.io.OutputBuffer;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;
import org.carracoo.seeds.lang.Corn;
import org.carracoo.utils.IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedDecoder implements DBDecoder {

	private final Seeds seeds;
	private final SeedView view;
	private final Class<? extends Corn> model;

	public SeedDecoder(Seeds seeds, SeedView view, Class<? extends Corn> model){
		this.seeds = seeds;
		this.view = view;
		this.model = model;
	}

	@Override
	public DBCallback getDBCallback(DBCollection collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DBObject decode(byte[] b, DBCollection collection) {
		return new SeedObject(seeds.decode(view,b,model));
	}

	@Override
	public DBObject decode(InputStream in, DBCollection collection) throws IOException {
		return decode(IO.readSized(in),collection);
	}

	@Override
	public BSONObject readObject(byte[] b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BSONObject readObject(InputStream in) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int decode(byte[] b, BSONCallback callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int decode(InputStream in, BSONCallback callback) throws IOException {
		throw new UnsupportedOperationException();
	}
}
