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

public class queryAllCustomers {
	
	
	public static int delay = 1000;
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
				
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		while (true) {
			
			try {
				Date date = new Date();
				List keys = cache.getKeys();
				System.out.println(dateFormat.format(date) + " -- Found the following customer IDs ... " + keys.toString());

				Iterator it = keys.iterator();
				while (it.hasNext()) {
					// System.out.println("Getting element: " + it.toString());
					
					Element e = null;
					
					if (readThrough) {
						 e = selfPopulatingCache.get(it.next());
					} else {
						 e = cache.get(it.next());
					}
					
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
