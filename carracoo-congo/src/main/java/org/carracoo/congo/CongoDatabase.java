package org.carracoo.congo;

import com.mongodb.*;
import org.carracoo.beans.BeanService;
import org.carracoo.beans.Walker;
import org.carracoo.beans.exceptions.BeanException;

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
	private BeanService beans;
	public  BeanService beans(){
		return beans;
	}

	public CongoDatabase(BeanService beans, Walker walker){
		this(URL, beans,walker);
	}

	public CongoDatabase(String url, BeanService beans, Walker walker){
		this.walker  = walker;
		this.beans = beans;
		DB database = null;
		try {
			MongoClientURI uri = new MongoClientURI(url);
			MongoClient client = new MongoClient(uri);
			database = client.getDB(uri.getDatabase());
			DefaultDBEncoder.FACTORY = new CongoEncoder.Factory(beans.getEncoder(walker));
			DefaultDBDecoder.FACTORY = new CongoDecoder.Factory(beans.getDecoder(walker),null);
		} catch (UnknownHostException e) {
			throw new RuntimeException("Unknown Host <"+url+">",e);
		} catch (BeanException e) {
			throw new RuntimeException("Bean serviced initialization problem",e);
		} finally {
			this.mongo = database;
		}
	}

	public <T> CongoCollection<T> collection(Class<T> cls){
		try{
			return new CongoCollection<T>(this,cls);
		}catch (BeanException e) {
			throw new RuntimeException("Probably invalid bean class",e);
		}
	}

}
