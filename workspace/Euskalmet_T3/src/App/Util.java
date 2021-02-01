package App;

import java.util.ArrayList;
import java.util.List;

import Hibernate.CalidadAire;
import Hibernate.EspaciosNaturales;
import Hibernate.Municipio;
import Hibernate.Provincia;

public class Util {
	
	public static ArrayList<ArrayList<Object>> visitas(List<Object> lista, String tipo) {
		ArrayList<ArrayList<Object>> visitas = new ArrayList<ArrayList<Object>>();
		
		for (int i = 0; i < lista.size(); i++) {
			ArrayList<Object> entrada = new ArrayList<Object>();
			
			if (tipo.equals("municipios")) {
				Municipio muni = (Municipio) lista.get(i);
				entrada.add(muni.getNombre());
				entrada.add(0);
			} else if (tipo.equals("espaciosN")) {
				EspaciosNaturales espN = (EspaciosNaturales) lista.get(i);
				entrada.add(espN.getNombre());
				entrada.add(0);
			}
			
			visitas.add(entrada);
		}
		
		return visitas;
	}
	
	public static String[] cmbxProvincias(List<Object> lista) {
		ArrayList<String> ayProv = new ArrayList<String>();
		
		for (int i = 0; i < lista.size(); i++) {
			Provincia prov = (Provincia) lista.get(i);
			ayProv.add(prov.getNombre());
		}
		
		return ayProv.toArray(new String[ayProv.size()]);
	}
	
	public static String[] lista(List<Object> lista, String tipoLista) {
		ArrayList<String> ayStr = new ArrayList<String>();
		
		for (int i = 0; i < lista.size(); i++) {
			if (tipoLista.equals("municipios")) {
				Municipio muni = (Municipio) lista.get(i);
				ayStr.add(muni.getNombre());
			} else if (tipoLista.equals("espaciosN")) {
				Object[] lEspN = (Object[]) lista.get(i);
				EspaciosNaturales espN = (EspaciosNaturales) lEspN[0];
				if (!ayStr.contains(espN.getNombre())) 
					ayStr.add(espN.getNombre());
			} else if (tipoLista.equals("Playas")) {
				EspaciosNaturales espN = (EspaciosNaturales) lista.get(i);
				if (espN.getNombre().contains("Playa")) 
					ayStr.add(espN.getNombre());
			}
		}
		
		return ayStr.toArray(new String[ayStr.size()]);
	}
	
	public static String[] top(ArrayList<ArrayList<Object>> lista, String topStr) {
		ArrayList<String> ayStr = new ArrayList<String>();
		
		int top = 5;
		if (topStr.equals("TOP 5")) 
			top = 5;
		else if (topStr.equals("TOP 10")) 
			top = 10;
		else if (topStr.equals("TOP 20")) 
			top = 20;
		
		for (int i = 0; i < top; i++) {
			ArrayList<Object> entrada = lista.get(i);
			
			if ((int) entrada.get(1) == 0) 
				break;
			
			ayStr.add("<"+(i+1)+"> "+entrada.get(0).toString()+"(Visitas: "+entrada.get(1).toString()+")"+" <"+(i+1)+">");
		}

		return ayStr.toArray(new String[ayStr.size()]);
	}
	
	public static String texto(List<Object> lista, String tipoDato) {
		String texto = "";
		
		for (int i = 0; i < lista.size(); i++) {
			if (tipoDato.equals("municipios")) {
				Municipio muni = (Municipio) lista.get(i);
				texto = muni.getDescripcion();
				break;
			} else if (tipoDato.equals("espaciosN")) {
				Object[] lEspN = (Object[]) lista.get(i);
				EspaciosNaturales espN = (EspaciosNaturales) lEspN[0];
				texto = espN.getDescripcion();
				break;
			}
		}
		
		return texto;
	}
	
	public static ArrayList<String> muniEspN(List<Object> lista, String nombre) {
		ArrayList<String> municipios = new ArrayList<String>();
		
		for (int i = 0; i < lista.size(); i++) {
			Object[] lEspN = (Object[]) lista.get(i);
			EspaciosNaturales espN = (EspaciosNaturales) lEspN[0];
			if (espN.getNombre().equals(nombre)) {
				municipios.add(lEspN[1].toString());
			}
		}
		
		return municipios;
	}
	
	public static String[] cmbxEstaciones(List<Object> lista, ArrayList<String> municipios) {
		ArrayList<String> ayEst = new ArrayList<String>();
		
		for (int i = 0; i < lista.size(); i++) {
			Object[] est = (Object[]) lista.get(i);
			if (municipios.contains(est[2].toString())) {
				String direccion = est[1].toString();
				if (!ayEst.contains(direccion)) 
					ayEst.add(direccion);
			}
		}
		
		return ayEst.toArray(new String[ayEst.size()]);
	}
	
	public static String historico(List<Object> lista, String direccion) {
		String historico = "";
		
		for (int i = 0; i < lista.size(); i++) {
			Object[] est = (Object[]) lista.get(i);
			if (((String) est[1]).equals(direccion)) {
				String fechaHora = ((CalidadAire) est[0]).getId().getFechaHora().toString();
				String calidad = ((CalidadAire) est[0]).getCalidad();
				if (calidad.length() != 0) {
					if (!calidad.contains("datos")) {
						historico += ">> " + fechaHora + " >> " + calidad + ". \n";
						historico += "\t >> ";
								
						Object[][] ayCA = {
								{"PM2.5",((CalidadAire) est[0]).getPm25()},
								{"PM10",((CalidadAire) est[0]).getPm10()},
								{"SO2",((CalidadAire) est[0]).getSo2()},
								{"NO2",((CalidadAire) est[0]).getNo2()},
								{"O3",((CalidadAire) est[0]).getO3()},
								{"CO",((CalidadAire) est[0]).getCo()}
							};
						
						int entrada = 0;
						for (int j = 0; j < ayCA.length; j++) {
							if (ayCA[j][1] != null) {
								if (entrada == 0) {
									historico += " | " + ayCA[j][0].toString() + ": " + ayCA[j][1].toString().replaceAll("\\.", "\\,") + " | ";
								} else {
									historico += ayCA[j][0].toString() + ": " + ayCA[j][1].toString().replaceAll("\\.", "\\,") + " | ";
								}
								
								entrada++;
							}
						}
						
						historico += "\n\n";
					}
				}
			}
		}
		
		return historico;
	}
	
}
