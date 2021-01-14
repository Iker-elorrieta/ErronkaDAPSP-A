package Inserts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_MySQL {
	private static Conexion_MySQL ourInstance = new Conexion_MySQL();
	
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/euskomet_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	private Connection conexion;
	
	public static Conexion_MySQL getInstance() {
		return ourInstance;
	}

	public Connection conectar() {
		if (conexion == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				conexion = (Connection) DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			} catch (ClassNotFoundException | SQLException e) {
				System.err.println("No se ha podido conectar a la BBDD.");
				e.printStackTrace();
			}
		}
		return conexion;
	}
	
	public void desconectar() {
		if (conexion != null) {
			try {
				conexion.close();
				conexion = null;
				System.out.println("Conexión cerrrada.");
			} catch (SQLException e) {
				System.err.println("No se ha podido desconectar de la BBDD.");
				e.printStackTrace();
			}
		}
	}
	
}

