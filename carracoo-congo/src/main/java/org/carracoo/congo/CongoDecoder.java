package org.carracoo.congo;

import com.mongodb.*;
import org.bson.BSONCallback;
import org.bson.BSONObject;
import org.carracoo.beans.BeanDecoder;
import org.carracoo.beans.exceptions.BeanDecodingException;
import org.carracoo.beans.exceptions.BeanException;
import org.carracoo.beans.Walker;
import org.carracoo.bson.BSON;
import org.carracoo.utils.IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/21/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoDecoder implements DBDecoder {

	private final BeanDecoder mapper;
	private final Class<?> model;

	public CongoDecoder(BeanDecoder mapper, Class<?> model){
		this.mapper = mapper;
		this.model  = model;
	}

	@Override
	public DBCallback getDBCallback(DBCollection collection) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DBObject decode(byte[] b, DBCollection collection) {
		try {
			if (model == null) {
				return new CongoObject(
					mapper.decode(BSON.decode(b), null)
				);
			} else {
				return new CongoObject(
					mapper.decode(BSON.decode(b), model),
					mapper.getService().getDefinition(model)
				);
			}
		}catch (BeanException e){
			throw new RuntimeException("Bean decoding problem",e);
		} catch (BeanDecodingException e) {
			throw new RuntimeException("Bean decoding problem",e);
		}
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

	/**
	 * Created with IntelliJ IDEA.
	 * User: Sergey
	 * Date: 7/21/13
	 * Time: 10:06 PM
	 * To change this template use File | Settings | File Templates.
	 */
	public static class Factory implements DBDecoderFactory {

		private final BeanDecoder mapper;
		private final Class<?> model;

		public Factory(BeanDecoder mapper, Class<?> model){
			this.mapper = mapper;
			this.model  = model;
		}

		@Override
		public DBDecoder create() {
			return new CongoDecoder(mapper,model);
		}
	}
}
