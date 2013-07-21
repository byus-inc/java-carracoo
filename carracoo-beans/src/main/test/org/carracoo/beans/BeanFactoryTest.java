package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.beans.models.Comment;
import org.carracoo.beans.models.Post;
import org.carracoo.beans.models.User;
import org.carracoo.beans.stl.EmailOptions;
import org.carracoo.beans.stl.StringOptions;
import org.carracoo.beans.stl.ValueProperty;
import org.carracoo.utils.Printer;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactoryTest {

	private final User sergey = new User(){{
		id.set("sergey");
		name.set("Sergey Mamyan");
		email.set("sergey.mamyan@gmail.com");
		tags.set("grish","axper");
		tags.set("hello","world","jan");
		tags.add("done");
	}};

	private final User aram = new User(){{
		id.set("aram");
		name.set("Aram Grigoryan");
		email.set("aram.grigoryan@gmail.com");
		tags.set("aram","jan","tag");
		tags.add("done");
	}};

	private final User grish = new User(){{
		id.set("grisha");
		name.set("Grisha Gharibyan");
		email.set("grishik@gmail.com");
		tags.set("grish","axper");
		tags.add("done");
	}};

	private final Post post = new Post(){{
		id      .set("1");
		title   .set("Cool Post Title");
		owner   .set(aram);
		comments.add(
			new Comment(){{
				author.set(sergey);
				message.set("Hello All");
			}},
			new Comment(){{
				author.set(aram);
				message.set("Hello Sergey");
			}},
			new Comment(){{
				author.set(grish);
				message.set("Hello Guys");
			}}
		);
	}};

	@Test
	public void testBeanFactory() throws BeanException {

		Map  map = Beans.encode(Beans.walker("json"), post);
		Post obj = Beans.decode(Beans.walker("json"), map, Post.class);

		Printer.print( Beans.encode(Beans.walker("json"), obj));
	}
}
