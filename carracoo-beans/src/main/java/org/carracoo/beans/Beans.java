package org.carracoo.beans;


import org.carracoo.beans.core.BeanServiceImpl;
import org.carracoo.beans.core.WalkerImpl;
import org.carracoo.beans.exceptions.BeanDecodingException;
import org.carracoo.beans.exceptions.BeanEncodingException;
import org.carracoo.beans.exceptions.BeanException;
import org.carracoo.beans.exceptions.BeanValidationException;
import org.carracoo.utils.ANSI;
import org.carracoo.utils.Printer;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 2:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Beans {

	public static Class<? extends BeanService> FACTORY_CLASS = BeanServiceImpl.class;

	private static BeanService factory;
	public  static BeanService factory(){
		if(factory==null || !FACTORY_CLASS.equals(factory.getClass())){
			try {
				factory = FACTORY_CLASS.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return factory;
	}

	public static Boolean isBean(Class<?> type){
		return factory().isBean(type);
	}

	public static  <T> T get(Class<T> type) throws BeanException {
		return factory().newBean(type);
	}

	public static Definition definition(Class<?> type) throws BeanException {
		return factory().getDefinition(type);
	}


	public static Property property(Object bean, String name) throws BeanException {
		return factory().getProperty(bean, name);
	}

	public static Collection<Property> properties(Object bean) throws BeanException {
		return factory().getProperties(bean);
	}

	public static void validate(Object bean) throws BeanValidationException, BeanException {
		factory().getValidator().validate(bean);
	}

	public static void validate(Walker view, Object bean) throws BeanValidationException, BeanException {
		factory().getValidator(view).validate(bean);
	}

	public static Walker walker(String string) {
		return new WalkerImpl(string);
	}

	public static <T> T encode(Walker view, Object target) throws BeanException, BeanEncodingException {
		return factory().getEncoder(view).encode(view,target);
	}

	public static <T> T decode(Walker view, Object target, Class<T> type) throws BeanException, BeanDecodingException {
		return factory().getDecoder(view).decode(target, type);
	}

	static {
		Printer.add(new BeanPrinter());
	}
}

class BeanPrinter extends Printer.ObjectPrinter{

	@Override
	public boolean support(Object val) {
		return val!=null && Beans.isBean(val.getClass());
	}

	@Override
	public void print(Printer.Cursor cursor, Appendable buf, Object val) throws IOException {
		try {
			Collection<Property> properties = Beans.properties(val);
			append(buf, val.getClass().getSimpleName(), ANSI.Color.BLUE);
			for (Property property:properties){
				cursor.enter(property.options.name);
				nl(buf);
				ident(buf,cursor.level());
				append(buf, property.options.name, ANSI.Color.BLUE);
				append(buf, " : ");
				if(property.empty()){
					append(buf, "EMPTY", ANSI.Color.RED);
				}else
				if(property.options.multiple){
					Integer index = 0;
					append(buf,"ARRAY", ANSI.Color.MAGENTA);
					for(int i =0; i<property.all().length; i++){
						cursor.enter(index++);
						nl(buf);
						ident(buf,cursor.level());
						append(buf, index.toString(), ANSI.Color.MAGENTA);
						append(buf, " : ");
						Printer.print(cursor,buf,property.get(i));
						cursor.exit();
					}
				}else{
					Printer.print(cursor,buf,property.get());
				}
				cursor.exit();
			}
		} catch (BeanException e) {
			append(buf, e.getMessage(), ANSI.Color.RED);
		}
	}
}