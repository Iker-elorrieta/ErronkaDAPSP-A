package Inserts;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LlenarBBDD {
	private static Conexion_MySQL conn = Conexion_MySQL.getInstance();
	
	public static void main(String[] args) throws SQLException, IOException {
		principal();
	}
	
	public static boolean principal() throws IOException, SQLException {
		boolean terminado=false;
		Connection conexion = conn.conectar();
		provincias(conexion);
		System.out.println("provincia -> COMPLETADO \n");
		municipios(conexion);
		System.out.println("municipio -> COMPLETADO \n");
		ArrayList<String> nomEstaciones = estaciones(conexion);
		System.out.println("estaciones -> COMPLETADO \n");
		espacios_naturales(conexion);
		System.out.println("espacios_naturales -> COMPLETADO \n");
		muni_espacios(conexion);
		System.out.println("muni_espacios -> COMPLETADO \n");
		calidad_aire(conexion, nomEstaciones);
		System.out.println("calidad_aire -> COMPLETADO \n");
		System.out.println("-> FINALIZADO <-");
		conexion.close(); conn.desconectar();
		terminado = true;
		
		return terminado;
	}
	
	private static ArrayList<ArrayList<String>> recorrerXML(String nomCarpeta, String nomArchivo) {
		ArrayList<ArrayList<String>> mtDatos = new ArrayList<ArrayList<String>>();
		try {
			File archivoOrigen = new File("src/"+nomCarpeta+"/"+nomArchivo+".xml");
			
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(archivoOrigen);
			doc.getDocumentElement().normalize();
			NodeList nlEntradas;
			if (nomCarpeta.contains("CalidadAire")) {
				nlEntradas = doc.getElementsByTagName("calidadAire").item(0).getChildNodes();
			} else {
				nlEntradas = doc.getElementsByTagName(nomArchivo).item(0).getChildNodes();
			}
			for (int kntEntradas = 0; kntEntradas < nlEntradas.getLength(); kntEntradas++) {
				Node nEntrada = nlEntradas.item(kntEntradas);
				
				ArrayList<String> ayEntrada = new ArrayList<String>();
				
				NodeList nlDatos = nEntrada.getChildNodes();
				for (int kntDatos = 0; kntDatos < nlDatos.getLength(); kntDatos++) {
					Node nDato = nlDatos.item(kntDatos);
					
					if(nDato.getNodeType() == Node.ELEMENT_NODE) {
						Element eDato = (Element) nDato;
						ayEntrada.add(eDato.getTextContent());
					}
				}
				
				mtDatos.add(ayEntrada);
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return mtDatos;
	}
	
	private static void provincias(Connection conexion) {
		PreparedStatement query;
		String sql;
		
		String[][] provincias = {
				{"1","Bizkaia"},
				{"2","Gipuzkoa"},
				{"3","Araba"}};
		
		int lineas = 0;
		for (int i = 0; i < provincias.length; i++) {
			try {
				sql = "INSERT INTO provincia(cod_prov,nombre) VALUES(?,?)";
				
				query = conexion.prepareStatement(sql);
				query.setInt(1, Integer.parseInt(provincias[i][0]));
				query.setString(2, provincias[i][1]);
				
				lineas += query.executeUpdate();
				query.close();
			} catch (SQLException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("provincias -> Lineas afectadas: " + lineas);
	}
	
	private static void municipios(Connection conexion) {
		PreparedStatement query;
		String sql;
		
		ArrayList<ArrayList<String>> municipios = recorrerXML("ArchivosXML","municipios");
		
		int lineas = 0;
		for (int i = 0; i < municipios.size(); i++) {
			try {
				sql = "INSERT INTO municipio(cod_muni,nombre,descripcion,cod_prov) VALUES(?,?,?,?)";
				
				query = conexion.prepareStatement(sql);
				query.setInt(1, Integer.parseInt(municipios.get(i).get(0)));
				query.setString(2, municipios.get(i).get(1));
				query.setString(3, municipios.get(i).get(2));
				query.setInt(4, Integer.parseInt(municipios.get(i).get(3)));
				
				lineas += query.executeUpdate();
				query.close();
			} catch (SQLException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("municipio -> Lineas afectadas: " + lineas);
	}
	
	private static ArrayList<String> estaciones(Connection conexion) {
		ArrayList<String> nomEstaciones = new ArrayList<String>();
		PreparedStatement query;
		String sql;
		
		ArrayList<ArrayList<String>> estaciones = recorrerXML("ArchivosXML","estaciones");
		
		int lineas = 0;
		for (int i = 0; i < estaciones.size(); i++) {
			try {
				sql = "INSERT INTO estaciones(cod_estacion,nombre,direccion,coord_x,coord_y,latitud,longitud,foto,cod_muni) VALUES(?,?,?,?,?,?,?,?,?)";
				
				query = conexion.prepareStatement(sql);
				query.setInt(1, Integer.parseInt(estaciones.get(i).get(0)));
				query.setString(2, estaciones.get(i).get(1));
				query.setString(3, estaciones.get(i).get(2));
				for (int j = 3; j < 7; j++) {
					String dato = estaciones.get(i).get(j).replaceAll("\\,", "\\.");
					if (dato.length() != 0) {
						query.setDouble(j+1, Double.parseDouble(dato));
					} else {
						query.setNull(j+1, Types.NULL);
					}
				}
				query.setString(8, estaciones.get(i).get(7));
				query.setInt(9, Integer.parseInt(estaciones.get(i).get(8)));
				
				lineas += query.executeUpdate();
				query.close();
			} catch (SQLException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			}
			nomEstaciones.add(estaciones.get(i).get(1));
		}
		
		System.out.println("estaciones -> Lineas afectadas: " + lineas);
		
		return nomEstaciones;
	}
	
	private static void espacios_naturales(Connection conexion) {
		PreparedStatement query;
		String sql;
		
		ArrayList<ArrayList<String>> espacios_naturales = recorrerXML("ArchivosXML","espacios_naturales");
		
		int lineas = 0;
		for (int i = 0; i < espacios_naturales.size(); i++) {
			try {
				sql = "INSERT INTO espacios_naturales(cod_enatural,nombre,descripcion,tipo,latitud,longitud,foto) VALUES(?,?,?,?,?,?,?)";
				
				query = conexion.prepareStatement(sql);
				query.setInt(1, Integer.parseInt(espacios_naturales.get(i).get(0)));
				query.setString(2, espacios_naturales.get(i).get(1));
				query.setString(3, espacios_naturales.get(i).get(2));
				query.setString(4, espacios_naturales.get(i).get(3));
				for (int j = 4; j < 6; j++) {
					String dato = espacios_naturales.get(i).get(j).replaceAll("\\,", "\\.");
					if (dato.length() != 0) {
						query.setDouble(j+1, Double.parseDouble(dato));
					} else {
						query.setNull(j+1, Types.NULL);
					}
				}
				query.setString(7, espacios_naturales.get(i).get(6));
				
				lineas += query.executeUpdate();
				query.close();
			} catch (SQLException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("espacios_naturales -> Lineas afectadas: " + lineas);
	}
	
	private static void muni_espacios(Connection conexion) {
		PreparedStatement query;
		String sql;
		
		ArrayList<ArrayList<String>> muni_espacios = recorrerXML("ArchivosXML","muni_espacios");
		
		int lineas = 0;
		for (int i = 0; i < muni_espacios.size(); i++) {
			try {
				sql = "INSERT INTO muni_espacios(cod_muni,cod_enatural) VALUES(?,?)";
				
				query = conexion.prepareStatement(sql);
				query.setInt(1, Integer.parseInt(muni_espacios.get(i).get(0)));
				query.setInt(2, Integer.parseInt(muni_espacios.get(i).get(1)));
				
				lineas += query.executeUpdate();
				query.close();
			} catch (SQLException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("muni_espacios -> Lineas afectadas: " + lineas);
	}
	
	private static void calidad_aire(Connection conexion, ArrayList<String> nomEstaciones) {
		PreparedStatement query;
		String sql;
		
		for (int kntEstacion = 0; kntEstacion < nomEstaciones.size(); kntEstacion++) {
			ArrayList<ArrayList<String>> calidad_aire = recorrerXML("ArchivosXML_CalidadAire",nomEstaciones.get(kntEstacion));
			
			int lineas = 0;
			for (int i = 0; i < calidad_aire.size(); i++) {
				try {
					sql = "INSERT INTO calidad_aire(fecha_hora,calidad,cod_estacion) VALUES(?,?,?)";
					
					query = conexion.prepareStatement(sql);
					query.setString(1, calidad_aire.get(i).get(0));
					query.setString(2, calidad_aire.get(i).get(1));
					query.setInt(3, Integer.parseInt(calidad_aire.get(i).get(2)));
					
					lineas += query.executeUpdate();
					query.close();
				} catch (SQLException e) {
					if (e.getErrorCode() != 1062) {
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("calidad_aire ("+nomEstaciones.get(kntEstacion)+") -> Lineas afectadas: " + lineas);
		}
	}
	
}
