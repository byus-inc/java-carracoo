package org.carracoo.congo;

import org.carracoo.beans.BeanMapper;
import org.carracoo.beans.BeanMappingException;
import org.carracoo.beans.Beans;
import org.carracoo.bson.BSON;
import org.carracoo.congo.models.User;
import org.carracoo.json.JSON;
import org.carracoo.utils.ByteUtils;
import org.carracoo.utils.Printer;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/21/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoDatabaseTest {
	@Test
	public void testDatabase() throws BeanMappingException {
		CongoDatabase database = new CongoDatabase(
			"mongodb://local.byus.com:27017/test",
			Beans.mapper(),
			Beans.walker("bson:mongo")
		);
		CongoCollection<User> users = database.collection(User.class);
		User sergey = new User(){{
			id.set("sergey");
			name.set("Sergey Mamyan");
			email.set("sergey.mamyan@gmail.com");
			tags.set("web","developer");
		}};
		Map map;

		users.save(sergey);
		User result = users.get("{'id':'sergey'}");
		map = Beans.encode(Beans.walker("bson:mongo"), result);
		Printer.print(map);
	}
}
