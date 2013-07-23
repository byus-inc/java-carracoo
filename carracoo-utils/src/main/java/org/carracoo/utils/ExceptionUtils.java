package org.carracoo.utils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/23/13
 * Time: 8:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionUtils {
	private static class ExceptionMapper {

		private final Throwable throwable;
		private final Stack<Throwable> path;
		private final String template;

		public ExceptionMapper(Throwable throwable){
			this(throwable,null);
		}

		public ExceptionMapper(Throwable throwable,String template){
			this.throwable = throwable;
			this.template  = template;
			this.path = new Stack<Throwable>();
		}

		public Map toMap() {
			return toMap(throwable);
		}

		public Map toMap(Throwable throwable) {

			Map<String,Object> map = null;
			if(throwable!=null){
				this.path.push(throwable);
				map = new LinkedHashMap<String,Object>();
				map.put("type",throwable.getClass().getName());
				map.put("message",throwable.getMessage());
				map.put("stack",getStack(throwable));
				if(throwable.getCause()!=null){
					if (this.path.contains(throwable.getCause())){
						map.put("cause","Cyclic Reference to parrent");
					}else{
						map.put("cause",toMap(throwable.getCause()));
					}
				}
				this.path.pop();
				return map;
			}else {
				return null;
			}
		}

		public List getStack(Throwable throwable) {
			StackTraceElement[] elements = throwable.getStackTrace();
			List<String> stack = new ArrayList<String>();
			for(StackTraceElement element:elements){
				stack.add(renderElement(element));
			}
			return stack;
		}

		public String renderElement(StackTraceElement loc){
			try{
				String str   = template;
				String cName = loc.getClassName().replaceAll("\\$","\\\\\\$");
				String mName = loc.getClassName().replaceAll("\\$","\\\\\\$");
				String line  = String.valueOf(loc.getLineNumber());
				String file  = loc.getClassName().replaceAll("\\$","\\\\\\$");
				str = str.replaceAll("\\{CLASS\\}",cName);
				str = str.replaceAll("\\{METHOD\\}",mName);
				str = str.replaceAll("\\{LINE\\}",line);
				str = str.replaceAll("\\{FILE\\}",file);
				return str;
			}catch(Exception ex){
				return loc.toString();
			}

		}

	}

	public static Map toMap(Throwable throwable) {
		return  new ExceptionMapper(throwable).toMap();
	}

	public static Map toMap(Throwable throwable,String template) {
		return  new ExceptionMapper(throwable,template).toMap();
	}

}
