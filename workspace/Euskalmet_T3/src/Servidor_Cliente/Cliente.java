package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import App.AppCliente;

public class Cliente {

	private final int PUERTO = 5000;
//	private final String IP = "localhost";
	private final String IP = "192.168.106.28";
	private List<Object> resultado;

	@SuppressWarnings("unchecked")
	public boolean iniciar() throws IOException, ClassNotFoundException {
		AppCliente frame = new AppCliente();
		frame.setVisible(true);
		Socket cliente = null;
		ObjectOutputStream salida = null;
		ObjectInputStream entrada = null;
		cliente = new Socket(IP, PUERTO);
		System.out.println("Conexion realizada con servidor");
		salida = new ObjectOutputStream (cliente.getOutputStream());
		entrada = new ObjectInputStream(cliente.getInputStream());
		while (true) {
			if (frame.getHQL().length() != 0) {
				salida.writeObject(frame.getHQL());
				resultado = (List<Object>) entrada.readObject();
				frame.setLista(resultado);
			}
		}
		try {
			if (cliente != null)
				cliente.close();
			if (entrada != null)
				entrada.close();
			if (salida != null)
				salida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Fin cliente");
		return true;
	}
	
	public static boolean clienteMain() throws ClassNotFoundException, IOException {
		Cliente c1 = new Cliente();
		c1.iniciar();
		return true;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		clienteMain();
	}
	

}
