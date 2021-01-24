package Servidor_Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Hibernate.HibernateUtil;

public class HiloServidor extends Thread {

	ObjectInputStream fentrada;
	ObjectOutputStream fsalida;
	Socket socket = null;

	public HiloServidor(Socket s) throws IOException{
		socket=s;
		fsalida = new ObjectOutputStream(socket.getOutputStream());
		fentrada = new ObjectInputStream((socket.getInputStream()));
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		System.out.println("Empieza el hilo servidor");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		SessionFactory sesioa = HibernateUtil.getSessionFactory();
		while (true) {
			try {
				Session session = sesioa.openSession();
				String hql = fentrada.readObject().toString();
				List<Object> resultado = session.createQuery(hql).list();
				session.close();
				fsalida.writeObject(resultado);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
