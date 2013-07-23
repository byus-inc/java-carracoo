package org.carracoo.beans.stl;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueProperty<String> {

	final
	private ValueOptions options;
	public ValueOptions options(){
		return options;
	}

	public ValueProperty(ValueOptions options){
		this.options = options;
	}

}
