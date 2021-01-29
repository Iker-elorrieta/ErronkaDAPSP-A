package Generadores;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class pruba {
	
	public static void main(String[] args) throws IOException {
		URL url = new URL("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json");

		URLConnection conexion = url.openConnection();
		conexion.connect();

		BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
		byte[] contents = new byte[1024];
		int bytesRead = 0;
		String origenStr="";
		while((bytesRead = inputStream.read(contents))!=-1) {
		origenStr += new String (contents, 0, bytesRead);
		}
		System.out.println(origenStr);
	}
	
}
