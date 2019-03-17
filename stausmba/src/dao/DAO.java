package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	private static Connection conn;
	 
	static{
		try {
			Class.forName("org.sqlite.JDBC");
			conn=DriverManager.getConnection("jdbc:sqlite:usmba.sqlite");
			System.out.println("creation d'une connexion");
		} catch (ClassNotFoundException e) {
			System.out.println("Not Connected");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Not Connected");
			e.printStackTrace();
		}
	
	}

	public static Connection getConn() {
		return conn;
	}

}
