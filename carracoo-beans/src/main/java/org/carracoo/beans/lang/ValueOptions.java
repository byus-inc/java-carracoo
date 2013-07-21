package org.carracoo.beans.lang;

import org.carracoo.beans.AbstractOptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueOptions extends AbstractOptions {
	public Boolean multiple     = false;
	public Boolean required     = false;
	public Boolean unique       = false;
	public Boolean ordered      = false;
	public Class   container    = null;
}
