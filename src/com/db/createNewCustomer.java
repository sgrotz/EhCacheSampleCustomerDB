package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ehcache.objects.customer;

public class createNewCustomer {

	private static int randomValue = 10000000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int count = 10;

		for (int i = 0; i < count; i++) {
			createCustomer();
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
		regions.add("Paul");
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
		regions.add("Hoffmann");
		regions.add("Berger");
		regions.add("Gruber");

		Random rand = new Random();
		int choice = rand.nextInt(regions.size());
		return regions.get(choice);

	}
	
	
	
	public static void createCustomer() {
		
		createCustomer(getRandomInt(), getRandomFirstName(), getRandomLastName(), getRandomRegion(), "Inserted through direct DB insert ...");
		
	}
	
	

	public static void createCustomer(int ID, String FIRSTNAME, String LASTNAME, String REGION, String ADDRESS ) {

		Connection connect = null;
		Statement st = null;
		int rs;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "ehcache";
		String driver = "com.mysql.jdbc.Driver";  
		String user = "ehcache";
		String pass = "terracotta";

		int key = ID;

		System.out.println("Will crate a new customer VALUES ('"+ key + "', '"+ FIRSTNAME +"', '"+LASTNAME+"', '"+REGION+"', '"+ADDRESS+"')");
		customer mc = new customer();
		

		try {

			Class.forName(driver);
			connect = DriverManager
					.getConnection(url + db, user, pass);

			st = connect.createStatement();
			// rs = st.executeQuery("select * from customers where ID='" + key + "'");
			rs = st.executeUpdate("insert into customers VALUES ('"+ key + "', '"+ FIRSTNAME +"', '"+LASTNAME+"', '"+REGION+"', '"+ADDRESS+"')");	

			

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
