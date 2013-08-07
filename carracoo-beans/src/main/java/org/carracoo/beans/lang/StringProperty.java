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
	public static class Options extends Property.Options implements View.ValueValidator {
		public Pattern format = null;

		@Override
		public void validate(View view, Property property, int index) throws BeanValidationException {
			if(index==-1){
				super.validate(view,property,index);
			}else{
				if(this.format!=null){
					String value = (String) property.get(index);
					if(value!=null){
						if(!this.format.matcher(value).matches()){
							throw new BeanValidationException(view,
								"PROPERTY_INVALID_PATTERN",String.format(
									"property %s '%s' should match pattern '%s'",
									this.name, value, this.format.pattern()
								)
							);
						}
					}
				}
			}
		}
	}

	protected Options options() {
		return (Options) super.options;
	}


}
