package PruebasPSP;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import Servidor_Cliente.Cliente;
import Servidor_Cliente.HiloServidor;
import Servidor_Cliente.Servidor;
import Servidor_Cliente.VentanaCliente;

class Pruebas {
	
	Servidor s = new Servidor();
	Cliente c = new Cliente();
	Socket so = null;
	HiloServidor hs = null;
	VentanaCliente v = new VentanaCliente();

	@Test
	public void servidorPrueba() {
		s.start();
//		so = new Socket("localhost",5000);
//		hs = new HiloServidor(so);
//		s = new Servidor(5000);
//		s.run();
		boolean conexion=c.iniciar();
		//hs.start();
		
		assertTrue(conexion);
	}
}
