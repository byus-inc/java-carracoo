package org.carracoo.beans;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanView implements Walker {

	public static class Path extends Stack<String> {
		@Override
		public synchronized String toString() {
			StringBuilder buf = new StringBuilder();
			for(String p:this){
				buf.append(".").append(p);
			}
			return buf.length()>0 ? buf.substring(1) : "";
		}
	}

	public BeanView(String name){
		this(name,new Path());
	}

	public BeanView(String url, Path path){

		String[] parts = url.split("\\?");
		String base,params;
		if(parts.length>1){
			base     = parts[0];
			params   = parts[1];
		}else{
			base     = parts[0];
			params   = null;
		}

		String[] baseParts = base.split(":");
		if(baseParts.length>1){
			this.format = baseParts[0];
			this.target = baseParts[1];
		}else{
			this.format = baseParts[0];
			this.target = "default";
		}

		if(params!=null){
			this.params = new LinkedHashMap<String,String>();
			String[] pairs = params.split("&");
			for(String pair:pairs){
				String[] entry = pair.split("=");
				this.params.put(entry[0].trim(),entry[1].trim());
			}
		}else {
			this.params = null;
		}

		this.path = path;
	}

	final
	private Map<String,String> params;

	@Override
	public String param(String param) {
		return params.get(param);
	}

	@Override
	public String param(String param, String value) {
		return params.put(param,value);
	}

	@Override
	public boolean has(String param) {
		return
			this.params !=null &&
			this.params.containsKey(param)
		;
	}

	@Override
	public boolean has(String param, String value) {
		return
			this.params !=null &&
			this.params.containsKey(param) &&
			this.params.get(param).equals(value)
		;
	}

	@Override
	public String base(){
		return format+":"+target;
	}

	final
	private String format;
	@Override
	public  String format(){
		return format;
	}

	final
	private String target;
	@Override
	public  String target(){
		return target;
	}

	final
	private Path path;
	@Override
	public  String path(){
		return path.toString();
	}

	@Override
	public boolean is(String format) {
		return this.format.equals(format);
	}

	@Override
	public boolean in(String path) {
		return path().matches(path);
	}

	@Override
	public boolean to(String target) {
		return this.target.equals(target);
	}


	public boolean isRoot() {
		return path.size()==1;
	}

	@Override
	public void enter(Integer i) {
		enter("#"+i);
	}
	@Override
	public void enter(String path) {
		this.path.push(path);
	}
	@Override
	public void exit() {
		this.path.pop();
	}

	public Walker clone(){
		BeanView v;
		if(this.params!=null){
			v = new BeanView(base()+"?%clone=TRUE");
			v.params.putAll(this.params);
			v.params.remove("%clone");
		}else{
			v = new BeanView(base());
		}
		return v;
	}
}
