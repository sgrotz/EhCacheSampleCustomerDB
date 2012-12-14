package com.ehcache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;

import com.db.dbCustomerReadThrough;
import com.ehcache.objects.customer;


// This class is used to query all customers in cache. This class is especially interesting when using the read-through
// functionality. Once a customer object expires in the cache, the read through should get the object back from the database. 
// Make sure the EhCache customer objects have the TTL/TTI have an expiry set :)

public class queryAllCustomers {
	
	// Delay between the queries
	public static int delay = 1000;
	
	// Enable or disable the readthrough functionality - Default: False
	public static boolean readThrough = false;

	
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Instantiate the cache manager and the cache...
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");
		
		// Create a new selfpopulating cache ...
		SelfPopulatingCache selfPopulatingCache = null;
		
		
		// If ReadThrough is enabled, make sure to replace the existing cache with a Ódecorated" cache.
		// Doing this will tell ehCache to use the dbCustomerReadThrough class to read in objects from the DB
		if (readThrough) {
			selfPopulatingCache = new SelfPopulatingCache(cache, new dbCustomerReadThrough());	
			manager.replaceCacheWithDecoratedCache(cache, selfPopulatingCache);
		}
				
		// Specify the date format used in the log outputs
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		while (true) {
			try {
				Date date = new Date();
				
				// Get all keys from the cache
				// Keep in mind, this is a server side call - it wont work, if non-stop is enabled.
				List keys = cache.getKeys();
				System.out.println(dateFormat.format(date) + " -- Found the following customer IDs ... " + keys.toString());

				Iterator it = keys.iterator();
				while (it.hasNext()) {
					
					// Loop through the results and print them ... 
					
					Element e = null;
					
					if (readThrough) {
						 e = selfPopulatingCache.get(it.next());
					} else {
						 e = cache.get(it.next());
					}
					
					// Parse the results back into a customer object...
					customer mc = (customer) e.getObjectValue();
					System.out.println(dateFormat.format(date) + " -- Customer: " + mc.getID() + " -- " + mc.getFIRSTNAME() + " " + mc.getLASTNAME() + " " + mc.getREGION() + " " + mc.getADDRESS());

				}

			} catch (Exception ex) {
				System.out.println("Customer not found ... Object expired before it could be collected..." + ex.getClass());

			}
			Thread.sleep(delay);

		}
		
		
		
	}

}
