/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carracoo.seeds.lang.grains;

import com.carracoo.seeds.lang.Corn;

/**
 *
 * @author Sergey
 */
public abstract class FLOAT<P extends Corn> extends NUMBER<P, Float> {

	@Override
	public final Class<?> type() {
		return Float.class;
	}
	
}
