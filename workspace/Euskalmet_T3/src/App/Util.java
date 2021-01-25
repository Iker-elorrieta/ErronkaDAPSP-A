package App;

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
	
}
