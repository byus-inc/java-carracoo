package org.carracoo.beans.lang;

import org.carracoo.beans.Property;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/26/13
 * Time: 4:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class NumberProperty<V extends Number> extends Property<V> {

	public void inc() {
		add((V) Integer.valueOf(1));
	}

	public void dec() {
		sub((V) Integer.valueOf(1));
	}

	public void sub(Number val) {
		if(options.type==Long.class){
			set((V)Long.valueOf(get().longValue()-val.longValue()));
		}else
		if(options.type==Integer.class){
			set((V)Integer.valueOf(get().intValue()-val.intValue()));
		}else
		if(options.type==Double.class){
			set((V)Double.valueOf(get().doubleValue()-val.doubleValue()));
		}else
		if(options.type==Float.class){
			set((V)Float.valueOf(get().floatValue()-val.floatValue()));
		}else
		if(options.type==Short.class){
			set((V)Short.valueOf((short)(get().shortValue()-val.shortValue())));
		}else
		if(options.type==Byte.class){
			set((V)Byte.valueOf((byte)(get().byteValue() - val.byteValue())));
		}
	}

	public void add(Number val) {
		if(options.type==Long.class){
			set((V)Long.valueOf(get().longValue()+val.longValue()));
		}else
		if(options.type==Integer.class){
			set((V)Integer.valueOf(get().intValue()+val.intValue()));
		}else
		if(options.type==Double.class){
			set((V)Double.valueOf(get().doubleValue()+val.doubleValue()));
		}else
		if(options.type==Float.class){
			set((V)Float.valueOf(get().floatValue()+val.floatValue()));
		}else
		if(options.type==Short.class){
			set((V)Short.valueOf((short)(get().shortValue()+val.shortValue())));
		}else
		if(options.type==Byte.class){
			set((V)Byte.valueOf((byte)(get().byteValue()+val.byteValue())));
		}
	}
}
