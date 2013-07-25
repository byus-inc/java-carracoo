package org.carracoo.rest;

import org.carracoo.utils.ANSI;
import org.carracoo.utils.StringUtils;

import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestHeaders extends LinkedHashMap<String,Object> {

	public RestHeaders(){
		super();
	}

	public RestHeaders(int size){
		super(size);
	}

	@Override
	public String toString() {
		String str = "";
		if(size()>0){
			StringBuilder sb = new StringBuilder();
			sb.append(" ");
			ANSI.append(sb, "HEADERS", ANSI.Color.CYAN);
			sb.append("\n");
			for (String key:keySet()){
				Object val = get(key);
				if(val!=null){
					sb.append("  ");
					ANSI.append(sb,key,ANSI.Color.YELLOW);
					sb.append(": ");
					sb.append(val.toString());
					sb.append("\n");
				}
			}
			str = sb.substring(0,sb.length()-1).toString();
		}
		return str;
	}
}
