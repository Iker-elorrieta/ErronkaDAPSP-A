package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Thread {
	
	private final int PUERTO = 5000;
//	private final String IP = "192.168.106.28";
	private final String IP = "127.0.0.1";
	
	private ObjectInputStream fentrada;
	private ObjectOutputStream fsalida;
	private Socket cliente = null;
	
	private ArrayList<List<Object>> resultado = null;
	
	@SuppressWarnings("unchecked")
	public Cliente() throws ClassNotFoundException {
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("Conexion realizada con servidor");
			fsalida = new ObjectOutputStream(cliente.getOutputStream());
			fentrada = new ObjectInputStream(cliente.getInputStream());
			
			resultado = (ArrayList<List<Object>>) fentrada.readObject();
			
			cliente.close();
			
			System.out.println("Fin cliente");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public ArrayList<List<Object>> getResultado() {
		return resultado;
	}
	
}
