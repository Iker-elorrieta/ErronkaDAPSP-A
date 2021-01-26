package PruebasDA;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import Hibernate.HibernateUtil;
import Inserts.LlenarBBDD;

class PruebaBBDD {
	
	private SessionFactory sf;
	private Logger log = Logger.getLogger("org.hibernate");

	@Test
	public void principalPrueba() throws IOException, SQLException {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		assertTrue(LlenarBBDD.principal(sf));
		sf.close();
	}

}
