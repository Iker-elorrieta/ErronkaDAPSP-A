package Generadores;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.SessionFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Hibernate.HibernateUtil;
import Inserts.LlenarBBDD;

public class MuniEspacios {
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
		
		String nomArchivo = "muni_espacios";
		generarXML(nomArchivo);
		System.out.println("[Datos/XML] >> " + nomArchivo + " -> GENERADO XML");
		LlenarBBDD.muni_espacios(sf);
		System.out.println("[Datos/BBDD] >> " + nomArchivo + " -> DATOS ACTUALIZADOS");
		System.out.println("\n"+"[Datos] >> MuniEspacios -> FINALIZADO \n");
		terminado = true;
		
		return terminado;
	}
	
	private static void generarXML(String nomArchivo) {
		try {
			FileReader fr = new FileReader("Archivos/ArchivosJSON/espacios_naturales.json");
			JsonArray datos = JsonParser.parseReader(fr).getAsJsonArray();
			
			FileReader fr2 = new FileReader("Archivos/ArchivosJSON/municipios.json");
			JsonArray datos2 = JsonParser.parseReader(fr2).getAsJsonArray();
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element muni_espacios = doc.createElement("muni_espacios"); doc.appendChild(muni_espacios);
			
			Iterator<JsonElement> iter = datos.iterator(); int knt=1;
			while (iter.hasNext()) {
				JsonObject objeto = iter.next().getAsJsonObject();
				
				String cod_enaturalStr = "";
				File archivoOrigen = new File("Archivos/ArchivosXML/espacios_naturales.xml");
				Document doc2 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(archivoOrigen);
				doc2.getDocumentElement().normalize();

				NodeList nList = doc2.getElementsByTagName("espacio_natural");
				for (int i = 0; i < nList.getLength(); i++) {
					Node n = nList.item(i); 
					if(n.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) n;
						if (e.getElementsByTagName("nombre").item(0).getTextContent().equals(objeto.get("documentName").getAsString())) {
							cod_enaturalStr = e.getElementsByTagName("cod_enatural").item(0).getTextContent();
						}
					}
				}
				
				String muni = objeto.get("municipality").getAsString();
				if (muni.indexOf(" ") != -1) {
					String[] ayMuni = muni.split(" ");
					String anterior = "";
					for (int i = 0; i < ayMuni.length; i++) {
						if (anterior.isEmpty() || !(anterior.equalsIgnoreCase(ayMuni[i]))) {
							Iterator<JsonElement> iter2 = datos2.iterator();
							while (iter2.hasNext()) {
								JsonObject objeto2 = iter2.next().getAsJsonObject();
								
								if (ayMuni[i].equalsIgnoreCase(objeto2.get("municipality").getAsString())) {
									String cod_muniStr = objeto2.get("municipalitycode").getAsString();
									
									Element entrada = doc.createElement("entrada"); muni_espacios.appendChild(entrada); entrada.setAttribute("id", ""+knt+"");
									
									Element cod_muni_espacios = doc.createElement("cod_muni_espacios"); entrada.appendChild(cod_muni_espacios);
									cod_muni_espacios.appendChild(doc.createTextNode(cod_muniStr+cod_enaturalStr));
									
									Element cod_muni = doc.createElement("cod_muni"); entrada.appendChild(cod_muni);
									cod_muni.appendChild(doc.createTextNode(cod_muniStr));
									
									Element cod_enatural = doc.createElement("cod_enatural"); entrada.appendChild(cod_enatural);
									cod_enatural.appendChild(doc.createTextNode(cod_enaturalStr));
									
									knt++;
									break;
								}
							}
							anterior = ayMuni[i];
						}
					}
				} else {
					Iterator<JsonElement> iter2 = datos2.iterator();
					while (iter2.hasNext()) {
						JsonObject objeto2 = iter2.next().getAsJsonObject();
						
						if (muni.equalsIgnoreCase(objeto2.get("municipality").getAsString())) {
							String cod_muniStr = objeto2.get("municipalitycode").getAsString();
							
							Element entrada = doc.createElement("entrada"); muni_espacios.appendChild(entrada); entrada.setAttribute("id", ""+knt+"");
							
							Element cod_muni_espacios = doc.createElement("cod_muni_espacios"); entrada.appendChild(cod_muni_espacios);
							cod_muni_espacios.appendChild(doc.createTextNode(cod_muniStr+cod_enaturalStr));
							
							Element cod_muni = doc.createElement("cod_muni"); entrada.appendChild(cod_muni);
							cod_muni.appendChild(doc.createTextNode(cod_muniStr));
							
							Element cod_enatural = doc.createElement("cod_enatural"); entrada.appendChild(cod_enatural);
							cod_enatural.appendChild(doc.createTextNode(cod_enaturalStr));
							
							knt++;
							break;
						}
					}
				}
			}
			
			Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(new File("Archivos/ArchivosXML/"+nomArchivo+".xml")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
