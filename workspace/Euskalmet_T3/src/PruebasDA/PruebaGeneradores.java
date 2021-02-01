package PruebasDA;

import static org.junit.Assert.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Generadores.CalidadDeAire;
import Generadores.EspaciosNaturales;
import Generadores.Estaciones;
import Generadores.MuniEspacios;
import Generadores.Municipios;
import Hibernate.HibernateUtil;

class PruebaGeneradores {
	private static Logger log = Logger.getLogger("org.hibernate");
	private static SessionFactory sf;
	
	@BeforeAll
	private void declarar() {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
	}
	
	@Test
	public void principalPruebaMunicipios() {
		assertTrue(Municipios.principal(sf));
	}
	
	@Test
	public void principalPruebaEspaciosNaturales() {
		assertTrue(EspaciosNaturales.principal(sf));
	}
	
	@Test
	public void principalPruebaMuniEspacios() {
		assertTrue(MuniEspacios.principal(sf));
	}
	
	@Test
	public void principalPruebaEstaciones() {
		assertTrue(Estaciones.principal(sf));
	}
	
	@Test
	public void principalPruebaCalidadAire() {
		assertTrue(CalidadDeAire.principal(sf));
	}
	
	@AfterAll
	private void cerrar() {
		sf.close();
	}

}
