package org.carracoo.beans.models;

import org.carracoo.beans.lang.StringProperty;
import org.carracoo.beans.models.properties.CommentProperty;
import org.carracoo.beans.models.properties.UserProperty;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PostModel {
	
	public final StringProperty id    = new StringProperty(){
		class Id extends Options {{
			required = true;
			unique   = true;
		}}
	};

	public final StringProperty title  = new StringProperty(){
		class Title extends Options {{

		}}
	};

	public final UserProperty owner = new UserProperty(){
		class Owner extends Options {{
		}}
	};

	public final CommentProperty comments = new CommentProperty(){
		class Comments extends Options {{
			multiple = true;
		}}
	};
}
