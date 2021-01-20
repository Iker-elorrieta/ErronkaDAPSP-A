package PruebasPSP;

import static org.junit.Assert.assertTrue;

import java.net.Socket;

import org.junit.jupiter.api.Test;

import Servidor_Cliente.Cliente;
import Servidor_Cliente.HiloServidor;
import Servidor_Cliente.Servidor;

class Pruebas {
	
	
	Servidor s = new Servidor();
	HiloServidor hs = new HiloServidor();
	Cliente c = new Cliente();

	@Test
	public void servidorPrueba() {
		hs.run();
		boolean conexion=c.iniciar(); 
		
		assertTrue(conexion);
	}
	
	

	

}
