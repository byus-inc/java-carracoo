package org.carracoo.beans.stl;

import org.carracoo.beans.AbstractProperty;
import org.carracoo.beans.Bean;
import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.utils.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueProperty<V> extends AbstractProperty<V> implements Iterable<V> {

	protected ValueOptions options(){
		return (ValueOptions) options;
	}

	protected V value;
	protected Collection<V> values;

	public V get(){
		if(options().multiple){
			if(values==null){
				return null;
			}else{
				return values.iterator().next();
			}
		}else{
			return value;
		}
	}


	public void set(V value){
		if(options().multiple){
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
		if(options().multiple){
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
			if(options().container==null){
				if(options().unique){
					if(options().ordered){
						options().container = TreeSet.class;
					}else{
						options().container = LinkedHashSet.class;
					}
				}else{
					options().container = ArrayList.class;
				}
			}
			return (Collection<V>) options().container.newInstance();
		}catch (Exception ex){
			if(options().unique){
				if(options().ordered){
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
		if(options().multiple){
			if(values==null){
				values = createContainer();
			}
			return values.iterator();
		}else{
			return null;
		}
	}

	public void validate() throws BeanValidationException {
		if (options().required && empty()){
			throw new BeanValidationException(
				String.format("property %s is required",options.name)
			);
		}
	}
}
