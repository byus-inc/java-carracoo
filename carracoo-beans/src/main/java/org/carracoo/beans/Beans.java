package org.carracoo.beans;


import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.beans.lang.ValueProperty;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Beans {

	public static Class<? extends BeanFactory> FACTORY_CLASS = BeanFactoryImpl.class;
	public static Class<? extends BeanMapper>  MAPPER_CLASS  = BeanMapperImpl.class;

	private static BeanFactory factory;
	public  static BeanFactory factory(){
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

	public static BeanMapper mapper(){
		BeanMapper mapper = null;
		try {
			mapper = MAPPER_CLASS.newInstance();
			mapper.setFactory(factory());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return mapper;
	}

	public static Boolean isBean(Class<?> type){
		return factory().isBean(type);
	}

	public static  <T> T get(Class<T> type) throws BeanException {
		return factory().getInstance(type);
	}

	public static BeanDefinition definition(Class<?> type) throws BeanException {
		return factory().getDefinition(type);
	}


	public static ValueProperty property(Bean bean, String name) throws BeanException {
		return factory().getProperty(bean, name);
	}

	public static Collection<ValueProperty> properties(Bean bean) throws BeanException {
		return factory().getProperties(bean);
	}

	public static void validate(Bean bean) throws BeanValidationException {
		factory().validateBean(bean);
	}

	public static Walker walker(String string) {
		return new BeanView(string);
	}

	public static <T> T encode(Walker view, Object target) throws BeanMappingException {
		return mapper().encode(view,target);
	}

	public static <T> T decode(Walker view, Object target, Class<T> type)  throws BeanMappingException {
		return mapper().decode(view, target, type);
	}
}
