package com.db;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.CacheDecoratorFactory;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

// Wrapper class, which will be called by ehCache, if a CacheDecorator was specified.
// Make sure to return the SelfPopulating Cache, if using Read Through ;)

public class dbCustomerReadFactory extends CacheDecoratorFactory {

	@Override
	public Ehcache createDecoratedEhcache(Ehcache cache, Properties arg1) {
		return new SelfPopulatingCache(cache, new dbCustomerReadThrough());
	}

	@Override
	public Ehcache createDefaultDecoratedEhcache(Ehcache arg0, Properties arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
