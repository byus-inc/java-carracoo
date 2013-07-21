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
public class User implements Bean,View.BeanEncoder,View.BeanDecoder {

	public final StringProperty id    = new StringProperty(){
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
	public final StringProperty name  = new StringProperty(){
		class Name extends StringOptions {{

		}}
	};
	public final EmailProperty  email = new EmailProperty(){
		class Email extends EmailOptions {{
			required = true;
		}}
	};
	public final StringProperty tags = new StringProperty(){
		class Tags extends StringOptions implements View.Values{
			public Tags() {
				multiple = true;
				unique   = true;
				ordered  = true;
			}

			@Override
			public Object get(View view, ValueProperty property, Object item) {
				return "TAG "+item;
			}
		}
	};

	@Override
	public Object encode(View view) {
		if(view.in("owner")){
			return id.get();
		}else{
			return View.class;
		}
	}

	@Override
	public Object decode(View view, Object value) {
		if( value instanceof String ){
			id.set((String)value);
			return this;
		}
		return value;
	}
}
