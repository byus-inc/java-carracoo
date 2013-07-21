package org.carracoo.beans;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanMapper {
	public void setFactory(BeanFactory factory);
	public BeanFactory getFactory();

	public <T> T encode(Walker view, Object target) throws BeanMappingException;
	public <T> T decode(Walker view, Object target, Class<T> type) throws BeanMappingException;

}
