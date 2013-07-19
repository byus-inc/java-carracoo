/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.lang.properties;

import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.lang.Bean;

import java.util.Date;

/**
 *
 * @author Sergey
 */
public abstract class DateProperty<P extends Bean> extends BeanProperty<P, Date> {

	@Override
	public final Class<?> type() {
		return Date.class;
	}

	@Override
	public Object get(SeedView view) {
		return super.get().getTime();
	}

	@Override
	public Object set(SeedView view, Object value) {
		if(value instanceof Number){
			super.set(new Date((Long)((Number)value).longValue()));
		}
		return value;
	}
}
