package org.carracoo.beans.models;

import org.carracoo.beans.Property;
import org.carracoo.beans.lang.DateProperty;
import org.carracoo.beans.models.properties.UserProperty;
import org.carracoo.beans.lang.StringProperty;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommentModel {
	
	public final UserProperty author    = new UserProperty(){
		class Author extends Property.Options {{
		}}
	};

	public final StringProperty message  = new StringProperty(){
		class Title extends Options {{
			required = true;
		}}
	};

	public final DateProperty createdAt = new DateProperty(){
		class CreatedAt extends Options {{
			required = true;
		}}{
			set(new Date());
		}
	};
}
