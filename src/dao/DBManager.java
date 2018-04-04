package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	private static final String DB_PASS = "";
	private static final String DB_USER = "";
	private static final String DB_PORT = "";
	private static final String DB_IP = "";
	private static final String DB_NAME = ""; //TODO: set credentials
	
	private static Connection connection;
	private static DBManager instance;
	
	public static synchronized DBManager getInstance() {
		if(instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, driver not loaded or does not exist! Aborting.");
			return;
		}
		System.out.println("Driver loaded");
		
		//create connection
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+DB_IP+":"+DB_PORT+"/" + DB_NAME, DB_USER, DB_PASS);
		
		} catch (SQLException e) {
			System.out.println("Sorry, connection failed. Check your credentials.");
			System.out.println(e.getMessage());
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
