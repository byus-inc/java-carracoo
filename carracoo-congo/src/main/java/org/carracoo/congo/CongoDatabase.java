package org.carracoo.congo;

import com.mongodb.*;
import org.carracoo.beans.BeanMapper;
import org.carracoo.beans.Walker;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/21/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoDatabase {

	public static final String URL = "mongodb://127.0.0.1:27017/test";

	final
	private DB mongo;
	public  DB mongo(){
		return mongo;
	}

	final
	private Walker walker;
	public  Walker walker(){
		return walker;
	}

	final
	private BeanMapper mapper;
	public  BeanMapper mapper(){
		return mapper;
	}

	public CongoDatabase(BeanMapper mapper, Walker walker){
		this(URL,mapper,walker);
	}

	public CongoDatabase(String url, BeanMapper mapper, Walker walker){
		this.walker = walker;
		this.mapper = mapper;
		DB database = null;
		try {
			MongoClientURI uri = new MongoClientURI(url);
			MongoClient client = new MongoClient(uri);
			database = client.getDB(uri.getDatabase());
			DefaultDBEncoder.FACTORY = new CongoEncoder.Factory(mapper,walker);
			DefaultDBDecoder.FACTORY = new CongoDecoder.Factory(mapper,walker,null);
		} catch (UnknownHostException e) {
			throw new RuntimeException("Unknown Host <"+url+">",e);
		}finally {
			this.mongo = database;
		}
	}

	public <T> CongoCollection<T> collection(Class<T> cls){
		return new CongoCollection<T>(this,cls);
	}

}
