package com.ehcache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

public class searchCustomersByRegion {

	public static String region = "US";
	public static int delay = 10000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");

		System.out.println("Searching for all customers in region " + region);

		Attribute<String> FIRSTNAME = cache.getSearchAttribute("FIRSTNAME");
		Attribute<String> LASTNAME = cache.getSearchAttribute("LASTNAME");
		Attribute<String> REGION = cache.getSearchAttribute("REGION");
		
		while (true) {
			Results results = cache
					.createQuery()
					.includeKeys()
					//.includeValues()
					.addCriteria(REGION.eq(region)
							//.and(FIRSTNAME.eq("Sepp"))
							)
							.execute();

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
