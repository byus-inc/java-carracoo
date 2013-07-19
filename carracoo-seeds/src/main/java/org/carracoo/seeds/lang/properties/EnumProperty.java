/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.lang.properties;

import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.lang.Bean;

/**
 *
 * @author Sergey
 */
public abstract class EnumProperty<P extends Bean,V extends Enum> extends BeanProperty<P, V> {
	public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumeration, String name) {
		for(Enum enumValue : enumeration.getEnumConstants()) {
			if(enumValue.name().equalsIgnoreCase(name)) {
				return (T) enumValue;
			}
		}
		throw new IllegalArgumentException("There is no value with name '" + name + " in Enum " + enumeration.getClass().getName());        
	}
	
	public P set(String value) {
		return super.set((V)valueOfIgnoreCase((Class<Enum>) type(), value));
	}

	@Override
	public Object get(SeedView view) {
		return get().name().toLowerCase();
	}
	
}
