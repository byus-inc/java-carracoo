package org.carracoo.beans.core;

import org.carracoo.beans.*;
import org.carracoo.beans.exceptions.BeanDecodingException;
import org.carracoo.beans.exceptions.BeanEncodingException;
import org.carracoo.beans.exceptions.BeanException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/25/13
 * Time: 4:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanEncoderImpl implements BeanEncoder {

	private static final Class<?> MAP_CLASS     = LinkedHashMap.class;
	private static final Class<?> ARRAY_CLASS   = ArrayList.class;

	private final BeanService service;
	private final Walker walker;

	public BeanEncoderImpl(){
		this(new BeanServiceImpl());
	}

	public BeanEncoderImpl(BeanService service){
		this(service,new WalkerImpl());
	}

	public BeanEncoderImpl(BeanService service, Walker walker){
		this.service = service;
		this.walker  = walker;
	}

	private <T> T newInstance(Class<T> type) throws BeanEncodingException {
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
				throw new BeanEncodingException("cant create and instance of "+type,e);
			}
		}
		return (T) target;
	}

	@Override
	public BeanService getService() {
		return service;
	}

	@Override
	public <T> T encode(Object target) throws BeanEncodingException{
		return (T) encodeValue(initialize(walker),target);
	}

	@Override
	public <T> T encode(Walker walker, Object target) throws BeanEncodingException {
		return (T) encodeValue(initialize(walker.clone()),target);
	}

	private Walker initialize(Walker walker){
		return walker;
	}
	private Object encodeValue(Walker walker, Object target) throws BeanEncodingException {
		if(target==null){
			return null;
		}else
		if(target instanceof View.BeanEncoder){
			Object result = ((View.BeanEncoder)target).encode(walker);
			if(result == null){
				return null;
			}else
			if(!result.equals(View.class)){
				target = result;
			}
		}

		if(target instanceof Map){
			return encodeMap(walker, target);
		}else
		if(target instanceof Iterable){
			return encodeList(walker, target);
		}else
		if(target instanceof Number || target instanceof Boolean){
			return target;
		}else
		if(service.isBean(target.getClass())){
			return encodeBean(walker, target);
		}else{
			return target.toString();
		}

	}


	private Object encodeMap(Walker walker, Object target) throws BeanEncodingException {
		Map<Object,Object> source = (Map) target;
		Map<Object,Object> map    = (Map) newInstance(target.getClass());
		for(Map.Entry<?,?> entry:source.entrySet()){
			if(entry.getKey()!=null){
				String key = entry.getKey().toString();
				Object val = entry.getValue();
				walker.enter(key);
				map.put(key,encodeValue(walker, val));
				walker.exit();
			}
		}
		return map;
	}

	private Object encodeList(Walker walker, Object target) throws BeanEncodingException {
		Iterable<Object> source = (Iterable) target;
		Collection<Object> list = (Collection) newInstance(target.getClass());
		Integer key = 0;
		for(Object val:source){
			walker.enter(key++);
			list.add(encodeValue(walker, val));
			walker.exit();
		}
		return list;
	}

	private Object encodeBean(Walker walker, Object target) throws BeanEncodingException {
		Collection<Property> properties;
		Map map = newInstance(Map.class);
		try {
			properties = service.getProperties(target);
		}catch (BeanException ex){
			throw new BeanEncodingException(ex.getMessage(),ex);
		}


		for(Property property:properties){
			if(property.empty()){
				continue;
			}

			String key;
			Object val;

			Property.Options options = property.options;
			if(options instanceof View.Key){
				key = ((View.Key)property.options).key(walker,property);
			}else{
				key = options.name;
			}

			walker.enter(key);
			if(options.multiple){
				List<Object> list = (List<Object>) newInstance(ARRAY_CLASS);
				if(options instanceof View.ValueEncoder){
					for(int i =0;i<property.all().length;i++){
						walker.enter(i);
						Object res = ((View.ValueEncoder)options).encode(walker, property, i);
						if(!View.class.equals(res)){
							list.add(res);
						}
						walker.exit();
					}
				}else{
					for(int i =0;i<property.all().length;i++){
						walker.enter(i);
						list.add(property.get(i));
						walker.exit();
					}
				}
				val = list;
			}else{
				if(options instanceof View.ValueEncoder){
					val = ((View.ValueEncoder)options).encode(walker, property, 0);
				}else{
					val = property.get();
				}
			}
			map.put(key,encodeValue(walker,val));
			walker.exit();
		}
		return map;
	}

}
