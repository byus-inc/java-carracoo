/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.models;

import org.carracoo.seeds.lang.properties.DateProperty;
import org.carracoo.seeds.lang.properties.BeanProperty;
import org.carracoo.seeds.lang.properties.StringProperty;

/**
 *
 * @author Sergey
 */
public class Comment extends Document {
	public final StringProperty<Comment> message		= new StringProperty<Comment>()		{
		@Override
		public Comment parent() {
			return Comment.this;
		}
	};
	public final DateProperty<Comment> createdAt	= new DateProperty<Comment>()		{
		@Override
		public Comment parent() {
			return Comment.this;
		}
	};
	public final DateProperty<Comment> updatedAt	= new DateProperty<Comment>()		{
		@Override
		public Comment parent() {
			return Comment.this;
		}
	};
	public final BeanProperty<Comment,User> author		= new BeanProperty<Comment,User>()	{
		@Override
		public Comment parent() {
			return Comment.this;
		}
	};
}
