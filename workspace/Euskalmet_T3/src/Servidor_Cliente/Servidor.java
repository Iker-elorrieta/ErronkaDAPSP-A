package Servidor_Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Generadores.GenerarTodo;
import Hibernate.HibernateUtil;
import Inserts.LlenarBBDD;

public class Servidor extends Thread{
	
	private final int PUERTO = 5000;
	private ArrayList<List<Object>> ayDatos = new ArrayList<List<Object>>();
	private SessionFactory sf;
	private Logger log = Logger.getLogger("org.hibernate");
	
	@SuppressWarnings("unchecked")
	public void run() {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		
		System.out.println("[Servidor] >> Actualizando datos... \n");
		prepararTodo();
		System.out.println("[Servidor] >> Datos actualizados. \n");
		
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
		
		//Espacios Naturales por Provincia
		List<Object> espNBizkaia = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = 'Bizkaia' ORDER BY me.espaciosNaturales.nombre").list();
		ayDatos.add(espNBizkaia);
		List<Object> espNGipuzkoa = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = 'Gipuzkoa' ORDER BY me.espaciosNaturales.nombre").list();
		ayDatos.add(espNGipuzkoa);
		List<Object> espNAraba = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = 'Araba' ORDER BY me.espaciosNaturales.nombre").list();
		ayDatos.add(espNAraba);
		
		//Calidad de Aire por Municipio
		List<Object> muniCAire = session.createQuery("SELECT DISTINCT ca.id.fechaHora, ca.calidad, ca.estaciones.direccion, ca.estaciones.municipio.nombre FROM CalidadAire AS ca ORDER BY ca.estaciones.municipio.nombre ASC, ca.estaciones.nombre ASC, ca.id.fechaHora DESC").list();
		ayDatos.add(muniCAire);
		
		session.close();
		sf.close();
		
		ServerSocket servidor = null;
		Socket cliente = null;
		try {
			servidor = crearSocket();
			System.out.println("[Servidor] >> Esperando conexiones del cliente... \n");
			while (true) {
				cliente = new Socket();
				cliente = servidor.accept();
				System.out.println("[Servidor] >> Cliente conectado. \n");
				HiloServidor hilo = new HiloServidor(cliente,ayDatos);
				hilo.start();
			}
		} catch (IOException e) {
			System.out.println("[Servidor] >> Error: " + e.getMessage() + " \n");
		
		}finally {
			if (servidor != null)
				try {
					servidor.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		System.out.println("[Servidor] >> El servidor se ha terminado. \n");
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
	
	private void prepararTodo() {
		try {
			GenerarTodo.principal();
			LlenarBBDD.principal(sf);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
