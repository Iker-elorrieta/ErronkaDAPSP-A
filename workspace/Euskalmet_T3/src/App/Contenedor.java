package App;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Hibernate.*;

public class Contenedor {
	
	private SessionFactory sf;
	private Logger log = Logger.getLogger("org.hibernate");
	
	private ArrayList<Provincia> ayProv;
	
	public Contenedor() {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		
		ayProv = new ArrayList<Provincia>();
		llenarArray("Provincia");
	}
	
	@SuppressWarnings("unchecked")
	private void llenarArray(String tipo) {
		Session session = sf.openSession();
		
		List<Object> list = session.createQuery("FROM "+tipo).list();
		for (int i = 0; i < list.size(); i++) {
			if (tipo.equals("Provincia")) {
				Provincia prov = (Provincia) list.get(i);
				ayProv.add(prov);
			}
		}
		
		session.close();
	}

	public SessionFactory getSf() {
		return sf;
	}
	
	public Logger getLog() {
		return log;
	}
	
	public ArrayList<Provincia> getAyProv() {
		return ayProv;
	}
	
}
