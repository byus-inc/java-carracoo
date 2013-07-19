/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.lang.properties;

import org.carracoo.seeds.lang.Bean;

/**
 *
 * @author Sergey
 */
public abstract class FloatProperty<P extends Bean> extends NumberProperty<P, Float> {

	@Override
	public final Class<?> type() {
		return Float.class;
	}
	
}
