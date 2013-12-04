package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.utils.ReflectUtils;
import org.carracoo.utils.StringUtils;

import java.lang.reflect.Array;
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
	public  static class Options implements View.ValueValidator {
		//General Options
		public String  name         = null;
		public Class   type         = null;
		public Field   field        = null;
		//Value Options
		public Boolean multiple     = false;
		public Boolean required     = false;
		public Boolean unique       = false;
		public Boolean ordered      = false;

		@Override
		public void validate(View view, Property property, int index) throws BeanValidationException {

			if (index==-1 && required && property.empty()){
				throw new BeanValidationException(view,
					"PROPERTY_REQUIRED",
					String.format("property %s is required",name)
				);
			}
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

	protected V[] values;

	public V get(){
		return values==null?null:values[0];
	}

	public V get(int index){
		return values!=null && values.length>index ? values[index]:null;
	}

	public V[] all(){
		return values;
	}

	public void set(Collection<V> collection){
		set((V[])collection.toArray());
	}

	public boolean has(V item){
		if(empty()){
			return false;
		}else
		if(options.multiple){
			for(V next:all()){
				if(next.equals(item)){
					return true;
				}
			}
			return false;
		}else{
			return get().equals(item);
		}
	}

	public void add(V item){
		if(options.multiple){
			if(!options.unique || (options.unique && !has(item))){
				if(values==null){
					values = (V[])Array.newInstance(options.type,0);
				}
				values = Arrays.copyOf(values,values.length+1);
				values[values.length-1] = item;
			}
		}
	}

	public void set(V... args){
		if(args==null || args.length==0){
			values = null;
		}else
		if(options.multiple){
			values = args;
		}else
		if(args.length==1){
			values = args[0]==null?null:args;
		}else{
			throw new RuntimeException("Property is not single value");
		}
	}

	public void clear(){
		values = null;
	}

	public boolean empty(){
		return values==null || values.length == 0;
	}

	@Override
	public Iterator<V> iterator() {
		return Arrays.asList(values).iterator();
	}

}
