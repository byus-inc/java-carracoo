package org.carracoo.beans;

import org.carracoo.beans.Property;
import org.carracoo.beans.exceptions.BeanValidationException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface View {

	public interface Key {
		public String key (View view, Property property);
	}

	public interface ValueValidator {
		public void validate (View view, Property property, int index) throws BeanValidationException;
	}

	public interface ValueEncoder {
		public Object encode(View view, Property property, int index);
	}

	public interface ValueDecoder {
		public Object decode (View view, Property property, Object item, int index);
	}

	public interface BeanEncoder {
		public Object encode(View view);
	}

	public interface BeanDecoder {
		public Object decode(View view, Object value);
	}

	public interface BeanValidator {
		public Object validate (View view);
	}

	public String base();
	public String format();
	public String target();
	public String path(Boolean includeArrays);
	public String path();

	public boolean in(String path);
	public boolean inRoot();
	public boolean inArray();
	public boolean is(String format);
	public boolean to(String target);
	public boolean has(String param);
	public boolean has(String param, Object value);
	public <T> T param(String param);
	public <T> T param(String param, Object value);
}
