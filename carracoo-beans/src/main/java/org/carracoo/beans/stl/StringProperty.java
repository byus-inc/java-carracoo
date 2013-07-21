package org.carracoo.beans.stl;

import org.carracoo.beans.Bean;
import org.carracoo.beans.exceptions.BeanValidationException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringProperty extends ValueProperty<String> {

	@Override
	protected StringOptions options() {
		return (StringOptions) super.options();
	}

	@Override
	public void validate() throws BeanValidationException {
		super.validate();
		if(!this.empty() && options().format!=null){
			if(!options().format.matcher(get()).matches()){
				throw new BeanValidationException(String.format(
					"property %s is should match pattern '%s'",
					options().name,
					options().format.pattern()
				));
			}
		}
	}

}
