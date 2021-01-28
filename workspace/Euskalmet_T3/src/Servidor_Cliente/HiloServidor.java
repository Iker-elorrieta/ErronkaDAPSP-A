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
		System.out.println(" [Servidor] >> Empieza el "+this.getName()+" \n");
		
		try {
			fsalida.writeObject(ayDatos);
		} catch (Exception e) {
			System.out.println(" [Servidor] >> Error: " + e.getMessage()+" \n");
		}
		
		System.out.println(" [Servidor] >> Fin del "+this.getName()+" \n");
	}
}

