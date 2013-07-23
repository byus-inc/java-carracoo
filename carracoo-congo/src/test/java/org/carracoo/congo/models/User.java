package org.carracoo.congo.models;

import org.carracoo.beans.View;
import org.carracoo.beans.lang.EmailProperty;
import org.carracoo.beans.lang.StringProperty;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class User implements View.BeanEncoder,View.BeanDecoder {

	public final StringProperty id    = new StringProperty(){
		class Id extends Options {{
				required = true;
				unique   = true;
		}}
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
		class Tags extends Options {{
			multiple = true;
			unique   = true;
			ordered  = true;
		}}
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
