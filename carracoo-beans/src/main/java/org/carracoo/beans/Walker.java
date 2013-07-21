package org.carracoo.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/21/13
 * Time: 12:10 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Walker extends View {
	public void enter(Integer index);
	public void enter(String path);
	public void exit();
}
