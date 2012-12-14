package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.ehcache.objects.customer;
import net.sf.ehcache.constructs.blocking.CacheEntryFactory;

// ReadThroughClass, which is used to collect a customer from the database. This class is called, when read through is enabled.

public class dbCustomerReadThrough implements CacheEntryFactory{
	
	public Object createEntry(Object key) throws Exception {
		// TODO Auto-generated method stub

		
		
		Connection connect = null;
	    Statement st = null;
	    ResultSet rs = null;
	    String url = "jdbc:mysql://localhost:3306/";
	    String db = "ehcache";
	    String driver = "com.mysql.jdbc.Driver";
	    String user = "ehcache";
	    String pass = "terracotta";
	    customer mc = new customer();
	    
		try {
			
			Class.forName(driver);
			connect = DriverManager
			          .getConnection(url + db, user, pass);
			
			st = connect.createStatement();
		    rs = st.executeQuery("select * from customers where ID='" + key + "'");
		    // rs = st.executeQuery("select * from customers where ID='1'");
		    
		    System.out.println("(dbReadThrough) Retrieving customer " + key + " from DB ... ");
		    
		    while (rs.next()) {
		    	
		    	
		    	int ID = rs.getInt("ID");
		    	String FIRSTNAME = rs.getString("FIRSTNAME");
		    	String LASTNAME = rs.getString("LASTNAME");
		    	String REGION = rs.getString("REGION");
		    	String ADDRESS = rs.getString("ADDRESS");
		     
		    	System.out.println("(dbReadThrough) Found customer: " + ID + " -- " + FIRSTNAME + " " + LASTNAME + " " + REGION + " " + ADDRESS);
		    			    	
	    		mc.setID(ID);
	    		mc.setFIRSTNAME(FIRSTNAME);
	    		mc.setLASTNAME(LASTNAME);
	    		mc.setREGION(REGION);
	    		mc.setADDRESS(ADDRESS);    	
		    			    	
		    }
		    
		    System.out.println("(dbReadThrough) Writing object for customer " + key + " to the cache ... ");
	    return mc;
		
		} finally {
			connect.close();
		}

	}
	

}
