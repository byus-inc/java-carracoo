package org.carracoo.congo;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.carracoo.bson.BSON;
import org.carracoo.congo.parsing.SeedDecoderFactory;
import org.carracoo.congo.parsing.SeedEncoderFactory;
import org.carracoo.congo.parsing.SeedObject;
import org.carracoo.congo.parsing.SeedQuery;
import org.carracoo.json.JSON;
import org.carracoo.seeds.lang.Corn;
import org.carracoo.seeds.lang.Grain;
import org.carracoo.seeds.lang.grains.IDENTIFIER;
import org.carracoo.utils.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoCollection<T extends Corn>{

	public CongoCollection(CongoDatabase database, Class<T> model){
		this.database   = database;
		this.model      = model;
		this.mongo      = database.mongo().getCollection(
			StringUtils.toUnderscoredNotation(model.getSimpleName())
		);
		this.mongo.setDBDecoderFactory(new SeedDecoderFactory(database.seeds(),database.view(),model));
        this.mongo.setDBEncoderFactory(new SeedEncoderFactory(database.seeds(),database.view(),model));
	}

	private final CongoDatabase database;
	public  final CongoDatabase database(){
		return database;
	}

	private final Class<T>      model;
	public  final Class<T>      model(){
		return model;
	}

	private final DBCollection  mongo;
	public  final DBCollection  mongo(){
		return mongo;
	}

	public void save(T model){
		Grain id = model.property("id");
		if(id!=null && id instanceof IDENTIFIER){
			((IDENTIFIER)id).generate();
		}
		mongo().save(new SeedObject(model));
	}

	public void drop(){
		mongo().drop();
	}

	public void remove(String query, Object... args){
		mongo().remove(SeedQuery.create(query,args));
	}

	public T get(String query, Object... args){
		return find(query, args).skip(0).limit(1).first();
	}

	public long count(){
		return count("{}");
	}
	public long count(String query, Object... args){
		return mongo().count(SeedQuery.create(query,args));
	}

	public CongoResult<T> find(String query, Object... args){
		return new CongoResult<T>(mongo().find(SeedQuery.create(query,args)));
	}

	public CongoResult<T> findOnly(String query, String keys, Object... args){
		return new CongoResult<T>(mongo().find(SeedQuery.create(query,args),SeedQuery.create(keys)));
	}

}
