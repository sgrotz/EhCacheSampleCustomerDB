package com.db;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.CacheDecoratorFactory;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

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
