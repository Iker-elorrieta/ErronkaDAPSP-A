package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

	private final int PUERTO = 5000;
	private final String IP = "localhost";
	//private final String IP = "192.168.106.28";
	private String resultado ="";

	public boolean iniciar() throws IOException, ClassNotFoundException {
		VentanaCliente v = new VentanaCliente();
		v.setVisible(true);
		Socket cliente = null;
		ObjectOutputStream salida = null;
		ObjectInputStream entrada = null;
		cliente = new Socket(IP, PUERTO);
		System.out.println("Conexion realizada con servidor");
		salida = new ObjectOutputStream (cliente.getOutputStream());
		entrada = new ObjectInputStream(cliente.getInputStream());
		salida.writeObject("FROM Municipio as m WHERE m.codMuni = :cod "); //SELECT nombre FROM municipio WHERE cod_muni = 1
		resultado = entrada.readObject().toString();
		v.lblDato.setText(resultado);
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
