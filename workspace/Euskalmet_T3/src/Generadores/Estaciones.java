package Generadores;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Hibernate.Hash;
import Hibernate.HibernateUtil;

public class Estaciones {
	private static Logger log = Logger.getLogger("org.hibernate");
	private static SessionFactory sf;
	
	public static void main(String[] args) {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		principal(sf);
		sf.close();
	}
 
	public static boolean principal(SessionFactory sf) {
		boolean terminado=false;
		
		String nomArchivo = "estaciones";
		String datos = compararHash(sf, "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/estaciones.json", nomArchivo);
		if (!datos.equals("*")) {
			generarJSON(datos, nomArchivo);
			System.out.println("[Datos/JSON] >> " + nomArchivo + " -> GENERADO JSON");
			limpiarJSON(nomArchivo);
			System.out.println("[Datos/JSON] >> " + nomArchivo + " -> LIMPIADO");
			generarXML(nomArchivo);
			System.out.println("[Datos/XML] >> " + nomArchivo + " -> GENERADO XML");
		}
		
		System.out.println("\n"+"[Datos] >> Estaciones -> FINALIZADO \n");
		terminado = true;
		
		return terminado;
	}
	
	private static String compararHash(SessionFactory sf, String urlStr, String nomArchivo) {
		String respuesta = "*";
		try {
			URL url = new URL(urlStr);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
			byte[] contents = new byte[1024];
			int bytesRead = 0;
			String origenStr="";
			while((bytesRead = inputStream.read(contents))!=-1) {
				origenStr += new String (contents, 0, bytesRead);
			}
			
			inputStream.close();

			Session sesion = sf.openSession();
			Transaction tx = sesion.beginTransaction();
			
			Hash hash = (Hash) sesion.createQuery("FROM Hash WHERE nombre = '"+nomArchivo+"'").uniqueResult();
			if (hash != null) {
				if (obtenerHash(origenStr).equals(hash.getHash())) {
					System.out.println("[Datos/Hash] >> "+nomArchivo+" -> ÚLTIMA VERSIÓN de hash EN VIGOR.");
				} else {
					hash.setHash(obtenerHash(origenStr));
					
					sesion.save(hash); tx.commit();
					System.out.println("[Datos/Hash] >> "+nomArchivo+" -> Hash ACTUALIZADO.");
					
					respuesta = origenStr;
				}
			} else {
				hash = new Hash();
				hash.setNombre(nomArchivo);
				hash.setHash(obtenerHash(origenStr));
				
				sesion.save(hash); tx.commit();
				System.out.println("[Datos/Hash] >> "+nomArchivo+" -> Hash CREADO.");
				
				respuesta = origenStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respuesta;
	}
	
	private static void generarJSON(String datos, String nomArchivo) {
		try {
			FileWriter fw = new FileWriter("Archivos/ArchivosJSON/"+nomArchivo+".json"); 
			fw.write(datos);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void limpiarJSON(String nomArchivo) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader("Archivos/ArchivosJSON/"+nomArchivo+".json", StandardCharsets.UTF_8));
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
	        
	        FileWriter fw = new FileWriter("Archivos/ArchivosJSON/"+nomArchivo+".json");
	        fw.write(texto);
	        fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void generarXML(String nomArchivo) {
		try {
			FileReader fr = new FileReader("Archivos/ArchivosJSON/"+nomArchivo+".json");
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			Iterator<JsonElement> iter = datos.iterator(); int knt=1;
			
			FileReader fr2 = new FileReader("Archivos/ArchivosJSON/municipios.json");
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
            tf.transform(new DOMSource(doc), new StreamResult(new File("Archivos/ArchivosXML/"+nomArchivo+".xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String obtenerHash(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte dataBytes[] = str.getBytes();
		md.update(dataBytes);
		byte resum[] = md.digest();
		
		return new String(resum);
	}
	
}
