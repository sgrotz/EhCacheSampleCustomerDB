package com.db;

import java.util.Collection;
import java.util.Iterator;

import com.ehcache.objects.customer;

import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.writebehind.operations.SingleOperationType;

// Sample class to implement the cacheWriter methods. 
// The below methods implement the CRUD operations to the database.
// Some methods also have an ALL function - which is used, if using write-behind.



public class createCustomerClass implements CacheWriter {

	@Override
	public CacheWriter clone(Ehcache arg0) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(CacheEntry arg0) throws CacheException {
		// This delete method is called, when a customer is deleted from the cache. 
		// This is a WRITE-THROUGH functionality
		
	}

	@Override
	public void deleteAll(Collection<CacheEntry> arg0) throws CacheException {
		// This delete method is called, when a customer is deleted from the cache. 
		// This is a WRITE-BEHIND functionality
		// This method is similar to the delete function, but it should delete an array of objects.
		
	}

	@Override
	public void dispose() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void throwAway(Element arg0, SingleOperationType arg1,
			RuntimeException arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(Element e) throws CacheException {
		// TODO Auto-generated method stub
		
		System.out.println("Calling CustomerClass write method ...");
		customer mc = (customer) e.getObjectValue();
   
		createNewCustomer.createCustomer(mc.getID(), mc.getFIRSTNAME(), mc.getLASTNAME(), mc.getREGION(), mc.getADDRESS());	
		
	}

	@Override
	public void writeAll(Collection<Element> ce) throws CacheException {
		// TODO Auto-generated method stub
		
		Iterator it = ce.iterator();
		while (it.hasNext()) {
			System.out.println("Calling CustomerClass write-all method ...");
			Element e = (Element) it.next();
			customer mc = (customer) e.getObjectValue();
	   
			createNewCustomer.createCustomer(mc.getID(), mc.getFIRSTNAME(), mc.getLASTNAME(), mc.getREGION(), mc.getADDRESS());	
		}
		
	}



}
