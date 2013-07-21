package org.carracoo.congo;

import com.mongodb.DBEncoder;
import com.mongodb.DBEncoderFactory;
import org.bson.BSONObject;
import org.bson.io.OutputBuffer;
import org.carracoo.beans.BeanMapper;
import org.carracoo.beans.BeanMappingException;
import org.carracoo.beans.Walker;
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

	private final BeanMapper mapper;
	private final Walker walker;

	public CongoEncoder(BeanMapper mapper, Walker walker){
		this.mapper = mapper;
		this.walker = walker;
	}

	@Override
	public int writeObject(OutputBuffer buf, BSONObject o){
		try {
			Object val = o;
			if(CongoObject.class.isAssignableFrom(o.getClass())){
				val = mapper.encode(walker,((CongoObject)o).target());
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
		}catch (BeanMappingException e){
			throw new UnsupportedOperationException(e);
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

		private final BeanMapper mapper;
		private final Walker walker;

		public Factory(BeanMapper mapper, Walker walker){
			this.mapper = mapper;
			this.walker = walker;
		}

		@Override
		public DBEncoder create() {
			return new CongoEncoder(mapper,walker);
		}
	}
}
