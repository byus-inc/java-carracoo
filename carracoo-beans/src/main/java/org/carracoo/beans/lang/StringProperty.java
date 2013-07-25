package org.carracoo.beans.lang;

import org.carracoo.beans.Property;
import org.carracoo.beans.View;
import org.carracoo.beans.exceptions.BeanValidationException;


import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 6:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringProperty extends Property<String> {
	public static class Options extends Property.Options {
		public Pattern format = null;
	}

	protected Options options() {
		return (Options) super.options;
	}

	@Override
	public void validate(View view, Object item) throws BeanValidationException {
		super.validate(view);
		if(item!=null && options().format!=null){
			if(!options().format.matcher((String)item).matches()){
				throw new BeanValidationException(view,
					"PROPERTY_INVALID_PATTERN",String.format(
						"property %s '%s' should match pattern '%s'",
						options().name, get(), options().format.pattern()
					)
				);
			}
		}
	}
}
