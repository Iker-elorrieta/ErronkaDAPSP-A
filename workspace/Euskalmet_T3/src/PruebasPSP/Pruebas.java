package PruebasPSP;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import Servidor_Cliente.Cliente;
import Servidor_Cliente.Servidor;

class Pruebas {

	@Test
	public void servidorPrueba() {
		Servidor s = new Servidor();
		Cliente c = new Cliente();
		s.iniciar();
		boolean conexion=c.iniciar(); 
		
		assertTrue(conexion);
	}
	
	

	

}
