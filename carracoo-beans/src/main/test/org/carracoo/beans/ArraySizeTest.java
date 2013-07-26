package org.carracoo.beans;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/26/13
 * Time: 3:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArraySizeTest {

	public static ArraySizeTest instance;

	private Object   object[];

	public static void main(String[] args){
		instance = new ArraySizeTest();
	}

	public ArraySizeTest(){
		object        = new Object[1000];
	}
}
