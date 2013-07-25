package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanException;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 7:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanService {

	public static interface Provider{
		BeanService getService();
	}

	public Boolean isBean (Class<?> type);
	public <T> T newBean(Class<T> type)  throws BeanException;
	public Definition getDefinition (Class<?> type)  throws BeanException;
	public Property getProperty(Object bean,String name) throws BeanException;

	public Collection<Property> getProperties(Object bean) throws BeanException;

	public BeanEncoder getEncoder() throws BeanException;
	public BeanEncoder getEncoder(Walker walker) throws BeanException;
	public BeanEncoder getEncoder(String view) throws BeanException;

	public BeanDecoder getDecoder() throws BeanException;
	public BeanDecoder getDecoder(Walker view) throws BeanException;
	public BeanDecoder getDecoder(String view) throws BeanException;

	public BeanValidator getValidator() throws BeanException;
	public BeanValidator getValidator(Walker walker) throws BeanException;
	public BeanValidator getValidator(String walker) throws BeanException;
}
