/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.lang.properties;

import org.carracoo.seeds.lang.Bean;
import org.carracoo.seeds.lang.Property;

/**
 *
 * @author Sergey
 */
public abstract class BeanProperty<P extends Bean, V> extends Property<P, V> {
	@Override
	protected V create() {
		return null;
	}
}
