package PruebasDA;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import Generadores.CalidadDeAire;
import Generadores.EspaciosNaturales;
import Generadores.Estaciones;
import Generadores.MuniEspacios;
import Generadores.Municipios;

class PruebaGeneradores {
	
	@Test
	public void principalPruebaMunicipios() throws IOException {
		assertTrue(Municipios.principal());
	}
	
	@Test
	public void principalPruebaEstaciones() throws IOException {
		assertTrue(Estaciones.principal());
	}
	
	@Test
	public void principalPruebaCalidadAire() throws IOException {
		assertTrue(CalidadDeAire.principal());
	}
	
	@Test
	public void principalPruebaEspaciosNaturales() throws IOException {
		assertTrue(EspaciosNaturales.principal());
	}
	
	@Test
	public void principalPruebaMuniEspacios() throws IOException {
		assertTrue(MuniEspacios.principal());
	}

}
