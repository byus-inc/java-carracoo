package org.carracoo.beans;

import org.carracoo.beans.lang.ValueOptions;
import org.carracoo.beans.lang.ValueProperty;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanMapperImpl implements BeanMapper {

	private BeanFactory factory;

	private Class<?>   mapClass;
	private Class<?>   arrayClass;

	@Override
	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	@Override
	public BeanFactory getFactory() {
		return this.factory;
	}

	private <T> T newInstance(Class<T> type) throws BeanMappingException{
		Object target;
		if(Map.class.isAssignableFrom(type) && type.isInterface()){
			type = (Class<T>) mapClass;
		}else
		if(Iterable.class.isAssignableFrom(type) && type.isInterface()){
			type = (Class<T>) arrayClass;
		}
		try {
			target = type.newInstance();
		} catch (Exception e) {
			try {
				if(Map.class.isAssignableFrom(type)){
					target = mapClass.newInstance();
				}else
				if(Iterable.class.isAssignableFrom(type)){
					target = arrayClass.newInstance();
				}else{
					throw new BeanMappingException("cant create and instance of "+type,e);
				}
			}catch (Exception ex){
				throw new BeanMappingException("cant create and instance of "+type,e);
			}
		}
		return (T) target;
	}

	public void initialize(Walker walker){
		try {
			if(!walker.has("map.class")){
				walker.param("map.class", LinkedHashMap.class.getName());
				this.mapClass   = LinkedHashMap.class;
			}else{
				this.mapClass   = Class.forName(walker.param("map.class"));
			}
			if(!walker.has("array.class")){
				walker.param("array.class",ArrayList.class.getName());
				this.arrayClass = ArrayList.class;
			}else{
				this.arrayClass = Class.forName(walker.param("array.class"));
			}
		} catch (Exception e) {
			this.mapClass   = LinkedHashMap.class;
			this.arrayClass = ArrayList.class;
		}
	}

	@Override
	public <T> T encode(Walker walker, Object target) throws BeanMappingException {
		initialize(walker);
		return (T) encodeValue(walker,target);
	}

	private Object encodeValue(Walker walker, Object target) throws BeanMappingException {
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
		if(factory.isBean(target.getClass())){
			return encodeBean(walker, target);
		}else{
			return target;
		}
	}


	private Object encodeMap(Walker walker, Object target) throws BeanMappingException {
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

	private Object encodeList(Walker walker, Object target) throws BeanMappingException {
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

	private Object encodeBean(Walker walker, Object target) throws BeanMappingException {
		Collection<ValueProperty> properties;
		Map map = newInstance(Map.class);
		try {
			properties = factory.getProperties(target);
		}catch (BeanException ex){
			throw new BeanMappingException(ex.getMessage(),ex);
		}


		for(ValueProperty property:properties){
			String key;
			Object val;

			ValueOptions options = ((ValueOptions)property.options);
			if(options instanceof View.Key){
				key = ((View.Key)property.options).key(walker,property);
			}else{
				key = options.name;
			}

			walker.enter(key);
			if(options.multiple){
				Collection<Object> list   = (Collection<Object>)newInstance(options.container);
				if(options instanceof View.Values){
					for(Object v : property){
						walker.enter(list.size());
						Object res = ((View.Values)options).get(walker, property, v);
						if(View.class.equals(res)){
							list.add(v);
						}else{
							list.add(res);
						}
						walker.exit();
					}
				}else{
					for(Object v : property){
						walker.enter(list.size());
						list.add(v);
						walker.exit();
					}
				}
				val = list;
			}else{
				if(options instanceof View.Value){
					Object res = ((View.Value)options).get(walker, property);
					if(View.class.equals(res)){
						val = property.get();
					}else{
						val = res;
					}
				}else{
					val = property.get();
				}
			}
			map.put(key,encodeValue(walker,val));
			walker.exit();
		}
		return map;
	}

	@Override
	public <T> T decode(Walker walker, Object target, Class<T> type) throws BeanMappingException {
		initialize(walker);
		return (T) decodeValue(walker,target,type);
	}

	public Object decodeValue(Walker walker, Object target, Class<?> type) throws BeanMappingException {
		if(factory.isBean(type)){
			return decodeBean(walker,target,type);
		}else{
			//TODO implement other cases
			return target;
		}
	}

	private Object decodeBean(Walker walker, Object value, Class<?> type) throws BeanMappingException{
		if(value==null){
			return null;
		}
		try {
			Object model = factory.getInstance(type);
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
				for(ValueProperty property:factory.getProperties(model)){
					ValueOptions options = (ValueOptions) property.options;
					String key = options.name;
					Object val = map.get(options.name);
					Object res = null;
					if(map.containsKey(key)){
						walker.enter(key);
						if(options.multiple){
							int index =0;
							if(val instanceof Iterable){
								List<Object> list   = newInstance(List.class);
								for(Object obj:(Iterable)val){
									walker.enter(index++);
									list.add(decodeValue(walker,obj,options.type));
									walker.exit();
								}
								res = list;
							}else{
								res = decodeValue(walker,val,options.type);
							}
						}else{
							res = decodeValue(walker,val,options.type);
						}
					}
					property.set(res);
				}
			}
			return model;
		}catch (Exception ex){
			throw new BeanMappingException("cant decode bean "+type,ex);
		}
	}


}
