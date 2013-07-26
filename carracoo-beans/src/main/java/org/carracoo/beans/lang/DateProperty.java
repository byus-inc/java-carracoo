package org.carracoo.beans.lang;

import org.carracoo.beans.Property;
import org.carracoo.beans.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProperty extends Property<Date> {
	public static class Options extends Property.Options implements View.ValueEncoder, View.ValueDecoder{

		public DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public boolean numeric = true;

		@Override
		public Object encode(View view, Property property, int index) {
			Date date = (Date) property.get(index);
			if(date!=null){
				if(numeric){
					return date.getTime();
				}else{
					return format.format(date);
				}
			}else {
				return null;
			}
		}

		@Override
		public Object decode(View view, Property property, Object item, int index) {
			if(item instanceof Number){
				return new Date(((Number)item).longValue());
			}else
			if(item instanceof String){
				try {
					return format.parse((String)item);
				} catch (ParseException e) {
					throw new RuntimeException("Invalid Date Property",e);
				}
			}
			return null;
		}
	}
}
