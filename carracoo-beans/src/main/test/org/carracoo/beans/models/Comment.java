package org.carracoo.beans.models;

import org.carracoo.beans.Bean;
import org.carracoo.beans.models.properties.UserProperty;
import org.carracoo.beans.lang.StringOptions;
import org.carracoo.beans.lang.StringProperty;
import org.carracoo.beans.lang.ValueOptions;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Comment implements Bean {
	
	public final UserProperty author    = new UserProperty(){
		class Author extends ValueOptions {{
			required = true;
		}}
	};

	public final StringProperty message  = new StringProperty(){
		class Title extends StringOptions {{
			required = true;
		}}
	};

}
