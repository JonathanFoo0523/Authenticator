package curlec.twoFA.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper {

	private final static String url = "jdbc:mysql://localhost:3306/curlec_authenticator";
	private final static String username = "root";
	private final static String password = "password";
	private final static String driver = "com.mysql.cj.jdbc.Driver";
	
	private static Connection getConnection() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Established connection with database");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static boolean pushQuery(String query) {
		Connection con = getConnection();
		boolean result;
		try {
			PreparedStatement ps = con.prepareStatement(query);
			System.out.println(query);
			ps.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
		
	}
	
	public static ArrayList<HashMap<String, String>> pullQuery(String query){
		Connection con = getConnection();
		ResultSet result;
		ArrayList<HashMap<String, String>> array = new ArrayList<HashMap<String, String>>();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			System.out.println(query);
			result = ps.executeQuery();
			while (result.next()) {
				HashMap<String, String> dict = new HashMap<String, String>();
				dict.put("id", result.getString("id"));
				dict.put("username", result.getString("username"));
				dict.put("hash", result.getString("hash"));
				dict.put("AuthSecret", result.getString("AuthSecret"));
				
				array.add(dict);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println(array);
		return array;
	}
}
