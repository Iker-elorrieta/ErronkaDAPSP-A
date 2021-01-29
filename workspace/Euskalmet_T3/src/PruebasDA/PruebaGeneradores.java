package PruebasDA;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import Generadores.CalidadDeAire;
import Generadores.EspaciosNaturales;
import Generadores.Estaciones;
import Generadores.MuniEspacios;
import Generadores.Municipios;

class PruebaGeneradores {
	
	@Test
	public void principalPruebaMunicipios() throws IOException, NoSuchAlgorithmException {
		assertTrue(Municipios.principal());
	}
	
	@Test
	public void principalPruebaEspaciosNaturales() throws IOException, NoSuchAlgorithmException {
		assertTrue(EspaciosNaturales.principal());
	}
	
	@Test
	public void principalPruebaMuniEspacios() throws IOException, NoSuchAlgorithmException {
		assertTrue(MuniEspacios.principal());
	}
	
	@Test
	public void principalPruebaEstaciones() throws IOException, NoSuchAlgorithmException {
		assertTrue(Estaciones.principal());
	}
	
	@Test
	public void principalPruebaCalidadAire() throws IOException, NoSuchAlgorithmException {
		assertTrue(CalidadDeAire.principal());
	}

}
