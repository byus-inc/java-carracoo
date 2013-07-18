package org.carracoo.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/11/13
 * Time: 7:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tracer {


	public static void printLocation(){
		System.out.println(getLocation("{CLASS}.{METHOD} {FILE}:{LINE}",3));
	}

	public static void printLocation(String template){
		System.out.println(getLocation(template,3));
	}

	public static String getLocation(){
		return getLocation("{CLASS}.{METHOD} {FILE}:{LINE}",3);
	}

	public static String getLocation(String template){
		return getLocation(template,3);
	}

	public static String getLocation(String template,int level){
		String str = template;
		StackTraceElement loc = getStackTraceElement(level);
		str = str.replaceAll("\\{CLASS\\}",loc.getClassName());
		str = str.replaceAll("\\{METHOD\\}",loc.getMethodName());
		str = str.replaceAll("\\{LINE\\}",""+loc.getLineNumber());
		str = str.replaceAll("\\{FILE\\}",loc.getFileName());
		return str;
	}

	public static StackTraceElement getStackTraceElement(int level){
		try{
			throw new Exception();
		}catch(Exception e){
			return e.getStackTrace()[level];
		}
	}
}
