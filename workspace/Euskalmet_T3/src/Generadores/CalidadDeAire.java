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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CalidadDeAire {
	public static void main(String[] args) throws IOException {
		principal();
	}
	
	public static boolean principal() throws IOException {
		boolean terminado=false;
	
		String nomArchivo = "0_CalidadAire";
		generarJSON("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json", nomArchivo);
		System.out.println(nomArchivo + " -> GENERADO JSON");
		limpiarJSON(nomArchivo);
		System.out.println(nomArchivo + " -> LIMPIADO \n");
		recorrerJSON(nomArchivo);
		System.out.println("-> FINALIZADO <-");
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

			FileOutputStream fileOS = new FileOutputStream("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json"); 
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
		BufferedReader bf = new BufferedReader(new FileReader("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json", StandardCharsets.UTF_8));
		String texto = "";
		String linea = bf.readLine();
		while(linea != null) {
            texto += "\n"+linea;
            linea = bf.readLine();
        }
        bf.close();
        
        texto = texto.substring(texto.indexOf("["), texto.indexOf("]")+1);
        
        FileWriter fw = new FileWriter("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json");
        fw.write(texto);
        fw.close();
	}
	
	private static void recorrerJSON(String nomArchivo) throws IOException {
		try {
			FileReader fr = new FileReader("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json", StandardCharsets.UTF_8);
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			
			Iterator<JsonElement> iter = datos.iterator();
			while (iter.hasNext()) {
				JsonObject objeto = iter.next().getAsJsonObject();
				
				String nombre = objeto.get("name").getAsString();
				
				String url = objeto.get("url").getAsString();
				if (url.contains("datos_indice")) {
					generarJSON(url, nombre);
					System.out.println(nombre + " -> GENERADO JSON");
					limpiarJSON(nombre);
					System.out.println(nombre + " -> LIMPIADO");
					generarXML(nombre);
					System.out.println(nombre + " -> GENERADO XML \n");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void generarXML(String nomArchivo) throws IOException {
		try {
			FileReader fr = new FileReader("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json", StandardCharsets.UTF_8);
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element calidadAire = doc.createElement("calidadAire"); doc.appendChild(calidadAire);
			
			Iterator<JsonElement> iter = datos.iterator(); int knt=1;
			while (iter.hasNext()) {
				JsonObject objeto = iter.next().getAsJsonObject();
				
				Element entrada = doc.createElement("entrada"); calidadAire.appendChild(entrada); entrada.setAttribute("id", ""+knt+"");
				
				Element fechaHora = doc.createElement("fechaHora"); entrada.appendChild(fechaHora);
				String fechaHoraStr = objeto.get("Date").getAsString() + " " + objeto.get("Hour").getAsString() + ":00";
				fechaHoraStr = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss").format(
						LocalDateTime.parse(fechaHoraStr, DateTimeFormatter.ofPattern("dd/MM/yyyy H:mm:ss")));
				fechaHora.appendChild(doc.createTextNode(fechaHoraStr));
				
				Element calidad = doc.createElement("calidad"); entrada.appendChild(calidad);
				String calidadStr = null;
				if (objeto.has("ICAEstacion")) {
					calidadStr = objeto.get("ICAEstacion").getAsString();
					//EUSKARA: calidadStr = calidadStr.substring(calidadStr.indexOf("/")+1);
					calidadStr = calidadStr.substring(0, calidadStr.indexOf("/")-1);
				}
				calidad.appendChild(doc.createTextNode(calidadStr));
				
				Element cod_estacion = doc.createElement("cod_estacion"); entrada.appendChild(cod_estacion);
				File archivoOrigen = new File("src/ArchivosXML/estaciones.xml");
				Document doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(archivoOrigen);
				doc2.getDocumentElement().normalize();

				NodeList nList = doc2.getElementsByTagName("estacion");
				for (int i = 0; i < nList.getLength(); i++) {
					Node n = nList.item(i); 
					if(n.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) n;
						if (e.getElementsByTagName("nombre").item(0).getTextContent().equals(nomArchivo)) {
							cod_estacion.appendChild(doc.createTextNode(e.getElementsByTagName("cod_estacion").item(0).getTextContent()));
							break;
						}
					}
				}
				
				knt++;
			}
			
			Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(new File("src/ArchivosXML_CalidadAire/"+nomArchivo+".xml")));
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
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
}
