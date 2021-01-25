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
	
	
	public Cliente(){
		iniciar();
	}
	
	@SuppressWarnings("unchecked")
	public boolean iniciar(){
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("[Cliente] >> Conexion realizada con servidor. \n");
			fsalida = new ObjectOutputStream(cliente.getOutputStream());
			fentrada = new ObjectInputStream(cliente.getInputStream());
			
			resultado = (ArrayList<List<Object>>) fentrada.readObject();
			
			cliente.close();
			
			System.out.println("[Cliente] >> Fin cliente. \n");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public ArrayList<List<Object>> getResultado() {
		return resultado;
	}
	
}
