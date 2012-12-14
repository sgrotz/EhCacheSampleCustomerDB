package com.db;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.CacheWriterFactory;


// Sample CacheWriterFactory, which overwrites the cache with a write-through or write-behind cache. 

public class createCustomerFactory extends CacheWriterFactory{

	@Override
	public CacheWriter createCacheWriter(Ehcache arg0, Properties arg1) {
		// TODO Auto-generated method stub
		return new createCustomerClass();
	}

}
