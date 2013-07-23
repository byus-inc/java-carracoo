package org.carracoo.beans.models;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/23/13
 * Time: 4:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommentBean {

	private UserBean author ;
	private String message ;

	public UserBean getAuthor() {
		return author;
	}

	public void setAuthor(UserBean author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
