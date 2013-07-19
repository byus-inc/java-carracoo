/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.lang;

import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.exceptions.ValidationException;
import org.carracoo.utils.ANSI;
import org.carracoo.utils.Printer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author Sergey
 */
public class Bean {
	
	
	
	private static final Map<Class,List<FieldInfo>> FIELDS = new ConcurrentHashMap<Class, List<FieldInfo>>();
	private static final Map<Class,Set<String>> KEYS = new ConcurrentHashMap<Class, Set<String>>();
	protected final static class FieldInfo {
		public final Field field;
		public final int   index;
		public FieldInfo(int index,Field field){
			this.field = field;
			this.index = index;
		}
	}
	
	protected final Class<? extends Bean> clazz(){
		Class<? extends Bean> cls = this.getClass();
		while(cls.isAnonymousClass() && cls.getEnclosingClass()!=null){
			cls = (Class<? extends Bean>) cls.getSuperclass();
		}
		return cls;
	}
	
	protected final List<FieldInfo> fields(){
		Class<?> cls = clazz();
		if(!FIELDS.containsKey(cls)){
			List<FieldInfo> fields = new ArrayList<FieldInfo>();
			int index = 1;
			for(Field field:cls.getDeclaredFields()){
				if(Property.class.isAssignableFrom(field.getType())){
					fields.add(new FieldInfo(index++, field));
				}
			}
			FIELDS.put(cls, fields);
		}
		return FIELDS.get(cls);
	}

	public final Set<String> keys(){
		Class<?> cls = clazz();
		if(!KEYS.containsKey(cls)){
			Set<String> keys = new LinkedHashSet<String>();
			for(FieldInfo field:fields()){
				keys.add(field.field.getName());
			}
			KEYS.put(cls, keys);
		}
		return KEYS.get(cls);
	}

	public List<Property> properties(){
		try{
			List<Property> properties = new ArrayList<Property>();
			for(FieldInfo info:fields()){
				properties.add((Property)info.field.get(this));
			}
			return properties;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private int pindex = 0;
	protected final FieldInfo field() {
		return fields().get(pindex++);
	}
	
	public Property property(String name) {
		for(Property property:properties()){
			if(property.name().equals(name)){
				return property;
			}
		}
		return null;
	}
	
	public Set<Property> changes(){
		Set<Property> changes = new HashSet<Property>();
		for(Property property:properties()){
			if(property.changed()){
				changes.add(property);
			}
		}
		return changes;
	}
	
	public Class<? extends Bean> type() {
		return clazz();
	}
	
	public String name() {
		return clazz().getSimpleName();
	}
	
	public Boolean changed(){
		return changes().size()>0;
	}
	
	public <T extends Bean> T rollback(){
		for(Property property:changes()){
			property.rollback();
		}
		return (T) this;
	}
	public <T extends Bean> T normalize(){
		return (T) this;
	}
	public <T extends Bean> T commit(){
		validate();
		for(Property property:changes()){
			property.commit();
		}
		return (T)this;
	}
	
	public <T extends Bean> T validate() throws ValidationException {
		normalize();
		for(Property property:properties()){
			property.validate();
		}
		return (T)this;
	}
	
	public Object get(SeedView view) {
		return this;
	}
	public Bean set(SeedView view, Object value) {
		return null;
	}
	{Printer.add(new BeanPrinter());}
}

class BeanPrinter extends Printer.ObjectPrinter{

	@Override
	public boolean support(Object val) {
		return val!=null && Bean.class.isAssignableFrom(val.getClass());
	}

	@Override
	public void print(Printer.Cursor cursor, Appendable buf, Object val) throws IOException {
		Bean map = (Bean)val;
		append(buf,map.type().getSimpleName(), ANSI.Color.BLUE);
		for (Property property:map.properties()){
			cursor.enter(property.name());
			nl(buf);
			ident(buf,cursor.level());
			append(buf, property.name(), ANSI.Color.BLUE);
			append(buf, " : ");
			if(property.multiple()){
				Integer index = 0;
				append(buf,"ARRAY", ANSI.Color.MAGENTA);
				for(Object item:property){
					cursor.enter(index++);
					nl(buf);
					ident(buf,cursor.level());
					append(buf, index.toString(), ANSI.Color.MAGENTA);
					append(buf, " : ");
					Printer.print(cursor,buf,item);
					cursor.exit();
				}
			} else {
				Printer.print(cursor,buf,property.get());
			}
			cursor.exit();
		}

	}
}