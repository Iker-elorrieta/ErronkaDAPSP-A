package App;

import java.util.ArrayList;
import java.util.List;

import Hibernate.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Util {
	
	public static String[] cmbxProvincias(ArrayList<Provincia> ay) {
		ArrayList<String> ayProv = new ArrayList<String>();
		
		for (int i = 0; i < ay.size(); i++) {
			Provincia prov = ay.get(i);
			ayProv.add(prov.getNombre());
		}
		
		return (String[]) ayProv.toArray();
	}
	
	public static String[] lista(SessionFactory sf, ArrayList<Object> ay, String tipoLista, String prov) {
		ArrayList<String> ayStr = new ArrayList<String>();
		
		if (tipoLista.equals("municipios")) {
			for (int i = 0; i < ay.size(); i++) {
				Municipio muni = (Municipio) ay.get(i);
				if (muni.getProvincia().getNombre().equals(prov))
					ayStr.add(muni.getNombre());
			}
		} else if (tipoLista.equals("espaciosN")) {
			for (int i = 0; i < ay.size(); i++) {
				Session session = sf.openSession();
				EspaciosNaturales espN = (EspaciosNaturales) ay.get(i);
				
				if (espN.getCodEnatural() == )
				ayStr.add(ay.get(i));
			}
		}
		
		return (String[]) ayStr.toArray();
	}
	
}
