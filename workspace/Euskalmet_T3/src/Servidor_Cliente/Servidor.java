package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Inserts.Conexion_MySQL;

public class Servidor {

private final int PUERTO = 5000;
	
	public void iniciar() {
		ServerSocket servidor = null;
		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;
		boolean continuar = true;
		
		try {
			servidor = new ServerSocket(PUERTO);
			System.out.println("Esperando conexiones del cliente...");
			cliente = servidor.accept();
			System.out.println("Cliente conectado");
			salida = new ObjectOutputStream (cliente.getOutputStream());
			entrada = new ObjectInputStream(cliente.getInputStream());

			//while (continuar) {
				String sql = entrada.readObject().toString();
				Connection conn = null;
				Statement stmt = null;
				try {

					// Konexioa Ireki
					System.out.println("Datubasera konektatzen...");
					Conexion_MySQL MDK = Conexion_MySQL.getInstance();
					conn = MDK.conectar();

					// query edo SQL sententzia egikaritu
					System.out.println(" Statementa.sortzen..");
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);

					// Erantzunetik informazioa erauzi
					while (rs.next()) {
						// Bueltatu zutabeen izenen arabera
						String nombre = rs.getString("nombre");
						salida.writeObject(nombre);

						// Emaitzak pantailaratu
						System.out.print("Nombre: " + nombre);
					}

					// Garbiketa
					rs.close();
					stmt.close();
					MDK.desconectar();
				} catch (SQLException se) {
					// JDBC erroreak
					se.printStackTrace();
				} catch (Exception e) {
					// Class.forName errorea
					e.printStackTrace();
				} finally {
					// itxi erabilitako errekurtsoak
					try {
						if (stmt != null)
							stmt.close();
					} catch (SQLException se2) {
					}
					try {
						if (conn != null)
							conn.close();
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			//}
			
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			try {
				if (servidor != null)
					servidor.close();
				if (cliente != null)
					cliente.close();
				if (entrada != null)
					entrada.close();
				if (salida != null)
					salida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Fin servidor");
		}
	}
	
	public static void main(String[] args) {
		Servidor s1 = new Servidor();
		s1.iniciar();
	}
	
}
