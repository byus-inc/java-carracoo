package org.carracoo.facebook;

import org.carracoo.rest.RestBody;
import org.carracoo.rest.RestContentType;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/30/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacebookBody extends RestBody {

	public FacebookBody(RestContentType type, byte[] bytes){
		super(type,bytes);
	}

	public FacebookBody(RestContentType type, Object content){
		super(type,content);
	}

	public FacebookBody(Object content){
		super(content);
	}
}
