package PruebasDA;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import Inserts.LlenarBBDD;

class PruebaBBDD {

	@Test
	public void principalPrueba() throws IOException, SQLException {
		assertTrue(LlenarBBDD.principal());
	}

}
