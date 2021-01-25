package Generadores;

import java.io.IOException;

public class GenerarTodo {

	public static void main(String[] args) throws IOException {
		principal();
	}
	
	public static void principal() throws IOException {
		ImportarCertificado.principal();
		Municipios.principal();
		EspaciosNaturales.principal();
		MuniEspacios.principal();
		Estaciones.principal();
		CalidadDeAire.principal();
	}
	
}
