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
import java.util.regex.Pattern;

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

public class EspaciosNaturales {
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

		String nomArchivo = "espacios_naturales";
		String datos = compararHash(sf, "https://opendata.euskadi.eus/contenidos/ds_recursos_turisticos/playas_de_euskadi/opendata/espacios-naturales.json", nomArchivo);
		if (!datos.equals("*")) {
			generarJSON(datos, nomArchivo);
			System.out.println("[Datos/JSON] >> " + nomArchivo + " -> GENERADO JSON");
			limpiarJSON(nomArchivo);
			System.out.println("[Datos/JSON] >> " + nomArchivo + " -> LIMPIADO");
			generarXML(nomArchivo);
			System.out.println("[Datos/XML] >> " + nomArchivo + " -> GENERADO XML");
		}
		
		System.out.println("\n"+"[Datos] >> EspaciosNaturales -> FINALIZADO \n");
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
			texto = texto.replaceAll("<br />", "\n").replaceAll("&nbsp", "\t").replaceAll("<br/>", "\n");
			texto = texto.replaceAll("&aacute ", "á").replaceAll("&Aacute ", "Á").replaceAll("&eacute ", "é").replaceAll("&Eacute ", "É");
			texto = texto.replaceAll("&iacute ", "í").replaceAll("&Iacute ", "Í").replaceAll("&oacute ", "ó").replaceAll("&Oacute ", "Ó");
			texto = texto.replaceAll("&uacute ", "ú").replaceAll("&Uacute ", "Ú").replaceAll("&ntilde ", "ñ");
			texto = Pattern.compile("<a (.*?)>", Pattern.DOTALL).matcher(texto).replaceAll("");

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
