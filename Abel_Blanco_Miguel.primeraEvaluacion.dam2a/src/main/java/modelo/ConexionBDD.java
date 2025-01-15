package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDD {
	private static ConexionBDD instance;
	private static Connection con;
	
	private ConexionBDD() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ictussacyl", "root", "toor");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConexionBDD getInstance() {
		if (instance==null) {
			instance = new ConexionBDD();
		}
		return instance;
	}

	public static Connection getCon() {
		return con;
	}
	
	public static void cerrarCon() {
		try {
			instance.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
