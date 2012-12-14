package com.quartz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ehcache.objects.customer;


// The following class is invoked by the scheduler to synchronize the database with the cache. Take a look at the
// runScheduler class file for more information about the job. 
// This class contains the information how to read the data from the DB and write it to the cache. 


public class SyncCustomersDBwithCache implements Job{

	
	public static CacheManager cacheM;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		
		Connection connect = null;
        Statement st = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "ehcache";
        String driver = "com.mysql.jdbc.Driver";
        String user = "ehcache";
        String pass = "terracotta";
		
		System.out.println("Synchronize Customers Database - Scheduler was triggered ...");
		System.out.println("Will now synchronize all customers from the database with ehCache ...");
		
		try {
			
			
			cacheM = CacheManager.newInstance("config/ehcache.xml");
			
			//JobDataMap jdm = arg0.getJobDetail().getJobDataMap();
			//CacheManager cacheM = (CacheManager) jdm.get("cacheManager");
					// .newInstance("config/ehcache.xml");
			
			System.out.println("Got CacheManager from Job Data Map ... ");
			
			Class.forName(driver);
			connect = DriverManager
			          .getConnection(url + db, user, pass);
			
			st = connect.createStatement();
		    rs = st.executeQuery("select * from customers");
		    
		    Cache cache = cacheM.getCache("customers");
		    
		    System.out.println("Retrieving data from DB ... ");
		    
		    while (rs.next()) {
		    	
		    	
		    	int ID = rs.getInt("ID");
		    	String FIRSTNAME = rs.getString("FIRSTNAME");
		    	String LASTNAME = rs.getString("LASTNAME");
		    	String REGION = rs.getString("REGION");
		    	String ADDRESS = rs.getString("ADDRESS");
		     
		    	System.out.println("Received new customer: " + ID + " -- " + FIRSTNAME + " " + LASTNAME + " " + REGION + " " + ADDRESS);
		    	
		    	customer mc = new customer();
		    		mc.setID(ID);
		    		mc.setFIRSTNAME(FIRSTNAME);
		    		mc.setLASTNAME(LASTNAME);
		    		mc.setREGION(REGION);
		    		mc.setADDRESS(ADDRESS);    	
		    	
		    	Element element = new Element(ID, mc);
		    	
		    	System.out.println("Writing element: " + element.getKey() + " to the customer cache ...");
		    	cache.put(element);
		    	
		    	//Thread.sleep(5000);
		    	
		    }
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connect.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	private static customer retrieveCustomer(int key) {
		
		return null;
		
	}
	
		

}
