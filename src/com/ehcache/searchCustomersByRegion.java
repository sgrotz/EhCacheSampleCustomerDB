package com.ehcache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;


// Sample class to show the search-query functionality of ehCache
// Search by region across all customers ... 

public class searchCustomersByRegion {

	// Define the region to search for ...
	public static String region = "US";
	
	// Time between queries (in msec)
	public static int delay = 10000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Initialize CacheManager and Cache
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");

		System.out.println("Searching for all customers in region " + region);

		// Get searchable fields - should be identical to the values specified in the ehcache.xml file!
		Attribute<String> FIRSTNAME = cache.getSearchAttribute("FIRSTNAME");
		Attribute<String> LASTNAME = cache.getSearchAttribute("LASTNAME");
		Attribute<String> REGION = cache.getSearchAttribute("REGION");
		
		while (true) {
			// Create a new query
			Results results = cache
					.createQuery()
					.includeKeys()
					// You can also include the values in the query - which will collect all items. 
					// For large queries it may make sense to not get the values and collect them in a second query, 
					// As this uses the local cached objects, instead of getting everything from the TSA.
					//.includeValues()
					.addCriteria(REGION.eq(region)
							// More search parameters can be specified ...
							//.and(FIRSTNAME.eq("Sepp"))
							)
							.execute();

			
			// Sample code to also inspect the resultsets ... 
			//List<Result> keys = results.all();

			//for (int i = 0; i < results.size(); i++) {
			//	System.out.println("Customer found: " + keys.get(i));
			//}
			
			System.out.println("Records found: " + results.toString());
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
