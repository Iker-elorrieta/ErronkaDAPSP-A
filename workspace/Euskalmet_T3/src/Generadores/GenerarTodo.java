package Generadores;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class GenerarTodo {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		principal();
	}
	
	public static void principal() throws IOException, NoSuchAlgorithmException {
		ImportarCertificado.principal();
		Municipios.principal();
		EspaciosNaturales.principal();
		MuniEspacios.principal();
		Estaciones.principal();
		CalidadDeAire.principal();
	}
	
}
