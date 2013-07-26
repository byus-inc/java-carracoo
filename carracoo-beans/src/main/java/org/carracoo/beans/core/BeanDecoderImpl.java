package org.carracoo.beans.core;

import org.carracoo.beans.*;
import org.carracoo.beans.exceptions.BeanDecodingException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/25/13
 * Time: 4:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanDecoderImpl implements BeanDecoder {

	private static final Class<?> MAP_CLASS     = LinkedHashMap.class;
	private static final Class<?> ARRAY_CLASS   = ArrayList.class;

	private final BeanService service;
	private final Walker walker;

	public BeanDecoderImpl(){
		this(new BeanServiceImpl());
	}

	public BeanDecoderImpl(BeanService service){
		this(service,new WalkerImpl());
	}

	public BeanDecoderImpl(BeanService service, Walker walker){
		this.service = service;
		this.walker  = walker;
	}

	@Override
	public BeanService getService() {
		return service;
	}

	@Override
	public <T> T decode(Object target, Walker view, Class<T> type) throws BeanDecodingException {
		return (T) decodeValue(initialize(view),target,type);
	}

	@Override
	public <T> T decode(Object target, Class<T> type) throws BeanDecodingException {
		return decode(target, walker, type);
	}

	private Walker initialize(Walker walker){
		return walker;
	}

	private <T> T newInstance(Class<T> type) throws BeanDecodingException {
		Object target;
		if(Map.class.isAssignableFrom(type) && type.isInterface()){
			type = (Class<T>) MAP_CLASS;
		}else
		if(Iterable.class.isAssignableFrom(type) && type.isInterface()){
			type = (Class<T>) ARRAY_CLASS;
		}
		try {
			target = type.newInstance();
		} catch (Exception e) {
			try {
				if(Map.class.isAssignableFrom(type)){
					target = MAP_CLASS.newInstance();
				}else
				if(Iterable.class.isAssignableFrom(type)){
					target = ARRAY_CLASS.newInstance();
				}else{
					throw new BeanDecodingException("cant create and instance of "+type,e);
				}
			}catch (Exception ex){
				throw new BeanDecodingException("cant create and instance of "+type,e);
			}
		}
		return (T) target;
	}

	private Object decodeValue(Walker walker, Object target, Class<?> type) throws BeanDecodingException {
		if(service.isBean(type)){
			return decodeBean(walker,target,type);
		}else{
			//TODO implement other cases
			return target;
		}
	}

	private Object decodeBean(Walker walker, Object value, Class<?> type) throws BeanDecodingException {
		if(value==null){
			return null;
		}
		try {
			Object model = service.newBean(type);
			if(model instanceof View.BeanDecoder){
				Object res = ((View.BeanDecoder)model).decode(walker,value);
				if(res==null){
					return null;
				}else
				if(res.equals(View.class)){
					return model;
				}else if(res.getClass().isAssignableFrom(type)){
					return res;
				}else{
					value = res;
				}
			}

			if(value instanceof Map){
				Map map = (Map)value;
				for(Property property:service.getProperties(model)){
					Property.Options options = property.options;
					String key      = options.name;
					Object val      = map.get(options.name);
					Object res[]   = null;
					if(map.containsKey(key)){
						walker.enter(key);
						if(options.multiple){
							List<Object> list   = newInstance(List.class);
							if(val instanceof Iterable){
								int index =0;
								for(Object obj:(Iterable)val){
									walker.enter(index);
									Object itm;
									if(property.options instanceof View.ValueDecoder){
										View.ValueDecoder decoder = ((View.ValueDecoder)property.options);
										itm = decoder.decode(walker,property,obj,index);
									}else{
										itm = obj;
									}
									if(!View.class.equals(itm)){
										list.add(decodeValue(walker,itm,options.type));
									}
									walker.exit();
									index++;
								}
							}else{
								Object itm;
								if(property.options instanceof View.ValueDecoder){
									View.ValueDecoder decoder = ((View.ValueDecoder)property.options);
									itm = decoder.decode(walker,property,val,0);
								}else{
									itm = val;
								}
								if(!View.class.equals(itm)){
									list.add(decodeValue(walker,itm,options.type));
								}
							}
							res = list.toArray();
						}else{
							Object itm;
							if(property.options instanceof View.ValueDecoder){
								View.ValueDecoder decoder = ((View.ValueDecoder)property.options);
								itm = decoder.decode(walker,property,val,0);
							}else{
								itm = val;
							}
							res = new Object[]{
								decodeValue(walker,itm,options.type)
							};
						}
					}
					if(res!=null){
						property.set(res);
					}
				}
			}
			return model;
		}catch (Exception ex){
			throw new BeanDecodingException("cant decode bean "+type,ex);
		}
	}

}
