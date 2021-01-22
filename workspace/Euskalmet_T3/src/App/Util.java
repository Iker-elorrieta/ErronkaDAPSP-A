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
	
	@SuppressWarnings("unchecked")
	public static String[] lista(SessionFactory sf, String tipoLista, String prov) {
		ArrayList<String> ayStr = new ArrayList<String>();
		
		if (tipoLista.equals("municipios")) {
			Session session = sf.openSession();
			
			List<Municipio> lMuni = session.createQuery("SELECT DISTINCT m FROM Municipio AS m WHERE m.provincia.nombre = '"+prov+"' ORDER BY m.nombre").list();
			for (int i = 0; i < lMuni.size(); i++) {
				Municipio muni = lMuni.get(i);
				ayStr.add(muni.getNombre());
			}
			session.close();
		} else if (tipoLista.equals("espaciosN")) {
			Session session = sf.openSession();
			
			List<EspaciosNaturales> lEspN = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = '"+prov+"' "
					+ "ORDER BY me.espaciosNaturales.nombre").list();
			for (int i = 0; i < lEspN.size(); i++) {
				EspaciosNaturales espN = lEspN.get(i);
				ayStr.add(espN.getNombre());
			}
			session.close();
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
