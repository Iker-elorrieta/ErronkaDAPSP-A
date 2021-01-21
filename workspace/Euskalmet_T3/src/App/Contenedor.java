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
	private ArrayList<Municipio> ayMuni;
	private ArrayList<EspaciosNaturales> ayEspN;
	private ArrayList<MuniEspacios> ayMuniEspN;
	private ArrayList<Estaciones> ayEst;
	private ArrayList<CalidadAire> ayCAire;
	
	public Contenedor() {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		
		ayProv = new ArrayList<Provincia>();
		llenarArray("Provincia");
		ayMuni = new ArrayList<Municipio>();
		llenarArray("Municipio");
		ayEspN = new ArrayList<EspaciosNaturales>();
		llenarArray("EspaciosNaturales");
		ayMuniEspN = new ArrayList<MuniEspacios>();
		llenarArray("MuniEspacios");
		ayEst = new ArrayList<Estaciones>();
		llenarArray("Estaciones");
		ayCAire = new ArrayList<CalidadAire>();
		llenarArray("CalidadAire");
	}
	
	@SuppressWarnings("unchecked")
	private void llenarArray(String tipo) {
		Session session = sf.openSession();
		
		List<Object> list = session.createQuery("FROM "+tipo).list();
		for (int i = 0; i < list.size(); i++) {
			if (tipo.equals("Provincia")) {
				Provincia prov = (Provincia) list.get(i);
				ayProv.add(prov);
			} else if (tipo.equals("Municipio")) {
				Municipio muni = (Municipio) list.get(i);
				ayMuni.add(muni);
			} else if (tipo.equals("EspaciosNaturales")) {
				EspaciosNaturales espN = (EspaciosNaturales) list.get(i);
				ayEspN.add(espN);
			} else if (tipo.equals("MuniEspacios")) {
				MuniEspacios muniEspN = (MuniEspacios) list.get(i);
				ayMuniEspN.add(muniEspN);
			} else if (tipo.equals("Estaciones")) {
				Estaciones est = (Estaciones) list.get(i);
				ayEst.add(est);
			} else {
				CalidadAire cAire = (CalidadAire) list.get(i);
				ayCAire.add(cAire);
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
	


	public ArrayList<Municipio> getAyMuni() {
		return ayMuni;
	}
	


	public ArrayList<EspaciosNaturales> getAyEspN() {
		return ayEspN;
	}
	


	public ArrayList<MuniEspacios> getAyMuniEspN() {
		return ayMuniEspN;
	}
	


	public ArrayList<Estaciones> getAyEst() {
		return ayEst;
	}
	


	public ArrayList<CalidadAire> getAyCAire() {
		return ayCAire;
	}
	

}
