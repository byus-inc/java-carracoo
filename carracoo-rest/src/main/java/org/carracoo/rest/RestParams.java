package org.carracoo.rest;

import org.carracoo.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/10/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestParams extends LinkedHashMap<String,Object> {
	@Override
	public Object put(String key, Object value) {
		return super.put(key, value);
	}

	@Override
	public String toString() {
		String str = "?";
		if(size()>0){
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (String key:keySet()){
				Object val = get(key);
				if(key.charAt(0)!='%' && val!=null){
					if(first){
						first = false;
						sb.append("?");
					}else {
						sb.append("&");
					}
					sb.append(key);
					sb.append("=");
					sb.append(StringUtils.urlEncode(val.toString()));
				}
			}
			str = sb.toString();
		}
		return str.equals("?")?"":str;
	}

	public static RestParams parse(String str){
		if(str.charAt(0)=='?'){
			str = str.substring(1);
		}
		RestParams params = new RestParams();
		String[] pairs = str.split("&");
		for(String pair:pairs){
			String[] entry = pair.split("=");
			params.put(entry[0],entry[1]);
		}
		return params;
	}
}
