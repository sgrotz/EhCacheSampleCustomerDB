package com.ehcache;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ehcache.objects.customer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


// Use this class to create a new customer within the cache. With the useWriter Method, you can also 
// make sure that each new customer is synchronized with the underlying database. If disabled, only the cache is updated.
// The cache-through or cache-write-behind strategy is configured in the ehcache.xml file.


public class createCustomerInCache {

	// RandomValue is used to create a random ID for customers
	private static int randomValue = 10000000;
	
	// If enabled, the writer will also persist changes to the underlying database. Default: false
	private static boolean useWriter = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Count - how many cutomers to be created by each thread...
		final int count = 1000;
		
		// How many threads to use to create customers ...
		int threadCount = 33;

		
		// Main part - create threads, each creating customers ...
		Thread[] threads = new Thread[threadCount];
		for (int i = 0; i < threads.length; i++) {

			final int current = i;
			threads[i] = new Thread() {

				public void run() {
					for (int i = 0; i < count; i++) {
						createCustomer(getRandomInt(), getRandomFirstName(), getRandomLastName(), getRandomRegion(), "Inserted through a cache entry ...");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		



	}

	
	// Random ID Generator
	public static int getRandomInt() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(randomValue);

	}

	
	// Random Regions
	public static String getRandomRegion() {
		List<String> regions = new LinkedList<String>();
		regions.add("DE");
		regions.add("US");
		regions.add("AU");
		regions.add("UK");
		regions.add("AT");
		regions.add("CH");

		Random rand = new Random();
		int choice = rand.nextInt(regions.size());
		return regions.get(choice);

	}

	// Random First Names
	public static String getRandomFirstName() {
		List<String> regions = new LinkedList<String>();
		regions.add("Sepp");
		regions.add("Hans");
		regions.add("Chris");
		regions.add("Charles");
		regions.add("Dirk");
		regions.add("Fabian");

		Random rand = new Random();
		int choice = rand.nextInt(regions.size());
		return regions.get(choice);

	}
	
	

	// Random Last Names
	public static String getRandomLastName() {
		List<String> regions = new LinkedList<String>();
		regions.add("Mustermann");
		regions.add("Huber");
		regions.add("Maier");
		regions.add("Peytier");
		regions.add("Berger");
		regions.add("Gruber");

		Random rand = new Random();
		int choice = rand.nextInt(regions.size());
		return regions.get(choice);

	}

	// Create customer method, performing the cache.put operation
	public static void createCustomer(int ID, String FIRSTNAME, String LASTNAME, String REGION, String ADDRESS ) {

		// Initialize the cachemanager and cache ...
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");

		// Create a new customer
		customer mc = new customer();

		mc.setID(ID);
		mc.setFIRSTNAME(FIRSTNAME);
		mc.setLASTNAME(LASTNAME);
		mc.setREGION(REGION);
		mc.setADDRESS(ADDRESS); 
		
		// Create a new element and map the customer into it...
		Element element = new Element(ID, mc);

		// If Writer is enabled, use the putWithWriter Method - otherwise cache.put
		if (useWriter) {
			cache.putWithWriter(element);
		} else {
			cache.put(element);
		}	

	}

}
