package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Definition implements Iterable<Property> {
	private final Map<String,Property> properties = Collections.synchronizedMap(
		new LinkedHashMap<String, Property>()
	);


	final private Class<?>   type;


	public Class<?> getType(){
		return type;
	}
	public <T> T create() throws BeanException {
		try {
			return (T) getType().newInstance();
		}catch (Exception ex){
			throw new BeanException("Bean creation failed"+type,ex);
		}
	}
	public Definition(Object target) throws BeanException {
		Class<?> type       = target.getClass();


		for (Field field : type.getFields()){
			if(Property.class.isAssignableFrom(field.getType())){
				try {
					Property property = (Property) field.get(target);
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

	public Boolean hasProperty(String name){
		return properties.containsKey(name);
	}

	public Property getProperty(String name){
		return properties.get(name);
	}

	public Property getProperty(Object bean, String name) throws BeanException {
		try {
			return ((Property) getProperty(name).options.field.get(bean));
		} catch (IllegalAccessException e) {
			throw new BeanException("property access exception "+name,e);
		}
	}

	public Set<String> getKeys() {
		return properties.keySet();
	}

	public Collection<Property> getProperties() {
		return properties.values();
	}

	public Collection<Property> getProperties(Object bean) throws BeanException{
		java.util.List<Property> propertyList = new ArrayList<Property>();
		for(Property property:getProperties()){
			try {
				propertyList.add((Property) property.options.field.get(bean));
			} catch (IllegalAccessException e) {
				throw new BeanException("property access exception "+property.options.name,e);
			}
		}
		return propertyList;
	}

	@Override
	public Iterator<Property> iterator() {
		return properties.values().iterator();
	}

}

