package Servidor_Cliente;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;

import Generadores.*;
import Hibernate.HibernateUtil;
import Inserts.LlenarBBDD;

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
		LlenarBBDD.provincias(sf);
		System.out.println("[Datos/BBDD] >> provincia -> DATOS ACTUALIZADOS");
		System.out.println("\n"+"[Datos] >> Provincias -> FINALIZADO \n");
		Municipios.principal(sf);
		EspaciosNaturales.principal(sf);
		MuniEspacios.principal(sf);
		Estaciones.principal(sf);
		CalidadDeAire.principal(sf);
	}
	
}
