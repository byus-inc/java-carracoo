package org.carracoo.beans.lang;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailProperty extends StringProperty {
	public static class Options extends StringProperty.Options {{
		format = Pattern.compile("^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$");
	}}
}
