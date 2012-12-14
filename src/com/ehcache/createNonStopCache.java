package com.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.*;

public class createNonStopCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		
		CacheManager cacheManager = new CacheManager(configuration);
		
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
