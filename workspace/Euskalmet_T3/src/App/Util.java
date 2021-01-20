package App;

import java.util.ArrayList;
import java.util.List;

import Hibernate.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Util {
	
	@SuppressWarnings("unchecked")
	public static String[] cmbxProvincias(SessionFactory sf) {
		ArrayList<String> ayProv = new ArrayList<String>();
		
		Session session = sf.openSession();
		List<Provincia> lProv = session.createQuery("FROM Provincia").list();
		for (int i = 0; i < lProv.size(); i++) {
			Provincia prov = lProv.get(i);
			ayProv.add(prov.getNombre());
		}
		
		return (String[]) ayProv.toArray();
	}
	
	@SuppressWarnings("unchecked")
	public static String[] lista(SessionFactory sf, String tipoLista, String prov) {
		ArrayList<String> ay = new ArrayList<String>();
		
		Session session = sf.openSession();
		if (tipoLista.equals("municipios")) {
			List<Municipio> lMuni = session.createQuery("FROM Municipio WHERE provincia.getNombre()='"+prov+"'").list();
			for (int i = 0; i < lMuni.size(); i++) {
				Municipio muni = lMuni.get(i);
				ay.add(muni.getNombre());
			}
		} else if (tipoLista.equals("espaciosN")) {
			List<String> lMuni = session.createQuery("SELECT e.nombre FROM EspaciosNaturales AS e, Municipio AS m, MuniEspacios AS me "
					+ "WHERE e.codEnatural=me.espacioNaturales.getCodEnatural() AND m.codMuni=me.municipio.getCodMuni() AND m.provincia.getNombre()='"+prov+"'").list();
			for (int i = 0; i < lMuni.size(); i++) {
				ay.add(lMuni.get(i));
			}
		}
		
		return (String[]) ay.toArray();
	}
	
}
