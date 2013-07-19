/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.lang.properties.ext;

import org.carracoo.seeds.lang.Bean;
import org.carracoo.seeds.lang.properties.StringProperty;

import java.util.regex.Pattern;

/**
 *
 * @author Sergey
 */
 public class EmailProperty<P extends Bean>	 extends StringProperty<P> {
	private final static Pattern EMAIL_REGEXP = Pattern.compile("^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$");
	public EmailProperty() {
		super();
		format(EMAIL_REGEXP);
	}		
}

