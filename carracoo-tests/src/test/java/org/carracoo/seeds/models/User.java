/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.models;

import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.lang.properties.DateProperty;
import org.carracoo.seeds.lang.properties.StringProperty;

import java.util.Date;

/**
 *
 * @author Sergey
 */
public class User extends Document {
	
	public final StringProperty<User> id			= new StringProperty<User>()	{
		
	};
	public final StringProperty<User> name		= new StringProperty<User>()	{
		{required(true);multiple(false);}
	};
	public final StringProperty<User> password	= new StringProperty<User>()	{
		@Override
		public User parent() {
			return User.this;
		}

		@Override
		public boolean available(SeedView view) {
			return view.is("bson");
		}
		
	};
	public final StringProperty<User> email		= new StringProperty<User>()	{
		@Override
		public User parent() {
			return User.this;
		}
		@Override
		public boolean available(SeedView view) {
			return view.isRoot() || view.path().match("author.email");
		}
	};
	public final DateProperty<User> createdAt	= new DateProperty<User>()		{
		{set(new Date());}
		@Override
		public boolean available(SeedView view) {
			return view.isRoot() || view.path().match("author.email");
		}
	};
	public final DateProperty<User> updatedAt	= new DateProperty<User>()		{
		{set(new Date());}
		@Override
		public boolean available(SeedView view) {
			return view.isRoot() || view.path().match("author.email");
		}
	};	
	public final StringProperty<User> links		= new StringProperty<User>()	{
		{multiple(true);}
		@Override
		public User parent() {
			return User.this;
		}
		
		@Override
		public boolean available(SeedView view) {
			return view.isRoot();
		}
	};
	
	@Override
	public Object get(SeedView view) {
		if(view.path().match("Post:Comment:comments.#\\d+.Comment:User:author")){
			return id.get();
		}else{
			return super.get(view);
		}
	}

	@Override
	public User   set(SeedView view, Object value) {
		if(value instanceof String){
			id.set((String)value);return this;
		}else{
			return null;
		}
	}
	
}
