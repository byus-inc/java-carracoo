package org.carracoo.beans.models;

import org.carracoo.beans.View;
import org.carracoo.beans.lang.EmailProperty;
import org.carracoo.beans.lang.StringProperty;
import org.carracoo.beans.lang.ValueProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserModel: Sergey
 * Date: 7/19/13
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserBean {
	private String id;
	private String name;
	private String email;
	private List<String> tags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
