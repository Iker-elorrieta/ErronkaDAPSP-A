package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HiloServidor extends Thread {

	private ObjectOutputStream fsalida;
	private Socket socket = null;
	private ArrayList<List<Object>> ayDatos; 

	public HiloServidor() {}
	
	public HiloServidor(Socket s, ArrayList<List<Object>> ayDatos) throws IOException{
		this.socket=s;
		this.fsalida = new ObjectOutputStream(socket.getOutputStream());
		this.ayDatos = ayDatos;
	}
	
	public void run() {
		System.out.println("Empieza el hilo servidor");
		
		try {
			fsalida.writeObject(ayDatos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin del hilo servidor");
	}
}

