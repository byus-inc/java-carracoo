package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/25/13
 * Time: 3:42 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanEncoder extends BeanService.Provider {
	public <T> T encode(Object target) throws BeanEncodingException;
	public <T> T encode(Walker view, Object target) throws BeanEncodingException;
}
