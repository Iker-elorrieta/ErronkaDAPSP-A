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

	private final static int PUERTO = 5000;

	public static void main(String args[]) throws IOException  {
		ServerSocket servidor = null;
		Socket cliente = null;
		//boolean continuar = true;

		try {
			servidor = new ServerSocket(PUERTO);
			System.out.println("Esperando conexiones del cliente...");

			//while (continuar) {
			cliente = new Socket();
			cliente = servidor.accept();
			System.out.println("Cliente conectado");

			HiloServidor hilo = new HiloServidor(cliente);
			hilo.start();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {

			if (servidor != null)
				servidor.close();
			if (cliente != null)
				cliente.close();
		}
		System.out.println("El servidor se ha terminado");
	}

	//	public static void main(String[] args) {
	//		Servidor s1 = new Servidor();
	//		s1.run();
	//	}

}
