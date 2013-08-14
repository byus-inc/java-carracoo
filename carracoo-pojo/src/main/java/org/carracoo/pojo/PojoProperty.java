package org.carracoo.pojo;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 12:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PojoProperty {

	protected final Field field = null;

	private void init(Field field) throws NoSuchFieldException, IllegalAccessException {
		Field fld = PojoProperty.class.getDeclaredField("field");
		fld.setAccessible(true);
		fld.set(this, field);
		fld.setAccessible(false);
		field.setAccessible(true);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(field.getName());
		sb.append(":");
		sb.append(field.getType().getSimpleName());
		return sb.toString();
	}

	public String name() {
		return field.getName();
	}

	public Object get(PojoDefinition definition,Object target) throws IllegalAccessException {
		return field.get(target);
	}

	public void set(PojoDefinition definition,Object target,Object value) throws IllegalAccessException {
		field.set(target,value);
	}
}
