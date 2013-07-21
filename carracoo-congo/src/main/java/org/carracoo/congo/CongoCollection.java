package org.carracoo.congo;

import com.mongodb.DBCollection;
import org.carracoo.beans.BeanDefinition;
import org.carracoo.beans.BeanException;
import org.carracoo.beans.stl.ValueProperty;
import org.carracoo.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/21/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoCollection <T>{
	private final BeanDefinition definition;
	public CongoCollection(CongoDatabase database, Class<T> model){
		this.database   = database;
		this.model      = model;

		this.mongo      = database.mongo().getCollection(
			StringUtils.toUnderscoredNotation(model.getSimpleName())
		);
		this.mongo.setDBDecoderFactory(new CongoDecoder.Factory(
			database.mapper(), database.walker(), model
		));
        this.mongo.setDBEncoderFactory(new CongoEncoder.Factory(
            database.mapper(), database.walker()
        ));
        BeanDefinition definition;
		try {
			definition = database()
				.mapper()
				.getFactory()
				.getDefinition(model)
			;
		}catch (BeanException ex){
			definition =null;
		}
		this.definition = definition;
	}

	private final CongoDatabase database;
	public  final CongoDatabase database(){
		return database;
	}

	private final Class<T>      model;
	public  final Class<T>      model(){
		return model;
	}

	private final DBCollection mongo;
	public  final DBCollection mongo(){
		return mongo;
	}

	public void save(T model){
		if(definition!=null){
			ValueProperty property = definition.getProperty("id");
			if(property.options instanceof CongoId){
				((CongoId)property.options).generate(property);
			}
			mongo().save(new CongoObject(model,definition));
		}else{
			mongo().save(new CongoObject(model));
		}
	}

	public void drop(){
		mongo().drop();
	}

	public void remove(String query, Object... args){
		mongo().remove(CongoQuery.create(query, args));
	}

	public T get(String query, Object... args){
		return find(query, args).skip(0).limit(1).first();
	}

	public long count(){
		return count("{}");
	}
	public long count(String query, Object... args){
		return mongo().count(CongoQuery.create(query, args));
	}

	public CongoResult<T> find(String query, Object... args){
		return new CongoResult<T>(mongo().find(CongoQuery.create(query, args)));
	}

	public CongoResult<T> findOnly(String query, String keys, Object... args){
		return new CongoResult<T>(mongo().find(CongoQuery.create(query, args), CongoQuery.create(keys)));
	}

}
