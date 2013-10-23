package org.carracoo.stats;


import org.carracoo.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;


public class Stats {
	
	private static Boolean enabled = true;
	
	public static void enable(){
		enabled = true;
	}
	
	public static void disable(){
		enabled = false;
	}
	
	public static void reset(){
		StatInfo.clear();
	}
	
	public static Boolean isEnabled(){
		return enabled;
	}

	public static String getTrackingString(){
		Map<String,Object> data = getTrackingMap();
		
		int keyMaxLength = 0;
		for(String key:data.keySet()){
			keyMaxLength = Math.max(keyMaxLength, key.length());
		}
		
		String res = "STATS"+ StringUtils.repeat(" ", keyMaxLength - 5)+"       HPS      HITS     FAILS       SUM       MIN       MAX       AVG\n";
		res += StringUtils.repeat("-", keyMaxLength)+"----------------------------------------------------------------------\n";
		for(Map.Entry<String, Object> entry:data.entrySet()){
			res+=formatRow(entry.getKey(),entry.getValue(),keyMaxLength)+"\n";
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private static String formatRow(String key, Object value, int keyMaxLength){
		String res = "";
		Map<String, Object> data = (Map<String, Object>)value;
		res += key+ StringUtils.repeat(" ", keyMaxLength - key.length());
		for(Map.Entry<String, Object> entry:data.entrySet()){
			String item = "";
			if(entry.getValue() instanceof Long){
				item = entry.getValue().toString();
				item = item.substring(0,Math.min(item.length(), 9));
			}
			if(entry.getValue() instanceof Double){
				item = new DecimalFormat("#.####").format(entry.getValue());
				item = item.substring(0,Math.min(item.length(), 9));
			}
			item = StringUtils.repeat(" ", 9 - item.length())+item;
			res+=" "+item;
		}
		return res;
	}
	
	public static Map<String,Object> getTrackingMap(String search){
		Map<String,Object> events 	= new TreeMap<String,Object>();
		for(StatInfo info:StatInfo.list().values()){
			Map<String, Object> infoMap	= new LinkedHashMap<String, Object>();
			String key = info.getName()
				.replace("T$", "Times.")
				.replace("I$", "Items.")
				.replace("B$", "Data.")
			;
			if(search.equals("*") || key.indexOf(search)>=0){
				infoMap.put("hps", 		info.getHps());
				infoMap.put("hits", 	info.getHits());
				infoMap.put("fails",	info.getFails());
				infoMap.put("sum", 		info.getSum());
				infoMap.put("min",		info.getMin());
				infoMap.put("max", 		info.getMax());
				infoMap.put("avg", 		info.getAvg());
				events.put(key,infoMap);
			}
		}
		return events;
	}
	public static Map<String,Object> getTrackingMap() {
		return getTrackingMap("*");
	}	
	public static StatEvent track(){
		return track("UNKNOWN");
	}
	
	public static void track(String name, Integer value, String units){
		track(name,value.doubleValue(),units);
	}
	
	public static void track(String name, Double value, String units){
		track(units+"."+name).finish(value);
	}
	
	public static StatEvent track(String name){
		if(isEnabled()){
			return new StatEvent(name);
		}
		return new StatEvent(false);
	}

	public static boolean register(Class<?> cls){
		System.out.println("TRACKING "+cls);
		return true;
	}

	public static String getClassNameString(Object document) {
		if(document==null){
			return "__null";
		}
		Class<?> cls = (document instanceof Class)?(Class<?>)document:document.getClass();
		return cls.getName()
			.replace(".", "_")
			.replace("$", "__")
		;		
	}

	public static StatEvent trackBytes(String string) {
		return track("B$"+string);
	}
	
	public static StatEvent trackItem(String string) {
		return track("I$"+string);
	}

	private final static Map<Integer, StatEvent> store = new ConcurrentHashMap<Integer, StatEvent>();
	public static void start(Integer id, String name){
		store.put(id,track(name));
	}

	public static void finish(Integer id, boolean b) {
		store.remove(id).finish(b);
	}
}
