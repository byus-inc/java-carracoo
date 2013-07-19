package org.carracoo.seeds.mapper.property;

import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.lang.Bean;
import org.carracoo.seeds.lang.Property;
import org.carracoo.utils.ObjectUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 5/30/13
 * Time: 3:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyDecoder {
	
	public <T> T decode(SeedView view, Object value, Class<T> type) {
		return decodeValue(view, value, type);
	}

	private <T> T decodeValue(SeedView view, Object encoded, Class<T> clazz) {
		if(clazz!=null){
			if(Bean.class.isAssignableFrom(clazz)){
				return (T) decodeModel(view, encoded, clazz);
			}
			if(clazz.isArray() && Bean.class.isAssignableFrom(clazz.getComponentType())){
				return (T) decodeArray(view, encoded, clazz);
			}
		}
		return (T) encoded;		
	}
	
	private Object decodeArray(SeedView view, Object encoded, Class<?> clazz) {
		ObjectUtils.ObjectInfo info = ObjectUtils.info(encoded);
		Object target = encoded;
		if(!info.isArray()){
			target = new Object[]{encoded};
		}
		
		List<Bean> list = new ArrayList<Bean>();
		int index=0;
		for(Object item:ObjectUtils.iterable(target)){
			view.enter(index++);
			list.add(decodeModel(view,item,clazz.getComponentType()));
			view.exit();
		}
		Object arr = Array.newInstance(clazz.getComponentType(), list.size());
		list.toArray((Object[])arr);
		return arr;
	}
	
	private Bean decodeModel(SeedView view, Object value, Class<?> type) {
		try{
			if(value==null){
				return null;
			}
			if(Bean.class.isAssignableFrom(type)){
				Bean model = (Bean) type.newInstance();
				if(model.equals(model.set(view,value))){
					return model;
				}else if(value instanceof Map){
					Map map = (Map)value;
					for(Property property:model.properties()){
						if(map.containsKey(property.name())){
							view.enter(property);
							if(property.multiple()){
								List<Object> list   = (List) map.get(property.name());
								int index = 0;
								for(Object item:list){
									view.enter(index++);
									property.set(view,decodeValue(view,item,property.type()));
									view.exit();
								}
							}else{
								property.set(view,decodeValue(view,map.get(property.name()),property.type()));
							}
							view.exit();
						}
					}
					return model;
				}else{
					throw new RuntimeException("Invalid grain structure");
				}
			}else{
				throw new RuntimeException("Invalid grain class");
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
