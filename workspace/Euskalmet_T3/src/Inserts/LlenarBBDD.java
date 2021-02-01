package Inserts;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Hibernate.CalidadAire;
import Hibernate.CalidadAireId;
import Hibernate.EspaciosNaturales;
import Hibernate.Estaciones;
import Hibernate.HibernateUtil;
import Hibernate.MuniEspacios;
import Hibernate.Municipio;
import Hibernate.Provincia;

public class LlenarBBDD {
//	private static Conexion_MySQL conn = Conexion_MySQL.getInstance();
	private static SessionFactory sf;
	private static Logger log = Logger.getLogger("org.hibernate");
	
	public static void main(String[] args) {
		log.setLevel(Level.OFF);
		sf = HibernateUtil.getSessionFactory();
		principal(sf);
		sf.close();
	}
	
	public static boolean principal(SessionFactory sf) {
		boolean terminado=false;
//		Connection conexion = conn.conectar();
		LlenarBBDD.sf = sf;
		
		provincias(/*conexion*/sf);
		System.out.println("[Datos/BBDD] >> provincia -> COMPLETADO \n");
		municipios(/*conexion*/sf);
		System.out.println("[Datos/BBDD] >> municipio -> COMPLETADO \n");
		ArrayList<String> nomEstaciones = estaciones(/*conexion*/sf);
		System.out.println("[Datos/BBDD] >> estaciones -> COMPLETADO \n");
		espacios_naturales(/*conexion*/sf);
		System.out.println("[Datos/BBDD] >> espacios_naturales -> COMPLETADO \n");
		muni_espacios(/*conexion, */sf);
		System.out.println("[Datos/BBDD] >> muni_espacios -> COMPLETADO \n");
		calidad_aire(/*conexion, */nomEstaciones);
		System.out.println("[Datos/BBDD] >> calidad_aire -> COMPLETADO \n");
		System.out.println("-> FINALIZADO <- \n");
		
//		conexion.close(); conn.desconectar();
		terminado = true;
		
		return terminado;
	}
	
