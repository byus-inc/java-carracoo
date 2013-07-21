package org.carracoo.beans;

import org.carracoo.beans.lang.ValueProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface View {

	public interface Key {
		public String key (View view, ValueProperty property);
	}

	public interface Value {
		public Object get (View view, ValueProperty property);
	}

	public interface Values {
		public Object get (View view, ValueProperty property, Object item);
	}

	public interface BeanEncoder {
		public Object encode(View view);
	}

	public interface BeanDecoder {
		public Object decode(View view, Object value);
	}

	public String base();
	public String format();
	public String target();
	public String path();

	public boolean in(String path);
	public boolean is(String format);
	public boolean to(String target);
	public boolean has(String param);
	public boolean has(String param, String value);
	public String param(String param);
	public String param(String param,String value);
}
