package org.carracoo.congo.models;

import org.carracoo.beans.Bean;
import org.carracoo.beans.stl.StringOptions;
import org.carracoo.beans.stl.StringProperty;
import org.carracoo.beans.stl.ValueOptions;
import org.carracoo.congo.models.properties.CommentProperty;
import org.carracoo.congo.models.properties.UserProperty;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Post implements Bean {
	
	public final StringProperty id    = new StringProperty(){
		class Id extends StringOptions {{
			required = true;
			unique   = true;
		}}
	};

	public final StringProperty title  = new StringProperty(){
		class Title extends StringOptions {{

		}}
	};

	public final UserProperty owner = new UserProperty(){
		class Owner extends ValueOptions {{
		}}
	};

	public final CommentProperty comments = new CommentProperty(){
		class Comments extends ValueOptions {{
			multiple = true;
		}}
	};
}
