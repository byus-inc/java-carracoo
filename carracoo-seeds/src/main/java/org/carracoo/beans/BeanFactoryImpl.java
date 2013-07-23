package org.carracoo.beans;

import org.carracoo.beans.stl.ValueOptions;
import org.carracoo.beans.stl.ValueProperty;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactoryImpl implements BeanFactory {

	private static final ConcurrentHashMap<Class<?>,BeanDefinition> cache = new ConcurrentHashMap<Class<?>,BeanDefinition>();


	@Override
	public <T> T getInstance(Class<T> type){
		try {
			BeanDefinition  beanDefinition  = getDefinition(type);
			T object = type.newInstance();
			if(beanDefinition.hasPartialProperties()){
				for (ValueProperty property:beanDefinition.getPartialProperties()){
					String key  = property.options().name;
					Field field = type.getField(key);
					ValueProperty target  = (ValueProperty) field.get(object);
					target.options().name = property.options().name;
				}
			}
			return object;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return null;
	}

	@Override
	public BeanDefinition getDefinition(Class<?> type) {
		if(!cache.containsKey(type)){
			try {
				cache.put(type,new BeanDefinition(type.newInstance()));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return cache.get(type);
	}

}
