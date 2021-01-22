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

	@Test
	public void servidorPrueba() throws ClassNotFoundException, IOException {
		s.start();
		boolean conexion=c.iniciar();
		assertTrue(conexion);
	}
	
}
