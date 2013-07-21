package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.beans.lang.ValueProperty;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanFactory {
	public Boolean isBean (Class<?> type);
	public <T> T getInstance (Class<T> type)  throws BeanException;
	public BeanDefinition getDefinition (Class<?> type)  throws BeanException;
	public ValueProperty getProperty(Object bean,String name) throws BeanException;
	public Collection<ValueProperty> getProperties (Object bean) throws BeanException;
	public void validateBean(Bean bean) throws BeanValidationException;
}
