package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import App.AppCliente;

public class HiloCliente extends Thread {
	
	private AppCliente frame;
	
	private final int PUERTO = 5000;
	private final String IP = "192.168.106.28";
	
	private ObjectInputStream fentrada;
	private ObjectOutputStream fsalida;
	private Socket cliente = null;
	
	private boolean cerrar;
	private List<Object> resultado = null;
	
	public HiloCliente(AppCliente frame) {
		this.frame = frame;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("Conexion realizada con servidor");
			fsalida = new ObjectOutputStream(cliente.getOutputStream());
			fentrada = new ObjectInputStream((cliente.getInputStream()));
			
			while (!cerrar) {
				String hql = frame.getHQL();
				if (hql.length() != 0) {
					try {
						fsalida.writeObject(hql);
						resultado = (List<Object>) fentrada.readObject();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			
			cliente.close();
			System.out.println("Fin cliente");
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void cerrar() {
		cerrar = true;
	}
	
	public List<Object> getResultado() {
		List<Object> lista = resultado;
		resultado = null;
		return lista;
	}
	
}
