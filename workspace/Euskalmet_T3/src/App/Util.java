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
	public static String[] lista(SessionFactory sf, ArrayList<?> ay, String tipoLista, String prov) {
		ArrayList<String> ayStr = new ArrayList<String>();
		
		if (tipoLista.equals("municipios")) {
			for (int i = 0; i < ay.size(); i++) {
				Session session = sf.openSession();
				Municipio muni = (Municipio) ay.get(i);
				List<Municipio> lMuni = session.createQuery("SELECT DISTINCT m FROM Municipio AS m WHERE m.provincia.nombre = '"+prov+"'").list();
				for (int j = 0; j < lMuni.size(); j++) {
					Municipio muni2 = lMuni.get(j);
					if (muni.getCodMuni() == muni2.getCodMuni()) 
						ayStr.add(muni.getNombre());
				}
				session.close();
			}
		} else if (tipoLista.equals("espaciosN")) {
			for (int i = 0; i < ay.size(); i++) {
				Session session = sf.openSession();
				EspaciosNaturales espN = (EspaciosNaturales) ay.get(i);
				List<EspaciosNaturales> lEspN = session.createQuery("SELECT DISTINCT me.espaciosNaturales FROM MuniEspacios AS me WHERE me.municipio.provincia.nombre = '"+prov+"'").list();
				for (int j = 0; j < lEspN.size(); j++) {
					EspaciosNaturales espN2 = lEspN.get(j);
					if (espN.getCodEnatural() == espN2.getCodEnatural()) 
						ayStr.add(espN.getNombre());
				}
				session.close();
			}
		}
		
		return ayStr.toArray(new String[ayStr.size()]);
	}
	
}
