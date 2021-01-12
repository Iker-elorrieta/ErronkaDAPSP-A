

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDBKonexioa {
	private static MariaDBKonexioa ourInstance = new MariaDBKonexioa();

	// init database konstanteak
	private static final String DATABASE_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/euskomet_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String MAX_POOL = "250";

	// konexioa
	private Connection konexioa;

	public static MariaDBKonexioa getInstance() {
		return ourInstance;
	}

	private MariaDBKonexioa() {
	}

	public Connection konektatu() {
		if (konexioa == null) {
			try {
				Class.forName(DATABASE_DRIVER);
				konexioa = (Connection) DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			} catch (ClassNotFoundException | SQLException e) {
				System.err.println("Konexioa ezin izan da burutu.");
				e.printStackTrace();
			}
		}
		return konexioa;
	}

	// datubasetik deskonektatu
	public void deskonektatu() {
		if (konexioa != null) {
			try {
				konexioa.close();
				konexioa = null;
				System.out.println("Konexioa itxi da");
			} catch (SQLException e) {
				System.err.println("Ezin izan da deskonektatu.");
				e.printStackTrace();
			}
		}
	}
}