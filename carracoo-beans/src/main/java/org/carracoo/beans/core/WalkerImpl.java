package org.carracoo.beans.core;

import org.carracoo.beans.Walker;

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
public class WalkerImpl implements Walker {

	public static class Path extends Stack<String> {
		public synchronized String string(Boolean includeArrays) {
			StringBuilder buf = new StringBuilder();
			for(String p:this){
				if(includeArrays || !p.startsWith("#")){
					buf.append(".").append(p);
				}
			}
			return buf.length()>0 ? buf.substring(1) : "";
		}
	}

	public WalkerImpl(){
		this("default:Default");
	}

	public WalkerImpl(String name){
		this(name,new Path());
	}

	public WalkerImpl(String url, Path path){

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
			this.target = baseParts[0];
			this.format = baseParts[1];
		}else{
			this.target = baseParts[0];
			this.format = "default";
		}

		if(params!=null){
			this.params = new LinkedHashMap<String,Object>();
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
	private Map<String,Object> params;

	@Override
	public Object param(String param) {
		return params.get(param);
	}

	@Override
	public Object param(String param, Object value) {
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
	public boolean has(String param, Object value) {
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
		return path.string(false);
	}

	@Override
	public  String path(Boolean includeArrays){
		return path.string(includeArrays);
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
	public boolean inRoot() {
		return in("")||in("#\\d+");
	}

	@Override
	public boolean inArray() {
		return in(".*#\\d+.*");
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
		WalkerImpl v;
		if(this.params!=null){
			v = new WalkerImpl(base()+"?%clone=TRUE");
			v.params.putAll(this.params);
			v.params.remove("%clone");
		}else{
			v = new WalkerImpl(base());
		}
		return v;
	}
}
