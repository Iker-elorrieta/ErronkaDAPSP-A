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
	
	@SuppressWarnings("unchecked")
	public static String texto(SessionFactory sf, String tipoDato, String dato) {
		String texto = "";
		
		if (tipoDato.equals("municipios")) {
			Session session = sf.openSession();
			
			List<Municipio> lMuni = session.createQuery("SELECT DISTINCT m FROM Municipio AS m WHERE m.nombre = '"+dato+"' ORDER BY m.nombre").list();
			for (int i = 0; i < lMuni.size(); i++) {
				Municipio muni = lMuni.get(i);
				texto = muni.getDescripcion();
			}
			session.close();
		} else if (tipoDato.equals("espaciosN")) {
			Session session = sf.openSession();
			
			List<EspaciosNaturales> lEspN = session.createQuery("SELECT DISTINCT e FROM EspaciosNaturales AS e WHERE e.nombre = '"+dato+"' ORDER BY e.nombre").list();
			for (int i = 0; i < lEspN.size(); i++) {
				EspaciosNaturales espN = lEspN.get(i);
				texto = espN.getDescripcion();
			}
			session.close();
		}
		
		return texto;
	}
	
}
