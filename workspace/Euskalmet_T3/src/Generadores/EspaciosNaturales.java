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
import java.util.regex.Pattern;

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

public class EspaciosNaturales {
	public static void main(String[] args) throws IOException {
		String nomArchivo = "espacios_naturales";
		generarJSON("https://opendata.euskadi.eus/contenidos/ds_recursos_turisticos/playas_de_euskadi/opendata/espacios-naturales.json", nomArchivo);
		System.out.println(nomArchivo + " -> GENERADO JSON");
		limpiarJSON(nomArchivo);
		System.out.println(nomArchivo + " -> LIMPIADO");
		generarXML(nomArchivo);
		System.out.println(nomArchivo + " -> GENERADO XML \n");
		System.out.println("-> FINALIZADO <-");
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
		String texto = ""; int knt = 1;
		String linea = bf.readLine();
		while(linea != null) {
			if (linea.contains("turismDescription")) {
            	if (knt%2 != 0) {
            		linea = linea.replaceFirst("turismDescription", "documentDescription");
    			}
            	knt++;
            } else if (linea.contains("municipality\" :")) {
        		if (linea.contains("Trapagaran")) {
					linea = linea.substring(0, 20) + "Valle_de_Trápaga-Trapagaran\",";
				} else if (linea.contains("Donostia")) {
					linea = linea.substring(0, 20) + "Donostia/San_Sebastián\",";
				}
            }
            texto += "\n"+linea;
            linea = bf.readLine();
        }
        bf.close();
        
        texto = texto.substring(texto.indexOf("["), texto.indexOf("]")+1);
        texto = texto.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<strong>", "").replaceAll("</strong>", "");
        texto = texto.replaceAll("<em>", "").replaceAll("</em>", "").replaceAll("<ul>", "\n").replaceAll("</ul>", "");
        texto = texto.replaceAll("<li>", "\u2022\t").replaceAll("</li>", "\n").replaceAll("</a>", "");
        texto = texto.replaceAll("<br />", "\n").replaceAll("&nbsp", "\t").replaceAll("<br/>", "\n").replaceAll("&ntilde ", "ñ");
        texto = Pattern.compile("<a (.*?)>", Pattern.DOTALL).matcher(texto).replaceAll("");
        
        FileWriter fw = new FileWriter("src/ArchivosJSON/"+nomArchivo+".json");
        fw.write(texto);
        fw.close();
	}
	
	private static void generarXML(String nomArchivo) throws IOException {
		try {
			FileReader fr = new FileReader("src/ArchivosJSON/"+nomArchivo+".json");
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element espacios_naturales = doc.createElement("espacios_naturales"); doc.appendChild(espacios_naturales);
			
			Iterator<JsonElement> iter = datos.iterator(); int knt=1;
			while (iter.hasNext()) {
				JsonObject entrada = iter.next().getAsJsonObject();
				
				Element espacio_natural = doc.createElement("espacio_natural"); espacios_naturales.appendChild(espacio_natural); espacio_natural.setAttribute("id", ""+knt+"");
				
				Element cod_enatural = doc.createElement("cod_enatural"); espacio_natural.appendChild(cod_enatural);
				cod_enatural.appendChild(doc.createTextNode(String.valueOf(knt)));
				
				Element nombre = doc.createElement("nombre"); espacio_natural.appendChild(nombre);
				String nombreStr = entrada.get("documentName").getAsString();
				nombre.appendChild(doc.createTextNode(nombreStr));
				
				Element descripcion = doc.createElement("descripcion"); espacio_natural.appendChild(descripcion);
				String descripcionStr = entrada.get("turismDescription").getAsString();
				descripcion.appendChild(doc.createTextNode(descripcionStr));
				
				Element tipo = doc.createElement("tipo"); espacio_natural.appendChild(tipo);
				String tipoStr = entrada.get("templateType").getAsString();
				tipo.appendChild(doc.createTextNode(tipoStr));
				
				Element latitud = doc.createElement("latitud"); espacio_natural.appendChild(latitud);
				String latitudStr = entrada.get("latwgs84").getAsString();
				latitud.appendChild(doc.createTextNode(latitudStr));
				
				Element longitud = doc.createElement("longitud"); espacio_natural.appendChild(longitud);
				String longitudStr = entrada.get("lonwgs84").getAsString();
				longitud.appendChild(doc.createTextNode(longitudStr));
				
				Element foto = doc.createElement("foto"); espacio_natural.appendChild(foto);
				
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
