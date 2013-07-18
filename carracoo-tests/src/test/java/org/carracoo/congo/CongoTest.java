/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.congo;

import junit.framework.TestCase;
import org.carracoo.bson.BsonParser;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;
import org.carracoo.seeds.models.Comment;
import org.carracoo.seeds.models.Post;
import org.carracoo.seeds.models.User;
import org.carracoo.utils.Printer;

/**
 *
 * @author Sergey
 */
public class CongoTest extends TestCase {

	private final CongoDatabase congo = new CongoDatabase(
		"mongodb://admin:admin@flame.mongohq.com:27074/test-sm",
		new Seeds(){{
			parser(new BsonParser());
		}},
		new SeedView("bson")
	);

	private final User sergey = new User(){{
		id          .set("51d590641df65bab401191d4");
		name		.set("Sergey Mamyan");
		password	.set("hehehe");
		email		.set("Sergey.Mamyan@gmail.com");
		links		.set("Hello");
		links		.set("World");
	}}.commit();

	private final User grisha = new User(){{
		name		.set("Grisha Valuk");
		password	.set("mypassword");
		email		.set("Grisha.Valuk@gmail.com");
		links		.set("Hello");
		links		.set("World");
	}}.commit();

	private final User manila= new User(){{
		name		.set("Manila Gruchak");
		password	.set("mypassword");
		email		.set("manila.gruchak@gmail.com");
		links		.set("Hello");
		links		.set("World");
	}}.commit();

	private final Post post = new Post(){{
		author		.set(sergey);
		title		.set("Post Title");
		content		.set("Post Content");
		comments	.set(new Comment(){{
			author.set(manila);
			message.set("Hello All");
		}});
		comments	.set(new Comment(){{
			author.set(grisha);
			message.set("Hello Manila");
		}});
		comments	.set(new Comment(){{
			author.set(sergey);
			message.set("Hello Gyus");
		}});
	}}.commit();



	public CongoTest(){
		super();
	}

	private void print(Object value){
		System.out.println(value==null?"NULL":value.toString());
	}

	public void testParsing() {
		congo.collection(User.class).save(sergey);
		System.out.println(congo.collection(User.class).count("{}"));
		Iterable<User> users = congo.collection(User.class).findOnly("{}","{'name':1,'password':1}").limit(1).sort("{'$natural':-1}");
		for(User user:users){
			Printer.print(user);
		}
	}
}
