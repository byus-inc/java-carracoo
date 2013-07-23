package org.carracoo.congo.models;

import org.carracoo.beans.lang.StringProperty;
import org.carracoo.congo.models.properties.UserProperty;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Comment {
	
	public final UserProperty author    = new UserProperty(){
		class Author extends Options {{
			required = true;
		}}
	};

	public final StringProperty message  = new StringProperty(){
		class Title extends Options {{
			required = true;
		}}
	};

}
