package org.carracoo.beans;

import org.carracoo.beans.stl.ValueProperty;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class BeanDefinition implements Iterable<ValueProperty> {
	private final Map<String,ValueProperty> properties = Collections.synchronizedMap(
		new LinkedHashMap<String, ValueProperty>()
	);


	final private Class<?>   type;


	public Class<?> getType(){
		return type;
	}
	public <T> T create() throws BeanException{
		try {
			return (T) getType().newInstance();
		}catch (Exception ex){
			throw new BeanException("Bean creation failed"+type,ex);
		}
	}
	public BeanDefinition(Object target) throws BeanException {
		Class<?> type       = target.getClass();


		for (Field field : type.getFields()){
			if(ValueProperty.class.isAssignableFrom(field.getType())){
				try {
					ValueProperty property = (ValueProperty) field.get(target);
					if(property.options.field == null){
						property.options.field = field;
					}
					properties.put(property.options.name, property);
				} catch (IllegalAccessException e) {
					throw new BeanException("Invalid bean class "+type,e);
				}
			}
		}

		if(properties.size()==0){
			throw new RuntimeException("Invalid bean class "+type);
		}

		this.type       = type;

	}

	public ValueProperty getProperty(String name) throws BeanException{
		return properties.get(name);
	}
	public ValueProperty getProperty(Object bean, String name) throws BeanException{
		try {
			return ((ValueProperty) getProperty(name).options.field.get(bean));
		} catch (IllegalAccessException e) {
			throw new BeanException("property access exception "+name,e);
		}
	}
	public Collection<ValueProperty> getProperties() {
		return properties.values();
	}

	public Collection<ValueProperty> getProperties(Object bean) throws BeanException{
		java.util.List<ValueProperty> propertyList = new ArrayList<ValueProperty>();
		for(ValueProperty property:getProperties()){
			try {
				propertyList.add((ValueProperty) property.options.field.get(bean));
			} catch (IllegalAccessException e) {
				throw new BeanException("property access exception "+property.options.name,e);
			}
		}
		return propertyList;
	}

	@Override
	public Iterator<ValueProperty> iterator() {
		return properties.values().iterator();
	}

}

