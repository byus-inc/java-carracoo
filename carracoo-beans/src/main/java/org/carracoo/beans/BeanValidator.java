package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanValidationException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/25/13
 * Time: 3:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BeanValidator extends BeanService.Provider {
	public void validate(Object bean) throws BeanValidationException;
	public void validate(Walker view, Object bean) throws BeanValidationException;
}
