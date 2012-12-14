package com.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

// Use this class as an example to flush a cache programmatically...

public class clearCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create a new connection to Terracotta through the cachemanager
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		
		// Initialize the customer cache ...
		Cache cache = manager.getCache("customers");
		
		// Flush the cache ...
		cache.flush();
		
		System.out.println("Flushed customer cache ... ");
		
		
	}

}
