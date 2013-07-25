package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanDecodingException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/25/13
 * Time: 3:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanDecoder extends BeanService.Provider  {
	public <T> T decode(Object target, Walker view, Class<T> type) throws BeanDecodingException;
	public <T> T decode(Object target, Class<T> type) throws BeanDecodingException;
}
