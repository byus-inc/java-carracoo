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
public abstract class IdentifierProperty<P extends Bean,V> extends BeanProperty<P, V> {
	abstract public void generate();
}
