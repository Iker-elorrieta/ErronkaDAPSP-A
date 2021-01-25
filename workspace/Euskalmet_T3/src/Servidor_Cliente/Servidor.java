package Servidor_Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Hibernate.HibernateUtil;

public class Servidor extends Thread{

	private final int PUERTO = 5000;
	private ArrayList<List<Object>> ayDatos = new ArrayList<List<Object>>();
	private SessionFactory sf;
	private Logger log = Logger.getLogger("org.hibernate");

	@SuppressWarnings("unchecked")
	public void run() {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		//Municipios por Provincia
		List<Object> prov = session.createQuery("SELECT DISTINCT p FROM Provincia AS p").list();
		ayDatos.add(prov);
		List<Object> muniBizkaia = session.createQuery("SELECT DISTINCT m FROM Municipio AS m WHERE m.provincia.nombre = 'Bizkaia' ORDER BY m.nombre").list();
		ayDatos.add(muniBizkaia);
		List<Object> muniGipuzkoa = session.createQuery("SELECT DISTINCT m FROM Municipio AS m WHERE m.provincia.nombre = 'Gipuzkoa' ORDER BY m.nombre").list();
		ayDatos.add(muniGipuzkoa);
		List<Object> muniAraba = session.createQuery("SELECT DISTINCT m FROM Municipio AS m WHERE m.provincia.nombre = 'Araba' ORDER BY m.nombre").list();
		ayDatos.add(muniAraba);
		//Espacios por Provincia
		List<Object> espNBizkaia = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = 'Bizkaia' ORDER BY me.espaciosNaturales.nombre").list();
		ayDatos.add(espNBizkaia);
		List<Object> espNGipuzkoa = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = 'Gipuzkoa' ORDER BY me.espaciosNaturales.nombre").list();
		ayDatos.add(espNGipuzkoa);
		List<Object> espNAraba = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = 'Araba' ORDER BY me.espaciosNaturales.nombre").list();
		ayDatos.add(espNAraba);
		session.close();
		sf.close();
		
		ServerSocket servidor = null;
		Socket cliente = null;
		try {
			servidor = crearSocket();
			System.out.println("Esperando conexiones del cliente...");
			while (true) {
				cliente = new Socket();
				cliente = servidor.accept();
				System.out.println("Cliente conectado");
				HiloServidor hilo = new HiloServidor(cliente,ayDatos);
				hilo.start();
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		
		}finally {
			if (servidor != null)
				try {
					servidor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		System.out.println("El servidor se ha terminado");
	}
	
	public ServerSocket crearSocket() throws IOException {
		return new ServerSocket(PUERTO);
	}
	
	public static boolean mainServer() {
		Servidor s1 = new Servidor();
		s1.run();
		return true;
	}

	public static void main(String[] args) {
		mainServer();
	}
	
	

}
