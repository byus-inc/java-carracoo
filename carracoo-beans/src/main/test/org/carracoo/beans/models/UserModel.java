package org.carracoo.beans.models;

import org.carracoo.beans.Property;
import org.carracoo.beans.View;
import org.carracoo.beans.lang.*;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserModel implements View.BeanEncoder,View.BeanDecoder {

	public enum Category {
		WORKER,STUDENT,CHILD,PARENT
	}

	public static enum Gender{
		MALE,FEMALE
	}

	public final StringProperty id    = new StringProperty(){
		class Id extends Options implements View.ValueEncoder,View.Key {
			public Id() {
				required = true;
				unique   = true;
			}

			@Override
			public String key(View view, Property property) {
				return name;
			}

			@Override
			public Object encode(View view, Property property, int index) {
				return view.path();
			}
		}
	};

	public final StringProperty name  = new StringProperty(){
		class Name extends Options {{
			required = true;
		}}
	};

	public final EnumProperty<Gender> gender  = new EnumProperty<Gender>(){
		class Gender extends Options {{
			numeric = false;
		}}
	};

	public final DateProperty birthday = new DateProperty(){
		class Birthday extends Options {{
			required = true;
			numeric = false;
		}}{
			set(new Date());
		}
	};

	public final EnumProperty<Category> categories  = new EnumProperty<Category>(){
		class Categories extends Options {{
			multiple = true;
		}}
	};

	public final EmailProperty  email = new EmailProperty(){
		class Email extends Options {{
			required = true;
		}}
	};

	public final StringProperty tags = new StringProperty(){
		class Tags extends Options implements View.ValueEncoder {
			public Tags() {
				multiple = true;
				unique   = true;
				ordered  = true;
			}
			@Override
			public Object encode(View view, Property property, int item) {
				return "TAG "+property.get(item);
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