	private static ArrayList<ArrayList<String>> recorrerXML(String nomCarpeta, String nomArchivo) {
		ArrayList<ArrayList<String>> mtDatos = new ArrayList<ArrayList<String>>();
		try {
			File archivoOrigen = new File("Archivos/"+nomCarpeta+"/"+nomArchivo+".xml");
			
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
	
	public static void provincias(/*Connection conexion*/SessionFactory sf) {
//		PreparedStatement query;
//		String sql;
		
		String[][] provincias = {
				{"1","Bizkaia"},
				{"2","Gipuzkoa"},
				{"3","Araba"}};
		
		int lineas = 0;
		for (int i = 0; i < provincias.length; i++) {
			try {
				Session sesion = sf.openSession();
				Transaction tx = sesion.beginTransaction();
				
//				sql = "INSERT INTO provincia(cod_prov,nombre) VALUES(?,?)";
				
//				query = conexion.prepareStatement(sql);
//				query.setInt(1, Integer.parseInt(provincias[i][0]));
//				query.setString(2, provincias[i][1]);
				
//				lineas += query.executeUpdate();
//				query.close();
				
				Provincia prov = new Provincia();
				prov.setCodProv(Integer.parseInt(provincias[i][0]));
				prov.setNombre(provincias[i][1]);
				
				sesion.save(prov); tx.commit(); lineas++;
				
				sesion.close();
//			} catch (SQLException e) {
//				if (e.getErrorCode() != 1062) {
//					e.printStackTrace();
//				}
			} catch (ConstraintViolationException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[Datos/BBDD] >> provincias -> Lineas afectadas: " + lineas);
	}
	
	public static void municipios(/*Connection conexion*/SessionFactory sf) {
//		PreparedStatement query;
//		String sql;
		
		ArrayList<ArrayList<String>> municipios = recorrerXML("ArchivosXML","municipios");
		
		int lineas = 0;
		for (int i = 0; i < municipios.size(); i++) {
			try {
				Session sesion = sf.openSession();
				Transaction tx = sesion.beginTransaction();
				
//				sql = "INSERT INTO municipio(cod_muni,nombre,descripcion,cod_prov,latitud,longitud) VALUES(?,?,?,?,?,?,?)";
//				
//				query = conexion.prepareStatement(sql);
//				query.setInt(1, Integer.parseInt(municipios.get(i).get(0)));
//				query.setString(2, municipios.get(i).get(1));
//				query.setString(3, municipios.get(i).get(2));
//				query.setInt(4, Integer.parseInt(municipios.get(i).get(3)));
//				for (int j = 4; j < 6; j++) {
//					String dato = municipios.get(i).get(j).replaceAll("\\,", "\\.");
//					if (dato.length() != 0) {
//						query.setDouble(j+1, Double.parseDouble(dato));
//					} else {
//						query.setNull(j+1, Types.NULL);
//					}
//				}
				
//				lineas += query.executeUpdate();
//				query.close();
				Municipio muni = new Municipio();
				muni.setCodMuni(Integer.parseInt(municipios.get(i).get(0)));
				muni.setNombre(municipios.get(i).get(1));
				muni.setDescripcion(municipios.get(i).get(2));
				Provincia prov = sesion.get(Provincia.class, Integer.parseInt(municipios.get(i).get(3)));
				muni.setProvincia(prov);
				for (int j = 4; j < 6; j++) {
					String dato = municipios.get(i).get(j).replaceAll("\\,", "\\.");
					if (dato.length() != 0) {
						if (j == 4) {
							muni.setLatitud(Double.parseDouble(dato));
						} else if (j == 5) {
							muni.setLongitud(Double.parseDouble(dato));
						}
					}
				}
				
				sesion.save(muni); tx.commit(); lineas++;
				
				sesion.close();
//				} catch (SQLException e) {
//				if (e.getErrorCode() != 1062) {
//					e.printStackTrace();
//				}
			} catch (ConstraintViolationException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[Datos/BBDD] >> municipio -> Lineas afectadas: " + lineas);
	}
	
	public static ArrayList<String> estaciones(/*Connection conexion*/SessionFactory sf) {
		ArrayList<String> nomEstaciones = new ArrayList<String>();
//		PreparedStatement query;
//		String sql;
		
		ArrayList<ArrayList<String>> estaciones = recorrerXML("ArchivosXML","estaciones");
		
		int lineas = 0;
		for (int i = 0; i < estaciones.size(); i++) {
			try {
				Session sesion = sf.openSession();
				Transaction tx = sesion.beginTransaction();
				
//				sql = "INSERT INTO estaciones(cod_estacion,nombre,direccion,coord_x,coord_y,latitud,longitud,cod_muni) VALUES(?,?,?,?,?,?,?,?,?)";
//				
//				query = conexion.prepareStatement(sql);
//				query.setInt(1, Integer.parseInt(estaciones.get(i).get(0)));
//				query.setString(2, estaciones.get(i).get(1));
//				query.setString(3, estaciones.get(i).get(2));
//				for (int j = 3; j < 7; j++) {
//					String dato = estaciones.get(i).get(j).replaceAll("\\,", "\\.");
//					if (dato.length() != 0) {
//						query.setDouble(j+1, Double.parseDouble(dato));
//					} else {
//						query.setNull(j+1, Types.NULL);
//					}
//				}
//				query.setInt(8, Integer.parseInt(estaciones.get(i).get(7)));
//				
//				lineas += query.executeUpdate();
//				query.close();
				
				Estaciones estacion = new Estaciones();
				estacion.setCodEstacion(Integer.parseInt(estaciones.get(i).get(0)));
				estacion.setNombre(estaciones.get(i).get(1));
				estacion.setDireccion(estaciones.get(i).get(2));
				for (int j = 3; j < 7; j++) {
					String dato = estaciones.get(i).get(j).replaceAll("\\,", "\\.");
					if (dato.length() != 0) {
						if (j == 3) {
							estacion.setCoordX(Double.parseDouble(dato));
						} else if (j == 4) {
							estacion.setCoordY(Double.parseDouble(dato));
						} else if (j == 5) {
							estacion.setLatitud(Double.parseDouble(dato));
						} else if (j == 6) {
							estacion.setLongitud(Double.parseDouble(dato));
						}
					}
				}
				Municipio muni = sesion.get(Municipio.class, Integer.parseInt(estaciones.get(i).get(7)));
				estacion.setMunicipio(muni);
				
				sesion.save(estacion); tx.commit(); lineas++;
				
				sesion.close();
//				} catch (SQLException e) {
//				if (e.getErrorCode() != 1062) {
//					e.printStackTrace();
//				}
			} catch (ConstraintViolationException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			nomEstaciones.add(estaciones.get(i).get(1));
		}
		
		System.out.println("[Datos/BBDD] >> estaciones -> Lineas afectadas: " + lineas);
		
		return nomEstaciones;
	}
	
	public static void espacios_naturales(/*Connection conexion*/SessionFactory sf) {
//		PreparedStatement query;
//		String sql;
		
		ArrayList<ArrayList<String>> espacios_naturales = recorrerXML("ArchivosXML","espacios_naturales");
		
		int lineas = 0;
		for (int i = 0; i < espacios_naturales.size(); i++) {
			try {
				Session sesion = sf.openSession();
				Transaction tx = sesion.beginTransaction();
				
//				sql = "INSERT INTO espacios_naturales(cod_enatural,nombre,descripcion,tipo,latitud,longitud) VALUES(?,?,?,?,?,?,?)";
//				
//				query = conexion.prepareStatement(sql);
//				query.setInt(1, Integer.parseInt(espacios_naturales.get(i).get(0)));
//				query.setString(2, espacios_naturales.get(i).get(1));
//				query.setString(3, espacios_naturales.get(i).get(2));
//				query.setString(4, espacios_naturales.get(i).get(3));
//				for (int j = 4; j < 6; j++) {
//					String dato = espacios_naturales.get(i).get(j).replaceAll("\\,", "\\.");
//					if (dato.length() != 0) {
//						query.setDouble(j+1, Double.parseDouble(dato));
//					} else {
//						query.setNull(j+1, Types.NULL);
//					}
//				}
//				
//				lineas += query.executeUpdate();
//				query.close();
				
				EspaciosNaturales eNatural = new EspaciosNaturales();
				eNatural.setCodEnatural(Integer.parseInt(espacios_naturales.get(i).get(0)));
				eNatural.setNombre(espacios_naturales.get(i).get(1));
				eNatural.setDescripcion(espacios_naturales.get(i).get(2));
				eNatural.setTipo(espacios_naturales.get(i).get(3));
				for (int j = 4; j < 6; j++) {
					String dato = espacios_naturales.get(i).get(j).replaceAll("\\,", "\\.");
					if (dato.length() != 0) {
						if (j == 4) {
							eNatural.setLatitud(Double.parseDouble(dato));
						} else if (j == 5) {
							eNatural.setLongitud(Double.parseDouble(dato));
						}
					}
				}

				sesion.save(eNatural); tx.commit(); lineas++;
				
				sesion.close();
//				} catch (SQLException e) {
//				if (e.getErrorCode() != 1062) {
//					e.printStackTrace();
//				}
			} catch (ConstraintViolationException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[Datos/BBDD] >> espacios_naturales -> Lineas afectadas: " + lineas);
	}
	
	public static void muni_espacios(/*Connection conexion, */SessionFactory sf) {
//		PreparedStatement query;
//		String sql;
		
		ArrayList<ArrayList<String>> muni_espacios = recorrerXML("ArchivosXML","muni_espacios");
		
		int lineas = 0;
		for (int i = 0; i < muni_espacios.size(); i++) {
			try {
				Session sesion = sf.openSession();
				Transaction tx = sesion.beginTransaction();
				
//				sql = "INSERT INTO muni_espacios(cod_muni,cod_enatural,cod_muni_espacios) VALUES(?,?,?)";
//				
//				query = conexion.prepareStatement(sql);
//				query.setInt(1, Integer.parseInt(muni_espacios.get(i).get(1)));
//				query.setInt(2, Integer.parseInt(muni_espacios.get(i).get(2)));
//				query.setInt(3, Integer.parseInt(muni_espacios.get(i).get(0)));
//				
//				lineas += query.executeUpdate();
//				query.close();
				
				MuniEspacios muni_esp = new MuniEspacios();
				muni_esp.setCodMuniEspacios(Integer.parseInt(muni_espacios.get(i).get(0)));
				Municipio muni = sesion.get(Municipio.class, Integer.parseInt(muni_espacios.get(i).get(1)));
				muni_esp.setMunicipio(muni);
				EspaciosNaturales eNatural = sesion.get(EspaciosNaturales.class, Integer.parseInt(muni_espacios.get(i).get(2)));
				muni_esp.setEspaciosNaturales(eNatural);
				
				sesion.save(muni_esp); tx.commit(); lineas++;
				
				sesion.close();
//				} catch (SQLException e) {
//				if (e.getErrorCode() != 1062) {
//					e.printStackTrace();
//				}
			} catch (ConstraintViolationException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[Datos/BBDD] >> muni_espacios -> Lineas afectadas: " + lineas);
	}
	
	private static void calidad_aire(/*Connection conexion, */ArrayList<String> nomEstaciones) {
//		PreparedStatement query;
//		String sql;
		
		for (int kntEstacion = 0; kntEstacion < nomEstaciones.size(); kntEstacion++) {
			ArrayList<ArrayList<String>> calidad_aire = recorrerXML("ArchivosXML_CalidadAire",nomEstaciones.get(kntEstacion));
			
			int lineas = 0;
			for (int i = 0; i < calidad_aire.size(); i++) {
				try {
					Session sesion = sf.openSession();
					Transaction tx = sesion.beginTransaction();
					
//					sql = "INSERT INTO calidad_aire(fecha_hora,calidad,PM25,PM10,SO2,NO2,03,CO,cod_estacion) VALUES(?,?,?,?,?,?,?,?)";
//					
//					query = conexion.prepareStatement(sql);
//					query.setString(1, calidad_aire.get(i).get(0));
//					query.setString(2, calidad_aire.get(i).get(1));
//					for (int j = 2; j < 8; j++) {
//						String dato = calidad_aire.get(i).get(j).replaceAll("\\,", "\\.");
//						if (dato.length() != 0) {
//							query.setDouble(j+1, Double.parseDouble(dato));
//						} else {
//							query.setNull(j+1, Types.NULL);
//						}
//					}
//					query.setInt(3, Integer.parseInt(calidad_aire.get(i).get(8)));
//					
//					lineas += query.executeUpdate();
//					query.close();
					
					CalidadAire cAire = new CalidadAire();
					CalidadAireId cAireID = new CalidadAireId();
						cAireID.setFechaHora(Timestamp.valueOf(calidad_aire.get(i).get(0)));
						cAireID.setCodEstacion(Integer.parseInt(calidad_aire.get(i).get(8)));
					cAire.setId(cAireID);
					cAire.setCalidad(calidad_aire.get(i).get(1));
					for (int j = 2; j < 8; j++) {
						String dato = calidad_aire.get(i).get(j).replaceAll("\\,", "\\.");
						if (dato.length() != 0) {
							if (j == 2) {
								cAire.setPm25(Double.parseDouble(dato));
							} else if (j == 3) {
								cAire.setPm10(Double.parseDouble(dato));
							} else if (j == 4) {
								cAire.setSo2(Double.parseDouble(dato));
							} else if (j == 5) {
								cAire.setNo2(Double.parseDouble(dato));
							} else if (j == 6) {
								cAire.setO3(Double.parseDouble(dato));
							} else if (j == 7) {
								cAire.setCo(Double.parseDouble(dato));
							}
						}
					}
					Estaciones estacion = sesion.get(Estaciones.class, Integer.parseInt(calidad_aire.get(i).get(8)));
					cAire.setEstaciones(estacion);
					
					sesion.save(cAire); tx.commit(); lineas++;
					
					sesion.close();
//					} catch (SQLException e) {
//					if (e.getErrorCode() != 1062) {
//						e.printStackTrace();
//					}
				} catch (ConstraintViolationException e) {
					if (e.getErrorCode() != 1062) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("[Datos/BBDD] >> calidad_aire ("+nomEstaciones.get(kntEstacion)+") -> Lineas afectadas: " + lineas);
		}
	}
	
	public static void calidad_aire(/*Connection conexion, */SessionFactory sf, String nombre) {
//		PreparedStatement query;
//		String sql;
		ArrayList<ArrayList<String>> calidad_aire = recorrerXML("ArchivosXML_CalidadAire",nombre);
		
		int lineas = 0;
		for (int i = 0; i < calidad_aire.size(); i++) {
			try {
				Session sesion = sf.openSession();
				Transaction tx = sesion.beginTransaction();
				
//					sql = "INSERT INTO calidad_aire(fecha_hora,calidad,PM25,PM10,SO2,NO2,03,CO,cod_estacion) VALUES(?,?,?,?,?,?,?,?)";
//					
//					query = conexion.prepareStatement(sql);
//					query.setString(1, calidad_aire.get(i).get(0));
//					query.setString(2, calidad_aire.get(i).get(1));
//					for (int j = 2; j < 8; j++) {
//						String dato = calidad_aire.get(i).get(j).replaceAll("\\,", "\\.");
//						if (dato.length() != 0) {
//							query.setDouble(j+1, Double.parseDouble(dato));
//						} else {
//							query.setNull(j+1, Types.NULL);
//						}
//					}
//					query.setInt(3, Integer.parseInt(calidad_aire.get(i).get(8)));
//					
//					lineas += query.executeUpdate();
//					query.close();
				
				CalidadAire cAire = new CalidadAire();
				CalidadAireId cAireID = new CalidadAireId();
					cAireID.setFechaHora(Timestamp.valueOf(calidad_aire.get(i).get(0)));
					cAireID.setCodEstacion(Integer.parseInt(calidad_aire.get(i).get(8)));
				cAire.setId(cAireID);
				cAire.setCalidad(calidad_aire.get(i).get(1));
				for (int j = 2; j < 8; j++) {
					String dato = calidad_aire.get(i).get(j).replaceAll("\\,", "\\.");
					if (dato.length() != 0) {
						if (j == 2) {
							cAire.setPm25(Double.parseDouble(dato));
						} else if (j == 3) {
							cAire.setPm10(Double.parseDouble(dato));
						} else if (j == 4) {
							cAire.setSo2(Double.parseDouble(dato));
						} else if (j == 5) {
							cAire.setNo2(Double.parseDouble(dato));
						} else if (j == 6) {
							cAire.setO3(Double.parseDouble(dato));
						} else if (j == 7) {
							cAire.setCo(Double.parseDouble(dato));
						}
					}
				}
				Estaciones estacion = sesion.get(Estaciones.class, Integer.parseInt(calidad_aire.get(i).get(8)));
				cAire.setEstaciones(estacion);
				
				sesion.save(cAire); tx.commit(); lineas++;
				
				sesion.close();
//					} catch (SQLException e) {
//					if (e.getErrorCode() != 1062) {
//						e.printStackTrace();
//					}
			} catch (ConstraintViolationException e) {
				if (e.getErrorCode() != 1062) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("[Datos/BBDD] >> calidad_aire ("+nombre+") -> Lineas afectadas: " + lineas);
	}
	
}
