package org.carracoo.congo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.carracoo.json.JSON;
import org.carracoo.beans.utils.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/5/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoQuery {

	private static final String  TOKEN   = "#";
	private static final Pattern PATTERN = Pattern.compile(TOKEN);

	public static DBObject create(String json, Object... args){
		return new BasicDBObject(Query.get(json, args).getAsMap());
	}

	public static DBObject create(Query query){
		Map map = null;
		if (query.equals(Query.NONE)) {
			map = new HashMap();
		} else {
			map = query.getAsMap();
		}
		return new BasicDBObject(map);
	}

}
