package org.carracoo.congo.parsing;

import com.mongodb.DBObject;
import org.bson.BSONObject;
import org.carracoo.seeds.lang.Corn;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SeedObject implements DBObject {

	private final Object target;

	public final Object target(){
		return target;
	}

	private boolean isModel(){
		return target instanceof Corn;
	}

	private boolean isMap(){
		return target instanceof Map;
	}

	public SeedObject(Object model){
		target = model;
	}

	public Corn toModel() {
		return (Corn)target;
	}

	@Override
	public Map toMap() {
		return (Map)target;
	}

	@Override
	public void markAsPartialObject() {

	}

	@Override
	public boolean isPartialObject() {
		return false;
	}



	@Override
	public void putAll(BSONObject o) {
		putAll(o.toMap());
	}

	@Override
	public void putAll(Map m) {
		if(isMap()){
			toMap().putAll(m);
		}else{
			throw new UnsupportedOperationException("Should be implemented for model");
		}
	}

	@Override
	public Object get(String key) {
		if(key.equals("_id")){
			key = "id";
		}
		if(isModel() && toModel().keys().contains(key)){
			return toModel().property(key).get();
		}else
		if(isMap()){
			return toMap().get(key);
		}
		return null;
	}

	@Override
	public Object put(String key, Object v) {
		if(key.equals("_id")){
			key = "id";
		}
		if(isModel()){
			toModel().property(key).set(v.toString());
		}else
		if(isMap()){
			toMap().put(key,v);
		}
		return v;
	}



	@Override
	public Object removeField(String key) {
		if(isModel()){
			Object old = toModel().property(key).get();
			toModel().property(key).set(null);
			return old;
		}else
		if(isMap()){
			return toMap().remove(key);
		}else{
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean containsKey(String key) {
		if(isModel()){
			return toModel().keys().contains(key);
		}else
		if(isMap()){
			return toMap().containsKey(key);
		}else{
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean containsField(String s) {
		return containsKey(s);
	}

	@Override
	public Set<String> keySet() {
		if(isModel()){
			return toModel().keys();
		}else
		if(isMap()){
			return toMap().keySet();
		}else{
			throw new UnsupportedOperationException();
		}
	}
}
