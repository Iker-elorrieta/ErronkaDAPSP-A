package Generadores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;

import Hibernate.HibernateUtil;

public class GenerarTodo {
	private static Logger log = Logger.getLogger("org.hibernate");
	private static SessionFactory sf;

	public static void main(String[] args) {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		principal(sf);
		sf.close();
	}
	
	public static void principal(SessionFactory sf) {
		ImportarCertificado.principal();
		Municipios.principal(sf);
		EspaciosNaturales.principal(sf);
		MuniEspacios.principal();
		Estaciones.principal(sf);
		CalidadDeAire.principal(sf);
	}
	
}
