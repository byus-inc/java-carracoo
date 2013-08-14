package org.carracoo.pojo.models;

import org.carracoo.pojo.PojoDefinition;
import org.carracoo.pojo.PojoProperty;
import org.carracoo.pojo.annotations.Accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 12:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Accessor(User.Definition.class)
public class User {

	@Accessor(Definition.id.class)	    public final String  id     = null;
	@Accessor(Definition.name.class)	public final String  name   = null;
	@Accessor(Definition.email.class)	public final String  email  = null;
	@Accessor(Definition.groups.class)	public final Group[] groups = null;


	public static class Definition  extends PojoDefinition {
		public static class id      extends PojoProperty {
			@Override
			public void set(PojoDefinition definition, Object target, Object value) throws IllegalAccessException {
				if(!(value instanceof String)){
					super.set(definition, target, "ID-"+value.toString());
				}else{
					super.set(definition, target, value.toString());
				}
			}
		}
		public static class name    extends PojoProperty {

		}
		public static class email   extends PojoProperty {

		}
		public static class groups  extends PojoProperty {

		}
	}

}
