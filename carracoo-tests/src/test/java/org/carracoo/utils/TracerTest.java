package org.carracoo.utils;

import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/11/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class TracerTest extends TestCase {
	public void testPrintLocation(){
		System.out.println(Tracer.getLocation());
		System.out.println(Tracer.getLocation("{CLASS}.{METHOD} {FILE}:{LINE}"));
		Tracer.printLocation();
		Tracer.printLocation("{CLASS}.{METHOD} {FILE}:{LINE}");
	}
}
