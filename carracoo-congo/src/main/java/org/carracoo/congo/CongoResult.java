package org.carracoo.congo;

import com.mongodb.DBCursor;
import org.carracoo.beans.utils.Query;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 7/5/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class CongoResult<T> implements Iterable<T>{
	private static class CongoIterator<I> implements Iterator<I>{

		private final DBCursor cursor;
		public CongoIterator(DBCursor cursor){
			this.cursor = cursor;
		}

		@Override
		public boolean hasNext() {
			return cursor.hasNext();
		}

		@Override
		public I next() {
			Object obj = cursor.next();
			if(obj instanceof CongoObject){
				obj = ((CongoObject)obj).target();
			}
			return (I) obj;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final DBCursor cursor;
	public CongoResult(DBCursor cursor){
		this.cursor = cursor;
	}

	@Override
	public Iterator<T> iterator() {
		return new CongoIterator<T>(cursor);
	}

	public T first(){
		Iterator<T> iterator = iterator();
		if (!iterator.hasNext()) {
			return null;
		}
		T next = iterator.next();
		close();
		return next;
	}

	public CongoResult<T> limit(int limit){
		cursor.limit(limit); return this;
	}

	public CongoResult<T> sort(String string, Object... args){
		sort(Query.get(string, args)); return this;
	}

	public CongoResult<T> sort(Query query){
		cursor.sort(CongoQuery.create(query));return this;
	}

	public CongoResult<T> skip(int limit){
		cursor.skip(limit);return this;
	}

	public void close(){
		cursor.close();
	}
}
