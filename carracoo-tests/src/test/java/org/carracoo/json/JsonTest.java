/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.json;

import junit.framework.TestCase;
import org.carracoo.bson.BsonParser;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;
import org.carracoo.seeds.mapper.grain.GrainDecoder;
import org.carracoo.seeds.mapper.grain.GrainEncoder;
import org.carracoo.seeds.models.Comment;
import org.carracoo.seeds.models.Post;
import org.carracoo.seeds.models.Simple;
import org.carracoo.seeds.models.User;
import org.carracoo.utils.Printer;

/**
 *
 * @author Sergey
 */
public class JsonTest extends TestCase {

	
	public void testParsing() {
		JSON.decode("{'hello':[],'other':{}}");
	}
}
