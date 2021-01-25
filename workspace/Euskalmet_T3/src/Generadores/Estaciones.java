package Generadores;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Estaciones {
	
	public static void main(String[] args) throws IOException {
		principal();
	}
 
	public static boolean principal() throws IOException {
		boolean terminado=false;
		String nomArchivo = "estaciones";
		generarJSON("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/estaciones.json", nomArchivo);
		System.out.println("[Datos/JSON] >> " + nomArchivo + " -> GENERADO");
		limpiarJSON(nomArchivo);
		System.out.println("[Datos/JSON] >> " + nomArchivo + " -> LIMPIADO");
		generarXML(nomArchivo);
		System.out.println("[Datos/XML] >> " + nomArchivo + " -> GENERADO XML \n");
		System.out.println("[Datos] >> Estaciones -> FINALIZADO \n");
		terminado = true;
		return terminado;
	}
	
	private static void generarJSON(String urlStr, String nomArchivo){
		URL url;
		try {
			url = new URL(urlStr);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			BufferedInputStream inputStream = new BufferedInputStream(url.openStream());

			FileOutputStream fileOS = new FileOutputStream("src/ArchivosJSON/"+nomArchivo+".json"); 
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				fileOS.write(data, 0, byteContent);
			}
			fileOS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void limpiarJSON(String nomArchivo) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader("src/ArchivosJSON/"+nomArchivo+".json", StandardCharsets.UTF_8));
		String texto = "";
		String linea = bf.readLine();
		while(linea != null) {
			 if (linea.contains("Town")) {
				if (linea.contains("Donostia")) {
					linea = linea.substring(0, 12) + "Donostia/San_Sebastián\",";
				} else if (linea.indexOf(" ", 12) != -1) {
					linea = linea.substring(0, linea.indexOf(" ", 12)) + "\",";
				}
			}
            texto += "\n"+linea;
            linea = bf.readLine();
        }
        bf.close();
        
        texto = texto.substring(texto.indexOf("["), texto.indexOf("]")+1);
        
        FileWriter fw = new FileWriter("src/ArchivosJSON/"+nomArchivo+".json");
        fw.write(texto);
        fw.close();
	}
	
	private static void generarXML(String nomArchivo) throws IOException {
		try {
			FileReader fr = new FileReader("src/ArchivosJSON/"+nomArchivo+".json");
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			Iterator<JsonElement> iter = datos.iterator(); int knt=1;
			
			FileReader fr2 = new FileReader("src/ArchivosJSON/municipios.json");
			JsonArray datos2 = JsonParser.parseReader(fr2).getAsJsonArray();
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element estaciones = doc.createElement("estaciones"); doc.appendChild(estaciones);
			while (iter.hasNext()) {
				JsonObject entrada = iter.next().getAsJsonObject();
				
				Element estacion = doc.createElement("estacion"); estaciones.appendChild(estacion); estacion.setAttribute("id", ""+knt+"");
				
				Element cod_estacion = doc.createElement("cod_estacion"); estacion.appendChild(cod_estacion);
				cod_estacion.appendChild(doc.createTextNode(String.valueOf(knt)));
				
				Element nombre = doc.createElement("nombre"); estacion.appendChild(nombre);
				String nombreStr = entrada.get("Name").getAsString();
				nombreStr = nombreStr.replaceAll("\\(", "").replaceAll(" ", "_").replaceAll("\\)", "").replaceAll("\\.", "").replaceAll("ª", "").replaceAll("Ñ", "N");
				
				nombre.appendChild(doc.createTextNode(nombreStr));
				
				Element direccion = doc.createElement("direccion"); estacion.appendChild(direccion);
				String direccionStr = entrada.get("Address").getAsString();
				direccion.appendChild(doc.createTextNode(direccionStr));
				
				Element coordX = doc.createElement("coordX"); estacion.appendChild(coordX);
				String coordXStr = entrada.get("CoordenatesXETRS89").getAsString();
				coordX.appendChild(doc.createTextNode(coordXStr));
				
				Element coordY = doc.createElement("coordY"); estacion.appendChild(coordY);
				String coordYStr = entrada.get("CoordenatesYETRS89").getAsString();
				coordY.appendChild(doc.createTextNode(coordYStr));
				
				Element latitud = doc.createElement("latitud"); estacion.appendChild(latitud);
				String latitudStr = entrada.get("Latitude").getAsString();
				latitud.appendChild(doc.createTextNode(latitudStr));
				
				Element longitud = doc.createElement("longitud"); estacion.appendChild(longitud);
				String longitudStr = entrada.get("Longitude").getAsString();
				longitud.appendChild(doc.createTextNode(longitudStr));
				
				Element cod_muni = doc.createElement("cod_muni"); estacion.appendChild(cod_muni);
				Iterator<JsonElement> iter2 = datos2.iterator();
				while (iter2.hasNext()) {
					JsonObject entrada2 = iter2.next().getAsJsonObject();
					
					if (entrada.get("Town").getAsString().toUpperCase().equals(entrada2.get("municipality").getAsString().toUpperCase())) {
						String cod_muniStr = entrada2.get("municipalitycode").getAsString();
						cod_muni.appendChild(doc.createTextNode(cod_muniStr));
						break;
					}
				}
				
				knt++;
			}
			
			Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(new File("src/ArchivosXML/"+nomArchivo+".xml")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
}
