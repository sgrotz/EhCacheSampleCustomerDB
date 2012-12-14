package com.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class clearCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		
		Cache cache = manager.getCache("customers");
		cache.flush();
		System.out.println("Flushed customer cache ... ");
		
		
	}

}
