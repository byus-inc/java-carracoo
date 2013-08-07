package org.carracoo.beans.lang;

import org.carracoo.beans.Property;
import org.carracoo.beans.View;

public class EnumProperty<V extends Enum> extends Property<V> {
	public static class Options extends Property.Options implements View.ValueEncoder, View.ValueDecoder {
		public boolean numeric = true;
		@Override
		public Object encode(View view, Property property, int index) {
			Enum en = (Enum) property.get(index);
			if(en!=null){
				if(numeric){
					return en.ordinal();
				}else{
					return en.name();
				}
			}else {
				return null;
			}
		}

		@Override
		public Object decode(View view, Property property, Object item, int index) {
			Object[] values = property.options.type.getEnumConstants();
			if(item instanceof Number){
				return values[((Number) item).intValue()];
			}else
			if(item instanceof String){
				for(Object value:values){
					if(((Enum)value).name().equals((String)item)){
						return value;
					}
				}
			}
			return null;
		}
	}
}
