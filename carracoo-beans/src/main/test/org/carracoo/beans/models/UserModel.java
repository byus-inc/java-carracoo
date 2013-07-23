package org.carracoo.beans.models;

import org.carracoo.beans.View;
import org.carracoo.beans.lang.*;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserModel implements View.BeanEncoder,View.BeanDecoder {

	public final StringProperty id    = new StringProperty(){
		class Id extends Options implements View.Value,View.Key {
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
		class Name extends Options {{

		}}
	};
	public final EmailProperty  email = new EmailProperty(){
		class Email extends Options {{
			required = true;
		}}
	};
	public final StringProperty tags = new StringProperty(){
		class Tags extends Options implements View.Values{
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
