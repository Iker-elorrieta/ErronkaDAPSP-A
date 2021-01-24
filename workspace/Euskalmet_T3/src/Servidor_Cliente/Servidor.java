package Servidor_Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Hibernate.HibernateUtil;

public class Servidor extends Thread{

	private final static int PUERTO = 5000;
	private ArrayList<List<Object>> ayDatos = new ArrayList<List<Object>>();

	@SuppressWarnings("unchecked")
	public void run(){
		SessionFactory sesioa = HibernateUtil.getSessionFactory();
		Session session = sesioa.openSession();
		//Municipios por Provincia
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
		
		ServerSocket servidor = null;
		Socket cliente = null;
		try {
			servidor = new ServerSocket(PUERTO);
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
	
	public static boolean mainServer() {
		Servidor s1 = new Servidor();
		s1.run();
		return true;
	}

	public static void main(String[] args) {
		mainServer();
	}
	
	

}
