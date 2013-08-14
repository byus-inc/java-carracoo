package org.carracoo.pojo;

import org.carracoo.pojo.models.User;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class PojoMapperTest {

	@Test
	public void testMapper() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {

		User user = new User();

		new With(user){{
			set("id",1);
			set("name","User Name");
			set("email","some.email@gmail.com");
		}};

		System.out.println(user.id);
		System.out.println(user.name);
		System.out.println(
			new With(user){{
				set("name","Gagik Jan");
			}}.map()
		);

	}
}
