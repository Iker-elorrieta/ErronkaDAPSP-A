package Servidor_Cliente;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Thread {
	
	private final int PUERTO = 4000;
//	private final String IP = "192.168.106.28";
	private final String IP = "127.0.0.1";
	
	private ObjectInputStream fentrada;
	private Socket cliente = null;
	
	private ArrayList<List<Object>> resultado = null;
	
	
	public Cliente(){
		iniciar();
	}
	
	@SuppressWarnings("unchecked")
	public boolean iniciar(){
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("[Cliente] >> Conexion realizada con servidor. \n");
			fentrada = new ObjectInputStream(cliente.getInputStream());
			
			resultado = (ArrayList<List<Object>>) fentrada.readObject();
		} catch (Exception e) {
			System.out.println("[Cliente] >> Error: " + e.getMessage() + " \n");
		}
		
		return true;
	}
	
	public void cerrar() {
		try {
			cliente.close();
			System.out.println("[Cliente] >> Fin cliente. \n");
		} catch (Exception e) {
			System.out.println("[Cliente] >> Error: " + e.getMessage() + " \n");
		}
	}
	
	public ArrayList<List<Object>> getResultado() {
		return resultado;
	}
	
}
