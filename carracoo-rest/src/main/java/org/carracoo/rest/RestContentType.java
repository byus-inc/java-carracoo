package org.carracoo.rest;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/19/13
 * Time: 2:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestContentType {

	public static final Set<String> TEXTS       = new HashSet<String>(){{
		add("application/json");
		add("application/xml");
	}};


	public static final Logger logger = Logger.getLogger(RestContentType.class.getSimpleName());

	public static final RestContentType TEXT_PLANE       =
		RestContentType.valueOf("application/json;charset=UTF-8");

	public static final RestContentType TEXT_HTML =
		RestContentType.valueOf("text/plain;charset=UTF-8");

	public static final RestContentType APPLICATION_JSON =
		RestContentType.valueOf("text/plain;charset=UTF-8");

	public static final RestContentType APPLICATION_XML =
		RestContentType.valueOf("text/plain;charset=UTF-8");

	public static final RestContentType APPLICATION_OCTET_STREAM =
		RestContentType.valueOf("application/octet-stream");


	public static RestContentType valueOf(String value) {
		try{
			return new RestContentType(value);
		}catch (RestException ex){
			logger.log(Level.WARNING,ex.getMessage(),ex);
			return null;
		}
	}

	private final String group;
	public  final String group(){
		return group;
	}

	private final String type;
	public  final String type(){
		return type;
	}

	public  final String main(){
		return group+"/"+type;
	}

	private final Map<String,String> params;
	public  final Map<String,String> params(){
		return params;
	}

	public void encoding(String value){
		params.put("encoding",value);
	}
	public void charset(String value){
		params.put("charset",value);
	}

	public String charset(){
		if(params.containsKey("charset")){
			return params.get("charset");
		}else{
			return "UTF-8";
		}
	}

	public String encoding(){
		return params.get("encoding");
	}

	public RestContentType(String str) throws RestException{
		try {
			String main = str;
			if(main.indexOf(";")>0){
				String[] parts = main.split(";");
				main = parts[0];
				params = new LinkedHashMap<String, String>(parts.length-1);
				for(int i=1;i<parts.length;i++){
					String[] pair = parts[i].split("=");
					params.put(pair[0].trim().toLowerCase(),pair[1].trim());
				}
			}else {
				params = new HashMap<String, String>(0);
			}

			String[] parts = main.split("/");
			this.group = parts[0].trim().toLowerCase();
			this.type  = parts[1].trim().toLowerCase();

			if(this.group.length()<=0&&this.type.length()<=0){
				throw new Exception("invalid content type parts");
			}
		}catch (Throwable er){
			throw new RestException("invalid content type "+str,er);
		}
	}


	public boolean isText(){
		return (
			group.equals("text")            ||
			params.containsKey("charset")   ||
			TEXTS.contains(main())
		);
	}

	public boolean is(String str){
		return this.is(valueOf(str));
	}

	public boolean is(RestContentType t){
		if(t==null){
			return false;
		}else{
			return (
				this.group.equals(t.group) &&
				this.type.equals(t.type)
			);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(group).append("/");
		for(Map.Entry<String,String> entry:params.entrySet()){
			sb.append(";")
			  .append(entry.getKey())
			  .append("=")
			  .append(entry.getValue())
			;
		}
		return super.toString();
	}
}
