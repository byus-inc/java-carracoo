package org.carracoo.congo;

import com.mongodb.*;
import org.carracoo.congo.parsing.SeedDecoderFactory;
import org.carracoo.congo.parsing.SeedEncoderFactory;
import org.carracoo.seeds.SeedView;
import org.carracoo.seeds.Seeds;
import org.carracoo.seeds.lang.Corn;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/4/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoDatabase {

	public static final String URL = "mongodb://127.0.0.1:27017/test";

	private final DB mongo;
	public  final DB mongo(){
		return mongo;
	}

	private final SeedView view;
	public  final SeedView view(){
		return view;
	}

	private final Seeds seeds;
	public  final Seeds seeds(){
		return seeds;
	}

	public CongoDatabase(Seeds seeds, SeedView view){
		this(URL,seeds,view);
	}

	public CongoDatabase(String url, Seeds seeds, SeedView view){
		this.seeds = seeds;
		this.view  = view;
		DB database = null;
		try {
			MongoClientURI uri = new MongoClientURI(url);
			MongoClient client = new MongoClient(uri);
			database = client.getDB(uri.getDatabase());
			DefaultDBEncoder.FACTORY = new SeedEncoderFactory(seeds,view,null);
			DefaultDBDecoder.FACTORY = new SeedDecoderFactory(seeds,view,null);
		} catch (UnknownHostException e) {
			throw new RuntimeException("Unknown Host <"+url+">",e);
		}finally {
			this.mongo = database;
		}
	}

	public <T extends Corn> CongoCollection<T> collection(Class<T> cls){
		return new CongoCollection<T>(this,cls);
	}
}
