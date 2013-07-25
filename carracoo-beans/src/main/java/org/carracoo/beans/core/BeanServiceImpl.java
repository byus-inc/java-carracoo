package org.carracoo.beans.core;


import org.carracoo.beans.*;
import org.carracoo.beans.exceptions.BeanException;
import org.carracoo.beans.exceptions.BeanValidationException;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanServiceImpl implements BeanService {

	private static final ConcurrentHashMap<Class<?>,Definition> cache = new ConcurrentHashMap<Class<?>,Definition>();
	private static final ConcurrentHashMap<Class<?>,Boolean>        beans = new ConcurrentHashMap<Class<?>,Boolean>();

	@Override
	public <T> T newBean(Class<T> type) throws BeanException {
		return getDefinition(type).create();
	}

	@Override
	public Definition getDefinition(Class<?> type) throws BeanException {
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
					cache.put(type,new Definition(type.newInstance()));
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
	public Property getProperty(Object bean,String name) throws BeanException {
		return getDefinition(bean.getClass()).getProperty(bean,name);
	}

	@Override
	public Collection<Property> getProperties(Object bean) throws BeanException {
		return getDefinition(bean.getClass()).getProperties(bean);

	}

	@Override
	public Boolean isBean(Class<?> type)  {
		if (type == null) {
			return false;
		}
		if(!beans.containsKey(type)){
			try {
				getDefinition(type);
			}catch (BeanException ex){}
		}
		return beans.get(type);
	}



	@Override
	public BeanEncoder getEncoder() throws BeanException {
		return new BeanEncoderImpl(this);
	}

	@Override
	public BeanEncoder getEncoder(Walker walker) throws BeanException {
		return new BeanEncoderImpl(this,walker);
	}

	@Override
	public BeanEncoder getEncoder(String view) throws BeanException {
		return new BeanEncoderImpl(this,new WalkerImpl(view));
	}

	@Override
	public BeanDecoder getDecoder() throws BeanException {
		return new BeanDecoderImpl(this);
	}

	@Override
	public BeanDecoder getDecoder(Walker view) throws BeanException {
		return new BeanDecoderImpl(this);
	}

	@Override
	public BeanDecoder getDecoder(String view) throws BeanException {
		return new BeanDecoderImpl(this,new WalkerImpl(view));
	}

	@Override
	public BeanValidator getValidator() throws BeanException {
		return new BeanValidatorImpl(this);
	}

	@Override
	public BeanValidator getValidator(Walker walker) throws BeanException {
		return new BeanValidatorImpl(this,walker);
	}

	@Override
	public BeanValidator getValidator(String walker) throws BeanException {
		return new BeanValidatorImpl(this,new WalkerImpl(walker));
	}
}
