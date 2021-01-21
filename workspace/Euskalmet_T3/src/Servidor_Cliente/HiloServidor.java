package Servidor_Cliente;

import java.io.*;
import java.net.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import Hibernate.Municipio;
import Inserts.HibernateUtil;

public class HiloServidor extends Thread{

	ObjectInputStream fentrada;
	ObjectOutputStream fsalida;
	Socket socket = null;

	public HiloServidor(Socket s){
		socket=s;
		try {
			fsalida = new ObjectOutputStream(socket.getOutputStream());
			fentrada = new ObjectInputStream((socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Empieza el hilo servidor");
		SessionFactory sesioa = HibernateUtil.getSessionFactory();
		Session session = sesioa.openSession();
		Transaction tx = session.beginTransaction();

		String hql=null;

		try {
			hql = fentrada.readObject().toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Query q = session.createQuery(hql);
		q.setParameter("cod", 1);
		try {
			Hibernate.Municipio muni = (Municipio) q.uniqueResult();
			fsalida.writeObject(muni.getNombre());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			tx.commit();	
		}catch(ConstraintViolationException e) {
			System.out.printf("Mezua: %s%n",e.getMessage());
			System.out.printf("COD ERROR: %d%n",e.getErrorCode());
			System.out.printf("ERROR SQL: %s%n",e.getSQLException().getMessage());
		}
		session.close();


		//		Connection conn = null;
		//		Statement stmt = null;
		//		try {

		//			// Konexioa Ireki
		//			System.out.println("Datubasera konektatzen...");
		//			Conexion_MySQL MDK = Conexion_MySQL.getInstance();
		//			conn = MDK.conectar();
		//
		//			// query edo SQL sententzia egikaritu
		//			System.out.println(" Statementa sortzen...");
		//			stmt = conn.createStatement();
		//			ResultSet rs = stmt.executeQuery(sql);

		// Erantzunetik informazioa erauzi
		//			while (rs.next()) {
		// Bueltatu zutabeen izenen arabera
		//				String nombre = rs.getString("nombre");
		//				fsalida.write(nombre);

		// Emaitzak pantailaratu
		//				System.out.println("Nombre: " + nombre);
		//			}

		// Garbiketa
		//			rs.close();
		//			stmt.close();
		//			MDK.desconectar();
		//		} catch (SQLException se) { 
		//			// JDBC erroreak
		//			se.printStackTrace();
		//		} catch (Exception e) {
		//			// Class.forName errorea
		//			e.printStackTrace();
		//		} finally {
		//			// itxi erabilitako errekurtsoak
		//			try {
		//				if (stmt != null)
		//					stmt.close();
		//			} catch (SQLException se2) {
		//			}
		//			try {
		//				if (conn != null)
		//					conn.close();
		//			} catch (SQLException se) {
		//				se.printStackTrace();
		//			}
		//		}
		//}
	}
}
