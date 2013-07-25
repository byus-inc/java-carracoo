package org.carracoo.congo;

import com.mongodb.DBEncoder;
import com.mongodb.DBEncoderFactory;
import org.bson.BSONObject;
import org.bson.io.OutputBuffer;
import org.carracoo.beans.BeanEncoder;
import org.carracoo.beans.exceptions.BeanDecodingException;
import org.carracoo.beans.exceptions.BeanEncodingException;
import org.carracoo.bson.BSON;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/21/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoEncoder implements DBEncoder {

	private final BeanEncoder mapper;

	public CongoEncoder(BeanEncoder mapper){
		this.mapper = mapper;
	}

	@Override
	public int writeObject(OutputBuffer buf, BSONObject o){
		try {
			Object val = o;
			if(CongoObject.class.isAssignableFrom(o.getClass())){
				val = mapper.encode(((CongoObject)o).target());
			}
			if(Map.class.isAssignableFrom(val.getClass())){
				byte[] bytes = BSON.encode(val);
				buf.write(bytes);
				return bytes.length;
			}else{
				throw new UnsupportedOperationException(
					o.getClass()+" cant be serialized to bson"
				);
			}
		} catch (BeanEncodingException e) {
			throw new RuntimeException("Bean encoding problem",e);
		}
	}

	/**
	 * Created with IntelliJ IDEA.
	 * User: Sergey
	 * Date: 7/21/13
	 * Time: 10:06 PM
	 * To change this template use File | Settings | File Templates.
	 */
	public static class Factory implements DBEncoderFactory {

		private final BeanEncoder encoder;

		public Factory(BeanEncoder encoder){
			this.encoder = encoder;
		}

		@Override
		public DBEncoder create() {
			return new CongoEncoder(encoder);
		}
	}
}
