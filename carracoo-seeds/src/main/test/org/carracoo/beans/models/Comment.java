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
public class Comment implements Bean {
	
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

	public final StringProperty title  = new StringProperty(this){
		class Title extends StringOptions {{

		}}
	};

	public final StringProperty comments = new StringProperty(this){
		class Comments extends StringOptions {{
			multiple = true;
		}}
	};
}
