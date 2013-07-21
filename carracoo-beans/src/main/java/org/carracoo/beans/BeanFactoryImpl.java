package org.carracoo.beans;


import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.beans.lang.ValueProperty;

import java.util.Collection;
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
	private static final ConcurrentHashMap<Class<?>,Boolean>        beans = new ConcurrentHashMap<Class<?>,Boolean>();

	@Override
	public <T> T getInstance(Class<T> type) throws BeanException {
		return getDefinition(type).create();
	}

	@Override
	public BeanDefinition getDefinition(Class<?> type) throws BeanException {
		if(!cache.containsKey(type)){
			if(type.isAnonymousClass()){
				try {
					cache.put(type,getDefinition(type.getSuperclass()));
					beans.put(type,true);
				}catch (BeanException e) {
					beans.put(type,false);
					throw e;
				}
			}else{
				try {
					cache.put(type,new BeanDefinition(type.newInstance()));
					beans.put(type,true);
				}catch (Exception e) {
					beans.put(type,false);
					if(e instanceof BeanException){
						throw (BeanException)e;
					}else{
						throw new BeanException("invalid bean "+type,e);
					}
				}
			}
		}
		return cache.get(type);
	}

	@Override
	public ValueProperty getProperty(Object bean,String name) throws BeanException {
		return getDefinition(bean.getClass()).getProperty(bean,name);
	}

	@Override
	public Collection<ValueProperty> getProperties(Object bean) throws BeanException {
		return getDefinition(bean.getClass()).getProperties(bean);

	}

	@Override
	public Boolean isBean(Class<?> type)  {
		if(!beans.containsKey(type)){
			try {
				getDefinition(type);
			}catch (BeanException ex){}
		}
		return beans.get(type);
	}

	@Override
	public void validateBean(Bean bean) throws BeanValidationException {
		Collection<ValueProperty> properties;
		try {
			properties = getProperties(bean);
		}catch (BeanException ex){
			throw new BeanValidationException(ex.getMessage(),ex);
		}
		for (ValueProperty property:properties){
			property.validate();
		}
	}
}
