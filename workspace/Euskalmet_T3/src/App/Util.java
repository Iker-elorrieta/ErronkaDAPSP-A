package App;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import Hibernate.EspaciosNaturales;
import Hibernate.Municipio;
import Hibernate.Provincia;

public class Util {
	
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
			}
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
			if (municipios.contains(est[3].toString())) {
				String direccion = est[2].toString();
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
			if (((String) est[2]).equals(direccion)) {
				String fechaHora = ((Timestamp) est[0]).toString();
				String calidad = (String) est[1];
				if (calidad.length() != 0) {
					if (!calidad.contains("datos")) 
						historico += ">> " + fechaHora + " >> " + calidad + ". \n\n";
				}
			}
		}
		
		return historico;
	}
	
}
