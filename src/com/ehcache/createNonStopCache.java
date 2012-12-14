package com.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.*;


// This class shows how to use the fluent interface to create a cache within your code...
// This example also uses non-stop features ...
public class createNonStopCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create a new configuration ...
		Configuration configuration = new Configuration()
			.terracotta(new TerracottaClientConfiguration().url("localhost:9510"))
			.defaultCache(new CacheConfiguration("defaultCache", 10000))
			.cache((new CacheConfiguration("nonstopCache", 10000))
				.terracotta(new TerracottaConfiguration()
					.consistency(TerracottaConfiguration.Consistency.STRONG)
					.nonstop(new NonstopConfiguration().enabled(true).timeoutMillis(4000)
						.timeoutBehavior(new TimeoutBehaviorConfiguration()
						.type(TimeoutBehaviorConfiguration.TimeoutBehaviorType.LOCAL_READS.getTypeName())))
						)
		);
		
		// Tell the cacheManager about the new cache using the configuration specified above ...
		CacheManager cacheManager = new CacheManager(configuration);
		
		// Just sleep for 30 Seconds - then exit
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
