package org.carracoo.beans.models;

import org.carracoo.beans.lang.StringProperty;
import org.carracoo.beans.models.properties.CommentProperty;
import org.carracoo.beans.models.properties.UserProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class PostBean {
	private String id;
	private String title;
	private UserBean owner ;
	private List<CommentBean> comments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserBean getOwner() {
		return owner;
	}

	public void setOwner(UserBean owner) {
		this.owner = owner;
	}

	public List<CommentBean> getComments() {
		return comments;
	}

	public void setComments(List<CommentBean> comments) {
		this.comments = comments;
	}
}
