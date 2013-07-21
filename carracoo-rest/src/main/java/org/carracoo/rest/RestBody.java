package org.carracoo.rest;

import org.carracoo.utils.ANSI;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/12/13
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestBody {
	private static final Logger logger = Logger.getLogger(RestBody.class.getSimpleName());

	final
	private RestContentType type;
	public  RestContentType type(){
		return type;
	}

	private Object content;
	public  Object content(){
		return content;
	}

	private byte[] bytes;
	public  byte[] bytes(){
		if(bytes == null){
			bytes = encodeBytes();
		}
		return bytes;
	}

	private String text;
	public  String text(){
		if(text == null){
			text = encodeText();
		}
		return text;
	}

	public String length(){
		return String.valueOf(bytes().length);
	}

	public RestBody(Object content){
		this.content = content;
		if(content instanceof CharSequence){
			this.type = RestContentType.TEXT_PLANE;
		}else{
			this.type = RestContentType.APPLICATION_OCTET_STREAM;
		}
	}

	public RestBody(RestContentType type, byte[] bytes){
		this.bytes   = bytes;
		this.type    = type;
	}

	public RestBody(RestContentType type, Object content){
		this.content = content;
		this.type    = type;
	}

	protected byte[] encodeBytes(){
		if (content instanceof byte[]){
			return (byte[]) content;
		}else
		if(content instanceof CharSequence){
			return ((CharSequence)content).toString().getBytes();
		}else{
			return null;
		}
	}

	protected String encodeText(){
		if(bytes()!=null){
			try {
				return new String(bytes(),type().charset());
			} catch (UnsupportedEncodingException e) {
				logger.log(Level.WARNING,"invalid charset "+type().charset());
			}
		}
		return "";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(type()!=null && type().isText()){
			sb.append(" ");
			ANSI.append(sb, "BODY", ANSI.Color.CYAN);
			sb.append("\n");
			sb.append("  "+text().replaceAll("\n","\n  "));
		}
		return sb.toString();
	}
}
