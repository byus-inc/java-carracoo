package org.carracoo.beans.lang;

import org.carracoo.beans.exceptions.BeanValidationException;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringProperty extends ValueProperty<String> {
	public static class Options extends ValueProperty.Options {
		public Pattern format = null;
	}

	@Override
	protected Options options() {
		return (Options) super.options();
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
