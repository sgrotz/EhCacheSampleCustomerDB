package com.ehcache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

import com.db.dbCustomerReadThrough;
import com.ehcache.objects.customer;


// This class is similar to the queryAllCustomers - but just for a single record
public class querySingleCustomer {

	// Time between querying the customer (in msec)
	public static int delay = 1000;
	
	// ID to search for (make sure it exists in the database!)
	public static int searchForID = 3951163;
	
	// Enable or disable readThrough. If enabled, it will check the underlying database, if no record can be found in the cache.
	public static boolean readThrough = false;


	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Initialize the cachemanager and cache
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");
		
		// Create new selfPopulating Cache
		SelfPopulatingCache selfPopulatingCache = null;

		// If ReadThrough is enabled, make sure to replace the existing cache with a Ódecorated" cache.
		// Doing this will tell ehCache to use the dbCustomerReadThrough class to read in objects from the DB
		if (readThrough) {
			selfPopulatingCache = new SelfPopulatingCache(cache, new dbCustomerReadThrough());	
			manager.replaceCacheWithDecoratedCache(cache, selfPopulatingCache);
		}

		System.out.println("Querying customer: " + searchForID + " every " + delay + " msecs");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		while (true) {		

			// Loop over the results ...
			try {
				Element e = null;
				
				// If readthrough is enabled, make sure to use the selfpopulating cache. If disabled, cache.get will suffice!
				if (readThrough) {
					 e = selfPopulatingCache.get(searchForID);
				} else {
					 e = cache.get(searchForID);
				}
				
					// Parse the customer object back
					customer mc = (customer) e.getObjectValue();

					Date date = new Date();
					System.out.println(dateFormat.format(date) + " - Customer: " + mc.getID() + " -- " + mc.getFIRSTNAME() + " " + mc.getLASTNAME() + " " + mc.getREGION() + " " + mc.getADDRESS());


			} catch (Exception ex) {
				System.out.println("Record " + searchForID + " does not exist ... " + ex.getClass() + " " + ex.getLocalizedMessage());
				// System.exit(0);
			}
			Thread.sleep(delay);


		}



	}

}
