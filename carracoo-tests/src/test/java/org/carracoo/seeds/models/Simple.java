/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.models;

import org.carracoo.seeds.lang.Bean;
import org.carracoo.seeds.lang.properties.BeanProperty;

/**
 *
 * @author Sergey
 */
public class Simple extends Bean {
	public final BeanProperty<Simple,String> p1	= new BeanProperty<Simple,String>(){
		
	};
	public final BeanProperty<Simple,Simple> p2	= new BeanProperty<Simple,Simple>(){
		
	};
	public final BeanProperty<Simple,String> p3	= new BeanProperty<Simple,String>(){
		
	};
}
