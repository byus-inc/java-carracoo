package org.carracoo.beans.models;

import org.carracoo.beans.Bean;
import org.carracoo.beans.View;
import org.carracoo.beans.stl.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Post implements Bean {
	
	public final StringProperty id    = new StringProperty(this){
		class Id extends StringOptions implements View.Value,View.Key {
			public Id() {
				required = true;
				unique   = true;
			}
			@Override
			public String key(View view, ValueProperty property) {
				return name;
			}
			@Override
			public Object get(View view, ValueProperty property) {
				return view.path();
			}
		}
	};

	public final StringProperty name  = new StringProperty(this){
		class Name extends StringOptions {{

		}}
	};

	public final EmailProperty  email = new EmailProperty(this){
		class Email extends EmailOptions {{
			required = true;
		}}
	};

	public final StringProperty tags = new StringProperty(this){
		class Tags extends StringOptions {{
			multiple = true;
		}}
	};
}
