/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carracoo.seeds.models;

import org.carracoo.seeds.lang.properties.BeanProperty;
import org.carracoo.seeds.lang.properties.StringProperty;

/**
 *
 * @author Sergey
 */

public class Post extends Document {
	public final StringProperty<Post> id			= new StringProperty<Post>()		{
		
	};
	public final StringProperty<Post> title		= new StringProperty<Post>()		{
		
	};
	public final StringProperty<Post> content		= new StringProperty<Post>()		{
		
	};
	public final BeanProperty<Post,User> author		= new BeanProperty<Post,User>()	{
		
	};
	public final BeanProperty<Post,Comment> comments	= new BeanProperty<Post,Comment>(){
		{multiple(true);}
	};
}
