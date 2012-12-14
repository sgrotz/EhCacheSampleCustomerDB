package com.ehcache;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ehcache.objects.customer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class createCustomerInCache {

	private static int randomValue = 10000000;
	private static boolean useWriter = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		final int count = 1000;
		int threadCount = 33;

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


	public static int getRandomInt() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(randomValue);

	}

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

	public static void createCustomer(int ID, String FIRSTNAME, String LASTNAME, String REGION, String ADDRESS ) {

		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("customers");

		customer mc = new customer();

		mc.setID(ID);
		mc.setFIRSTNAME(FIRSTNAME);
		mc.setLASTNAME(LASTNAME);
		mc.setREGION(REGION);
		mc.setADDRESS(ADDRESS); 

		Element element = new Element(ID, mc);

		//System.out.println("Writing new customer: " + ID + " -- " + FIRSTNAME + " " + LASTNAME + " " + REGION + " " + ADDRESS + " to the customer cache!");

		if (useWriter) {
			cache.putWithWriter(element);
		} else {
			cache.put(element);
		}	

	}

}
