package Servidor_Cliente;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import Hibernate.*;

public class HiloServidor extends Thread {

	ObjectInputStream fentrada;
	ObjectOutputStream fsalida;
	Socket socket = null;

	public HiloServidor(Socket s) throws IOException{
		socket=s;
		fsalida = new ObjectOutputStream(socket.getOutputStream());
		fentrada = new ObjectInputStream((socket.getInputStream()));
	}

	public HiloServidor() {}

	@SuppressWarnings("unchecked")
	public void run() {
		System.out.println("Empieza el hilo servidor");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
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
		List<Object> resultado = session.createQuery(hql).list();
		try {
			fsalida.writeObject(resultado);
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
	}
}
