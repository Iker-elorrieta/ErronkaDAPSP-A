package Generadores;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Hibernate.Hash;
import Hibernate.HibernateUtil;
import Inserts.LlenarBBDD;

public class CalidadDeAire {
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
		
		trustEveryone();
		String nomArchivo = "0_CalidadAire";
		generarJSON("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json", nomArchivo);
		System.out.println("[Datos/JSON] >> calidad_aire -> GENERADO JSON");
		limpiarJSON(nomArchivo);
		System.out.println("[Datos/JSON] >> calidad_aire -> LIMPIADO");
		recorrerJSON(sf, nomArchivo);
		System.out.println("\n"+"[Datos] >> CalidadAire -> FINALIZADO \n");
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
					System.out.println("\n"+"[Datos/Hash] >> calidad_aire ("+nomArchivo+") -> �LTIMA VERSI�N de hash EN VIGOR.");
				} else {
					hash.setHash(obtenerHash(origenStr));
					
					sesion.save(hash); tx.commit();
					System.out.println("\n"+"[Datos/Hash] >> calidad_aire ("+nomArchivo+") -> Hash ACTUALIZADO.");
					
					respuesta = origenStr;
				}
			} else {
				hash = new Hash();
				hash.setNombre(nomArchivo);
				hash.setHash(obtenerHash(origenStr));
				
				sesion.save(hash); tx.commit();
				System.out.println("\n"+"[Datos/Hash] >> calidad_aire ("+nomArchivo+") -> Hash CREADO.");
				
				respuesta = origenStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respuesta;
	}
	
	private static void generarJSON(String datos, String nomArchivo) {
		try {
			if (nomArchivo.equals("0_CalidadAire")) {
				URL url = new URL(datos);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				
				BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
				FileOutputStream fileOS = new FileOutputStream("Archivos/ArchivosJSON_CalidadAire/"+nomArchivo+".json"); 
				byte data[] = new byte[1024];
				int byteContent;
				while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
					fileOS.write(data, 0, byteContent);
				}
				
				fileOS.close();
			} else {
				FileWriter fw = new FileWriter("Archivos/ArchivosJSON_CalidadAire/"+nomArchivo+".json"); 
				fw.write(datos);
				fw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void limpiarJSON(String nomArchivo) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader("Archivos/ArchivosJSON_CalidadAire/"+nomArchivo+".json", StandardCharsets.UTF_8));
			String texto = "";
			String linea = bf.readLine();
			while(linea != null) {
	            texto += "\n"+linea;
	            linea = bf.readLine();
	        }
	        bf.close();
	        
	        texto = texto.substring(texto.indexOf("["), texto.indexOf("]")+1);
	        
	        FileWriter fw = new FileWriter("Archivos/ArchivosJSON_CalidadAire/"+nomArchivo+".json");
	        fw.write(texto);
	        fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void recorrerJSON(SessionFactory sf, String nomArchivo) {
		try {
			FileReader fr = new FileReader("Archivos/ArchivosJSON_CalidadAire/"+nomArchivo+".json", StandardCharsets.UTF_8);
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			
			Iterator<JsonElement> iter = datos.iterator();
			while (iter.hasNext()) {
				JsonObject objeto = iter.next().getAsJsonObject();
				
				String nombre = objeto.get("name").getAsString();
				
				String url = objeto.get("url").getAsString();
				if (url.contains("datos_indice")) {
					
					String json = compararHash(sf, url, nombre);
					if (!json.equals("*")) {
						generarJSON(json, nombre);
						System.out.println("[Datos/JSON] >> calidad_aire (" + nombre + ") -> GENERADO JSON");
						limpiarJSON(nombre);
						System.out.println("[Datos/JSON] >> calidad_aire (" + nombre + ") -> LIMPIADO");
						generarXML(nombre);
						System.out.println("[Datos/XML] >> calidad_aire (" + nombre + ") -> GENERADO XML");
						LlenarBBDD.calidad_aire(sf, nombre);
						System.out.println("[Datos/BBDD] >> calidad_aire (" + nombre + ") -> DATOS ACTUALIZADOS");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void generarXML(String nomArchivo) {
		try {
			FileReader fr = new FileReader("Archivos/ArchivosJSON_CalidadAire/"+nomArchivo+".json", StandardCharsets.UTF_8);
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
				} else {
					//EUSKARA: calidadStr = "Daturik gabe";
					calidadStr = "Sin datos";
				}
				calidad.appendChild(doc.createTextNode(calidadStr));
				
				Element PM25 = doc.createElement("PM25"); entrada.appendChild(PM25);
				Element PM10 = doc.createElement("PM10"); entrada.appendChild(PM10);
				Element SO2 = doc.createElement("SO2"); entrada.appendChild(SO2);
				Element NO2 = doc.createElement("NO2"); entrada.appendChild(NO2);
				Element O3 = doc.createElement("O3"); entrada.appendChild(O3);
				Element CO = doc.createElement("CO"); entrada.appendChild(CO);
				
				String[] ayCAire = {"PM25","PM10","SO2","NO2","OZONO","COmgm3"};
				for (int i = 0; i < ayCAire.length; i++) {
					String dato = null;
					if (objeto.has(ayCAire[i])) {
						dato = objeto.get(ayCAire[i]).getAsString();
						
						if (i == 0) {
							PM25.appendChild(doc.createTextNode(dato));
						} else if (i == 1) {
							PM10.appendChild(doc.createTextNode(dato));
						} else if (i == 2) {
							SO2.appendChild(doc.createTextNode(dato));
						} else if (i == 3) {
							NO2.appendChild(doc.createTextNode(dato));
						} else if (i == 4) {
							O3.appendChild(doc.createTextNode(dato));
						} else {
							CO.appendChild(doc.createTextNode(dato));
						}
					}
				}
				
				Element cod_estacion = doc.createElement("cod_estacion"); entrada.appendChild(cod_estacion);
				File archivoOrigen = new File("Archivos/ArchivosXML/estaciones.xml");
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
            tf.transform(new DOMSource(doc), new StreamResult(new File("Archivos/ArchivosXML_CalidadAire/"+nomArchivo+".xml")));
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
	
	private static void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return false;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] {new X509TrustManager() {
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			}}, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			System.out.println("[Certificado] >> ERROR: "+e.getMessage());
		}
	}
	
}
