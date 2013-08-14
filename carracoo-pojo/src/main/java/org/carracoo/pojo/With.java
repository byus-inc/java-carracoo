package org.carracoo.pojo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 2:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class With {

	private final PojoDefinition definition;
	private final Object instance;

	public With(Object clazz){
		PojoDefinition definition;
		Object instance = clazz;
		try {
			if(instance instanceof Class){
				instance = ((Class)instance).newInstance();
			}
			definition = PojoMapper.getInstance().toDefinition(instance);
		}catch (Exception ex){
			definition = null;
		}
		this.instance   = instance;
		this.definition = definition;
	}

	public void set(String key,Object val){
		try {
			definition.set(instance,key,val);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> T get(String key){
		try {
			return (T) definition.get(instance,key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T get(){
		try {
			return (T) instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map map() {
		try {
			return definition.toMap(instance);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
