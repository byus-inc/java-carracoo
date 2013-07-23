package org.carracoo.beans;

import org.carracoo.beans.models.CommentModel;
import org.carracoo.beans.models.PostModel;
import org.carracoo.beans.models.UserModel;
import org.carracoo.utils.Printer;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactoryTest {

	private final UserModel sergey = new UserModel(){{
		id.set("sergey");
		name.set("Sergey Mamyan");
		email.set("sergey.mamyan@gmail.com");
		tags.set("grish","axper");
		tags.set("hello","world","jan");
		tags.add("done");
	}};

	private final UserModel aram = new UserModel(){{
		id.set("aram");
		name.set("Aram Grigoryan");
		email.set("aram.grigoryan@gmail.com");
		tags.set("aram","jan","tag");
		tags.add("done");
	}};

	private final UserModel grish = new UserModel(){{
		id.set("grisha");
		name.set("Grisha Gharibyan");
		email.set("grishik@gmail.com");
		tags.set("grish","axper");
		tags.add("done");
	}};

	private final PostModel post = new PostModel(){{
		id      .set("1");
		title   .set("Cool PostModel Title");
		owner   .set(aram);
		comments.add(
			new CommentModel(){{
				author.set(sergey);
				message.set("Hello All");
			}},
			new CommentModel(){{
				author.set(aram);
				message.set("Hello Sergey");
			}},
			new CommentModel(){{
				author.set(grish);
				message.set("Hello Guys");
			}}
		);
	}};

	@Test
	public void testBeanFactory() throws BeanException {

		Map  map = Beans.encode(Beans.walker("json"), post);
		PostModel obj = Beans.decode(Beans.walker("json"), map, PostModel.class);

		Printer.print( Beans.encode(Beans.walker("json"), obj));
	}
}
