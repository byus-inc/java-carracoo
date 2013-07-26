package org.carracoo.beans;

import org.carracoo.beans.exceptions.BeanDecodingException;
import org.carracoo.beans.exceptions.BeanEncodingException;
import org.carracoo.beans.exceptions.BeanException;
import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.beans.models.CommentModel;
import org.carracoo.beans.models.PostModel;
import org.carracoo.beans.models.UserModel;
import org.carracoo.utils.Printer;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanServiceTest {

	private final UserModel sergey = new UserModel(){{
		id.set("sergey");
		name.set("Sergey Mamyan");
		email.set("sergey.mamyan@ser.com");
		tags.set("hello","world","jan");
		birthday.set(new Date(87,0,01,01,0,0));
		gender.set(Gender.MALE);
		categories.set(Category.WORKER);
	}};

	private final UserModel aram = new UserModel(){{
		id.set("aram");
		name.set("Aram Grigoryan");
		email.set("aram.grigoryan@gmail.com");
		tags.set("aram","jan","tag");
		//tags.add("done");
		gender.set(Gender.MALE);
		categories.set(
			Category.WORKER,
			Category.PARENT
		);
	}};

	private final UserModel grish = new UserModel(){{
		id.set("grisha");
		name.set("Grisha Gharibyan");
		email.set("grishik@gmail.com");
		tags.set("grish","axper");
		categories.set(
			Category.WORKER,
			Category.PARENT,
			Category.STUDENT
		);
	}};

	private final UserModel veronica = new UserModel(){{
		id.set("grisha");
		name.set("Veronica Grishman");
		email.set("vera@gmail.com");
		gender.set(Gender.FEMALE);
		categories.set(
			Category.STUDENT,
			Category.CHILD
		);
	}};

	private final PostModel post = new PostModel(){{
		id      .set("1");
		title   .set("Cool PostModel Title");
		owner   .set(aram);
		comments.set(
				new CommentModel() {{
					author.set(sergey);
					message.set("Hello All");
				}},
				new CommentModel() {{
					author.set(aram);
					message.set("Hello Sergey");
				}},
				new CommentModel() {{
					author.set(grish);
					message.set("Hello Guys");
				}}
		);
	}};

	@Test
	public void testBeanFactory() throws BeanException, BeanEncodingException, BeanDecodingException, BeanValidationException {
		Map         map   = Beans.encode(Beans.walker("json"), post);
		Printer.print(map);
		PostModel   obj   = Beans.decode(Beans.walker("json"), map, PostModel.class);
		Printer.print(obj);
	}

	@Test
	public void testBeanValidation() throws BeanException, BeanEncodingException, BeanDecodingException, BeanValidationException {
		Beans.validate(Beans.walker("json"),post);
	}

	@Test(expectedExceptions = BeanValidationException.class)
	public void testBeanValidationFail() throws BeanException, BeanEncodingException, BeanDecodingException, BeanValidationException {
		sergey.email.set("Gago");
		grish.name.clear();
		Beans.validate(Beans.walker("json"),post);
	}
}
