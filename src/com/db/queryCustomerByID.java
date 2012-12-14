package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.quartz.JobDataMap;

import com.ehcache.objects.customer;

public class queryCustomerByID {

	/**
	 * @param args
	 */
	public static void main(String[] arg) {
		// TODO Auto-generated method stub

		
		Connection connect = null;
        Statement st = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/";
        String db = "ehcache";
        String driver = "com.mysql.jdbc.Driver";
        String user = "ehcache";
        String pass = "terracotta";
        
        int key = 3951163;
		
		System.out.println("Will query the database for customer: " + key + " ...");
		
		
		
		
		
		try {
			
			Class.forName(driver);
			connect = DriverManager
			          .getConnection(url + db, user, pass);
			
			st = connect.createStatement();
		    rs = st.executeQuery("select * from customers where ID='" + key + "'");
		    
		    System.out.println("Retrieving data from DB ... ");
		    
		    while (rs.next()) {
		    	
		    	
		    	int ID = rs.getInt("ID");
		    	String FIRSTNAME = rs.getString("FIRSTNAME");
		    	String LASTNAME = rs.getString("LASTNAME");
		    	String REGION = rs.getString("REGION");
		    	String ADDRESS = rs.getString("ADDRESS");
		     
		    	System.out.println("Received customer: " + ID + " -- " + FIRSTNAME + " " + LASTNAME + " " + REGION + " " + ADDRESS);
		    	
		    	customer mc = new customer();
		    		mc.setID(ID);
		    		mc.setFIRSTNAME(FIRSTNAME);
		    		mc.setLASTNAME(LASTNAME);
		    		mc.setREGION(REGION);
		    		mc.setADDRESS(ADDRESS);    	
		    			    	
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
}
