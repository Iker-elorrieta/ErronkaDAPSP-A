package Servidor_Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{

	private final static int PUERTO = 5000;

	public Servidor() {}

	public Servidor(int puerto) {
		try {
			ServerSocket s = new ServerSocket(puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		ServerSocket servidor = null;
		Socket cliente = null;
		//boolean continuar = true;

		try {
			servidor = new ServerSocket(PUERTO);
			System.out.println("Esperando conexiones del cliente...");

			//while (true) {
			cliente = new Socket();
			cliente = servidor.accept();
			System.out.println("Cliente conectado");

			HiloServidor hilo = new HiloServidor(cliente);
			hilo.start();
			//}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}finally {

			if (servidor != null)
				try {
					servidor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			//			if (cliente != null)
			//				cliente.close();
		}
		System.out.println("El servidor se ha terminado");
	}

	public static void main(String[] args) {
		Servidor s1 = new Servidor();
		s1.run();
	}

}
