package org.carracoo.congo;

import com.mongodb.DBCollection;
import org.carracoo.beans.BeanDefinition;
import org.carracoo.beans.BeanException;
import org.carracoo.beans.lang.IdentifierProperty;
import org.carracoo.beans.lang.ValueProperty;
import org.carracoo.beans.utils.Query;
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
		try {
			if(definition!=null){
				ValueProperty property = definition.getProperty("id");
				if(property instanceof IdentifierProperty){
					property = database().mapper().getFactory().getProperty(model, "id");
					((IdentifierProperty)property).generate();
				}
				mongo().save(new CongoObject(model,definition));
			}else{
				mongo().save(new CongoObject(model));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void drop(){
		mongo().drop();
	}

	public void remove(String query, Object... args){
		remove(Query.get(query, args));
	}

	public void remove(Query query){
		mongo().remove(CongoQuery.create(query));
	}

	public T get(String query, Object... args){
		return get(Query.get(query, args));
	}

	public T get(Query query){
		return find(query).skip(0).limit(1).first();
	}

	public long count(){
		return count("{}");
	}

	public long count(String query, Object... args){
		return count(Query.get(query, args));
	}

	public long count(Query query){
		return mongo().count(CongoQuery.create(query));
	}

	public CongoResult<T> find(String query, Object... args){
		return find(Query.get(query, args));
	}

	public CongoResult<T> find(Query query){
		return new CongoResult<T>(mongo().find(CongoQuery.create(query)));
	}

	public CongoResult<T> findOnly(String query, String keys, Object... args){
		return findOnly(Query.get(query, args), keys);
	}

	public CongoResult<T> findOnly(Query query, String keys){
		return new CongoResult<T>(mongo().find(CongoQuery.create(query), CongoQuery.create(keys)));
	}

}
