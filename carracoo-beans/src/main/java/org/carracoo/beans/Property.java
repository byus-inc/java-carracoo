package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.utils.ReflectUtils;
import org.carracoo.utils.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Property<V> implements Iterable<V> {

	private static final Map<Class,Options> optionsCache = new ConcurrentHashMap<Class,Options>();
	public  static class Options {
		//General Options
		public Object  parent       = null;
		public String  name         = null;
		public Class   type         = null;
		public Field   field        = null;
		//Value Options
		public Boolean multiple     = false;
		public Boolean required     = false;
		public Boolean unique       = false;
		public Boolean ordered      = false;
		public Class   container    = null;
		public Class   container(){
			if(container==null){
				if(unique){
					if(ordered){
						container = TreeSet.class;
					}else{
						container = LinkedHashSet.class;
					}
				}else{
					container = ArrayList.class;
				}
			}
			return container;
		}
	}

	public Property() {
		Options options = null;
		try {
			if(!optionsCache.containsKey(this.getClass())){
				Constructor<?> constructor = this.getClass().getDeclaredClasses()[0].getDeclaredConstructor(this.getClass());
				constructor.setAccessible(true);
				options = (Options) constructor.newInstance(this);
				if(options.name==null){
					options.name = StringUtils.toUnderscoredNotation(options.getClass().getSimpleName());
				}
				if(options.type==null){
					options.type = ReflectUtils.getTypeArguments(
						Property.class, this.getClass()
					).get(0);
				}
				optionsCache.put(this.getClass(), options);
			}else{
				options = optionsCache.get(this.getClass());
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		this.options = options;
	}



	final public Options options;


	protected V value;
	protected Collection<V> values;

	public V get(){
		if(options.multiple){
			if(values==null){
				return null;
			}else{
				if(values.size()>0){
					return values.iterator().next();
				}else{
					return null;
				}
			}
		}else{
			return value;
		}
	}


	public void set(V value){
		if(options.multiple){
			setMulti(value);
		}else{
			setSingle(value);
		}
		this.value = value;
	}

	public void clear(){
		value = null;
		if(values!=null){
			values.clear();
		}
	}

	public void set(Iterable<V> value){
		clear(); add(value);
	}
	public void set(V... args){
		clear();
		add(args);
	}
	public void add(V... args){
		for(V v:args){
			set(v);
		}
	}
	public void add(Iterable<V> value){
		for(V v:value){
			set(v);
		}
	}

	public boolean empty(){
		if(options.multiple){
			return values==null || values.size()==0;
		}else {
			return null == value;
		}
	}

	private void setSingle(V value){
		this.value = value;
	}

	private void setMulti(V value){
		if(values==null){
			values = createContainer();
		}
		if(value instanceof Collection){
			values.addAll((Collection)value);
		}else {
			values.add(value);
		}
	}

	protected Collection<V> createContainer(){
		try{
			return (Collection<V>) options.container().newInstance();
		}catch (Exception ex){
			if(options.unique){
				if(options.ordered){
					return new TreeSet<V>();
				}else{
					return new LinkedHashSet<V>();
				}
			}else{
				return new ArrayList<V>();
			}
		}
	}

	@Override
	public Iterator<V> iterator() {
		if(options.multiple){
			if(values==null){
				values = createContainer();
			}
			return values.iterator();
		}else{
			return null;
		}
	}

	public void validate(View view) throws BeanValidationException {
		if (options.required && empty()){
			throw new BeanValidationException(view,
				"PROPERTY_REQUIRED",
				String.format("property %s is required",options.name)
			);
		}
	}

	public void validate(View view, Object item) throws BeanValidationException {

	}
}
