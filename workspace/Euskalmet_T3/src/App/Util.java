package App;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import Hibernate.EspaciosNaturales;
import Hibernate.Municipio;
import Hibernate.Provincia;

public class Util {
	
	public static String[] cmbxProvincias(List<Object> ay) {
		ArrayList<String> ayProv = new ArrayList<String>();
		
		for (int i = 0; i < ay.size(); i++) {
			Provincia prov = (Provincia) ay.get(i);
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
				EspaciosNaturales espN = (EspaciosNaturales) lista.get(i);
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
			} else if (tipoDato.equals("espaciosN")) {
				EspaciosNaturales espN = (EspaciosNaturales) lista.get(i);
				texto = espN.getDescripcion();
			}
		}
		
		return texto;
	}
	
	public static String[] cmbxEstaciones(List<Object> ay, String nombre) {
		ArrayList<String> ayEst = new ArrayList<String>();
		
		for (int i = 0; i < ay.size(); i++) {
			Object[] est = (Object[]) ay.get(i);
			if (((String) est[3]).equals(nombre)) {
				String direccion = (String) est[2];
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
				if (calidad.length() == 0) {
					historico += ">> " + fechaHora + " >> Sin datos. \n\n";
				} else {
					historico += ">> " + fechaHora + " >> " + calidad + ". \n\n";
				}
			}
		}
		
		return historico;
	}
	
}
