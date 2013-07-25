package org.carracoo.beans;


import org.carracoo.beans.core.BeanServiceImpl;
import org.carracoo.beans.core.WalkerImpl;
import org.carracoo.beans.exceptions.BeanDecodingException;
import org.carracoo.beans.exceptions.BeanEncodingException;
import org.carracoo.beans.exceptions.BeanException;
import org.carracoo.beans.exceptions.BeanValidationException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Beans {

	public static Class<? extends BeanService> FACTORY_CLASS = BeanServiceImpl.class;

	private static BeanService factory;
	public  static BeanService factory(){
		if(factory==null || !FACTORY_CLASS.equals(factory.getClass())){
			try {
				factory = FACTORY_CLASS.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return factory;
	}

	public static Boolean isBean(Class<?> type){
		return factory().isBean(type);
	}

	public static  <T> T get(Class<T> type) throws BeanException {
		return factory().newBean(type);
	}

	public static Definition definition(Class<?> type) throws BeanException {
		return factory().getDefinition(type);
	}


	public static Property property(Object bean, String name) throws BeanException {
		return factory().getProperty(bean, name);
	}

	public static Collection<Property> properties(Object bean) throws BeanException {
		return factory().getProperties(bean);
	}

	public static void validate(Object bean) throws BeanValidationException, BeanException {
		factory().getValidator().validate(bean);
	}

	public static void validate(Walker view, Object bean) throws BeanValidationException, BeanException {
		factory().getValidator(view).validate(bean);
	}

	public static Walker walker(String string) {
		return new WalkerImpl(string);
	}

	public static <T> T encode(Walker view, Object target) throws BeanException, BeanEncodingException {
		return factory().getEncoder(view).encode(view,target);
	}

	public static <T> T decode(Walker view, Object target, Class<T> type) throws BeanException, BeanDecodingException {
		return factory().getDecoder(view).decode(target, type);
	}
}
