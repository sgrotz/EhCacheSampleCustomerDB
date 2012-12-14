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

public class querySingleCustomer {


	public static int delay = 1000;
	public static int searchForID = 3951163;
	public static boolean readThrough = true;


	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");
		SelfPopulatingCache selfPopulatingCache = null;

		if (readThrough) {
			selfPopulatingCache = new SelfPopulatingCache(cache, new dbCustomerReadThrough());	
			manager.replaceCacheWithDecoratedCache(cache, selfPopulatingCache);
		}

		System.out.println("Querying customer: " + searchForID + " every " + delay + " msecs");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		while (true) {		

			try {
				Element e = null;
				if (readThrough) {
					 e = selfPopulatingCache.get(searchForID);
					 //e = cache.getWithLoader(searchForID, new dbCustomerReadThrough(), searchForID)
				} else {
					 e = cache.get(searchForID);
				}
				
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
