package org.carracoo.beans.exceptions;

import org.carracoo.beans.View;
import org.carracoo.beans.Walker;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/20/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanValidationException extends Exception{

	public static class Error {

		final public String message;
		final public String path;
		final public String type;

		private Error(String type,String path, String message){
			this.type    = type;
			this.path    = path;
			this.message = message;
		}

		public static Error get(View view, String type, String message){
			return new Error(type, view.path(), message);
		}
	}

	private final List<Error> errors;
	public List<Error> getErrors() {
		return errors;
	}
	public BeanValidationException(View view, String type, String message){
		this(Error.get(view, type, message));
	}
	public BeanValidationException(Error error){
		super("validation failed in "+error.path+" with type "+error.type+" and message '"+error.message+"'");
		this.errors = new ArrayList<Error>();
		this.errors.add(error);
	}

	public BeanValidationException(List<Error> errors){
		super("Invalid bean object");
		this.errors = new ArrayList<Error>();
		this.errors.addAll(errors);
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.getMessage()).append("\n");
		for (Error error:errors){
			sb.append("  ").append(error.type).append(" ").append(error.path).append("\n");
			sb.append("    ").append(error.message).append("\n");
		}
		return sb.toString();
	}
}
