package org.carracoo.lruc;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey
 * Date: 9/8/13
 * Time: 3:08 AM
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

public class Cache<K, T> {

	private long ttl;
	private LRUMap cache;

	protected class CacheObject {
		public long lastAccessed = System.currentTimeMillis();
		public T value;
		protected CacheObject(T value) {
			this.value = value;
		}
	}

	public Cache(long timeToLive, final long timerInterval, int maxItems) {
		this.ttl = timeToLive * 1000;

		cache = new LRUMap(maxItems);

		if (ttl > 0 && timerInterval > 0) {

			Thread t = new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(timerInterval * 1000);
						} catch (InterruptedException ex) {
						}
						cleanup();
					}
				}
			});

			t.setDaemon(true);
			t.start();
		}
	}

	public void put(K key, T value) {
		synchronized (cache) {
			cache.put(key, new CacheObject(value));
		}
	}

	@SuppressWarnings("unchecked")
	public T get(K key) {
		synchronized (cache) {
			CacheObject c = (CacheObject) cache.get(key);

			if (c == null)
				return null;
			else {
				c.lastAccessed = System.currentTimeMillis();
				return c.value;
			}
		}
	}

	public void remove(K key) {
		synchronized (cache) {
			cache.remove(key);
		}
	}

	public int size() {
		synchronized (cache) {
			return cache.size();
		}
	}

	@SuppressWarnings("unchecked")
	public void cleanup() {

		long now = System.currentTimeMillis();
		ArrayList<K> deleteKey = null;

		synchronized (cache) {
			MapIterator itr = cache.mapIterator();

			deleteKey = new ArrayList<K>((cache.size() / 2) + 1);
			K key = null;
			CacheObject c = null;

			while (itr.hasNext()) {
				key = (K) itr.next();
				c = (CacheObject) itr.getValue();

				if (c != null && (now > (ttl + c.lastAccessed))) {
					deleteKey.add(key);
				}
			}
		}

		for (K key : deleteKey) {
			synchronized (cache) {
				cache.remove(key);
			}

			Thread.yield();
		}
	}
}