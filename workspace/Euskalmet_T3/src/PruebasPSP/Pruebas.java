package PruebasPSP;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import Servidor_Cliente.Cliente;
import Servidor_Cliente.HiloServidor;
import Servidor_Cliente.Servidor;

class Pruebas {

	Servidor s = new Servidor();
	Cliente c = new Cliente();
	HiloServidor hs = new HiloServidor();
	Socket so = new Socket();
	

	@Test
	public void servidorPrueba() throws ClassNotFoundException, IOException {
		s.start();
		boolean conexion=c.iniciar();
		assertTrue(conexion);
	}
	
	/* estos tests con ExecutorService hacen que el metodo que se prueba entre a una excepcion,
	 * es util para testear las exceptiones de los runs de los hilos ya que no se pueden convertir a boolean
	 */
	@Test
	public void exceptionServidor() throws Exception{
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<?> future = es.submit(() -> {
			s.start();
			return null;
		});

		future.get();
	}
	
	@Test
	public void exceptionCliente() throws Exception{
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<?> future = es.submit(() -> {
			c.iniciar();
			return null;
		});

		future.get();
	}
	
	@Test
	public void exceptionHiloServidor() throws Exception{
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<?> future = es.submit(() -> {
			hs.start();
			return null;
		});

		future.get();
	}
	
	@Test
	public void serverMainTest() {
		@SuppressWarnings("static-access")
		boolean t = s.mainServer();
		assertTrue(t);
	}
	
	
}
