package org.carracoo.pojo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 8/4/13
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Target(value={
	ElementType.TYPE,
	ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Accessor {
	Class value();
}
