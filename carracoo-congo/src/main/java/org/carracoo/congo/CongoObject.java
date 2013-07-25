package org.carracoo.congo;

import com.mongodb.DBObject;
import org.bson.BSONObject;
import org.carracoo.beans.Definition;
import org.carracoo.beans.exceptions.BeanException;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoObject implements DBObject {

	private final Object         bean;
	private final Map            map;
	private final Definition definition;


	public final Object target(){
		if(bean!=null){
			return bean;
		}else{
			return map;
		}
	}

	public CongoObject(Object bean, Definition definition){
		if(definition.getType().isAssignableFrom(bean.getClass())){
			this.bean        = bean;
			this.definition  = definition;
			this.map         = null;
			this.partial     = false;
		}else{
			throw new RuntimeException("invlid bean object");
		}
	}

	public CongoObject(Object target) {
		if(target instanceof Map){
			this.bean        = null;
			this.definition  = null;
			this.map         = (Map)target;
			this.partial     = false;
		}else{
			throw new RuntimeException("invlid map object");
		}
	}

	@Override
	public Map toMap() {
		return map;
	}

	private boolean partial;
	@Override
	public void markAsPartialObject() {
		partial = true;
	}

	@Override
	public boolean isPartialObject() {
		return partial;
	}

	@Override
	public void putAll(BSONObject o) {
		for(String key:o.keySet()){
			setProperty(key,o.get(key));
		}
	}

	@Override
	public void putAll(Map m) {
		for(String key: (Set<String>) m.keySet()){
			setProperty(key,m.get(key));
		}
	}

	private Boolean hasProperty(String key){
		if(bean!=null){
			return definition.hasProperty(key);
		}else{
			return map.containsKey(key);
		}
	}

	private Object getProperty(String key){
		try {
			if(bean!=null){
				if(definition.hasProperty(key)){
					return definition.getProperty(bean,key).get();
				}else{
					return null;
				}
			}else{
				return map.get(key);
			}
		} catch (BeanException e) {
			return null;
		}
	}

	private Object removeProperty(String key){
		try {
			if(bean!=null){
				if(definition.hasProperty(key)){
					Object result = getProperty(key);
					definition.getProperty(bean,key).clear();
					return result;
				}else{
					return null;
				}
			}else{
				return map.remove(key);
			}
		} catch (BeanException e) {
			return null;
		}
	}

	private Object setProperty(String key,Object value){
		try {
			if(bean!=null){
				if(definition.hasProperty(key)){
					Object result = getProperty(key);
					definition.getProperty(bean,key).set(value);
					return result;
				}else{
					return null;
				}
			}else{
				return map.put(key, value);
			}
		} catch (BeanException e) {
			return null;
		}
	}

	@Override
	public Object get(String key) {
		if(key.equals("_id")){
			key = "id";
		}
		return getProperty(key);
	}

	@Override
	public Object put(String key, Object v) {
		if(key.equals("_id")){
			key = "id";
		}
		return setProperty(key, v);
	}

	@Override
	public Object removeField(String key) {
		if(key.equals("_id")){
			key = "id";
		}
		return removeProperty(key);
	}

	@Override
	public boolean containsKey(String key) {
		if(key.equals("_id")){
			key = "id";
		}
		return hasProperty(key);
	}

	@Override
	public boolean containsField(String s) {
		return containsKey(s);
	}

	@Override
	public Set<String> keySet() {
		if(bean!=null){
			return definition.getKeys();
		}else{
			return map.keySet();
		}
	}

}
