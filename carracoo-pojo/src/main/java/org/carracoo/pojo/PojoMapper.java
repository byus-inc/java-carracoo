package org.carracoo.pojo;

import org.carracoo.pojo.annotations.Accessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 12:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class PojoMapper {

	private static PojoMapper instance;
	public  static PojoMapper getInstance() {
		if(instance==null){
			instance = new PojoMapper();
		}
		return instance;
	}

	public Map<Class,PojoDefinition> cache = new ConcurrentHashMap<Class, PojoDefinition>();

	public PojoDefinition toDefinition(Object user) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Class clazz = user instanceof Class ? (Class) user : user.getClass();
		if(!cache.containsKey(clazz)){
			Class definitionType = PojoDefinition.class;
			if(clazz.isAnnotationPresent(Accessor.class)){
				definitionType = ((Accessor) clazz.getAnnotation(Accessor.class)).value();
			}
			if(PojoDefinition.class.isAssignableFrom(definitionType)){
				PojoDefinition definition= (PojoDefinition) definitionType.newInstance();
				Method init = PojoDefinition.class.getDeclaredMethod("init",Class.class);
				init.setAccessible(true);
				init.invoke(definition,clazz);
				init.setAccessible(false);
				cache.put(clazz,definition);
			}else{
				throw new RuntimeException("invalid bean");
			}

		}
		return cache.get(clazz);
	}



}
