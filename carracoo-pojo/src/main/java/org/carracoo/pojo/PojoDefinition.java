package org.carracoo.pojo;

import org.carracoo.pojo.annotations.Accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PojoDefinition {

	protected final Class clazz = null;
	protected final PojoProperty[] properties = null;

	private void init(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
		List<PojoProperty> properties = new ArrayList<PojoProperty>();
		for(Field field:clazz.getDeclaredFields()){
			Class propertyType = PojoProperty.class;
			if(field.isAnnotationPresent(Accessor.class)){
				propertyType = field.getAnnotation(Accessor.class).value();
			}
			if(PojoProperty.class.isAssignableFrom(propertyType)){
				PojoProperty property = (PojoProperty)propertyType.newInstance();

				Method init = PojoProperty.class.getDeclaredMethod("init",Field.class);
				init.setAccessible(true);
				init.invoke(property,field);
				init.setAccessible(false);

				properties.add(property);
			}else{
				throw new RuntimeException("invalid bean property");
			}
		}

		Field clazzFld      = PojoDefinition.class.getDeclaredField("clazz");
		clazzFld.setAccessible(true);
		clazzFld.set(this,clazz);
		clazzFld.setAccessible(false);

		Field propertiesFld = PojoDefinition.class.getDeclaredField("properties");
		propertiesFld.setAccessible(true);
		propertiesFld.set(this,properties.toArray(
			new PojoProperty[properties.size()]
		));
		propertiesFld.setAccessible(false);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getSimpleName());
		sb.append("\n");
		for (PojoProperty property:properties){
			sb.append("  ").append(property.toString()).append("\n");
		}
		return sb.substring(0,sb.length()-1);
	}

	public Map toMap(Object target) throws IllegalAccessException {
		Map<String,Object> map = new LinkedHashMap<String, Object>(properties.length);
		for (PojoProperty property:properties){
			map.put(property.name(),property.get(this,target));
		}
		return map;
	}

	public <T extends PojoProperty> T property(String id) {
		for(PojoProperty property:properties){
			if(property.name().equals(id)){
				return (T)property;
			}
		}
		return null;
	}

	public void set(Object target,String name,Object value) throws IllegalAccessException {
		property(name).set(this,target, value);
	}

	public <T extends PojoProperty> T get(Object target,String name) throws IllegalAccessException {
		return (T) property(name).get(this,target);
	}
}
