/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.json;

import junit.framework.TestCase;

/**
 *
 * @author Sergey
 */
public class JsonTest extends TestCase {

	
	public void testParsing() {
		JSON.decode("{'hello':[],'other':{}}");
	}
}
