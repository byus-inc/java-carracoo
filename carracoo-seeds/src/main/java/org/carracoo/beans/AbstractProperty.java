package org.carracoo.beans;

import org.carracoo.beans.stl.ValueOptions;
import org.carracoo.utils.ReflectUtils;
import org.carracoo.utils.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 5:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractProperty<T> {

	private static final Map<Class,ValueOptions> optionsCache =
		new ConcurrentHashMap<Class, ValueOptions>();


	final public Bean          parent;
	final public ValueOptions  options;


	public AbstractProperty(Bean bean){
		ValueOptions options = null;
		Bean parent = bean;
		try {
			if(parent==null){
				Field field = this.getClass().getDeclaredField("this$0");
				field.setAccessible(true);
				parent = (Bean) field.get(this);
			}
			if(!optionsCache.containsKey(this.getClass())){
				Constructor<?> constructor = this.getClass().getDeclaredClasses()[0].getDeclaredConstructor(this.getClass());
				constructor.setAccessible(true);
				options = (ValueOptions) constructor.newInstance(this);
				if(options.name==null){
					options.name = StringUtils.toUnderscoredNotation(options.getClass().getSimpleName());
					if(options.name.endsWith("_options")){
						options.name = options.name.replaceAll("(.*)_options", "$1");
					}
				}
				if(options.type==null){
					options.type = ReflectUtils.getTypeArguments(
						AbstractProperty.class, this.getClass()
					).get(0);
				}
				optionsCache.put(this.getClass(), options);
			}else{
				options = optionsCache.get(this.getClass());
			}

		}catch (Exception ex){
			ex.printStackTrace();
		}

		this.parent  = parent;
		this.options = options;
	}
}
