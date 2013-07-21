package org.carracoo.beans;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 6:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractOptions {
	public Object  parent       = null;
	public String  name         = null;
	public Class   type         = null;
	public Field   field        = null;
}
