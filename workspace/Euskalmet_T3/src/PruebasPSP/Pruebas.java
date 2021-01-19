package PruebasPSP;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import Servidor_Cliente.Cliente;
import Servidor_Cliente.Servidor;

class Pruebas {
	
	Servidor s = new Servidor();
	Cliente c = new Cliente();

	@Test
	public void servidorPrueba() {
		s.run();
		boolean conexion=c.iniciar(); 
		
		assertTrue(conexion);
	}
	
	

	

}
