package App;

import java.util.List;

public class Contenedor {
	
	private List<Object> ayProv;
	private List<Object> ayMuniBizkaia;
	private List<Object> ayMuniGipuzkoa;
	private List<Object> ayMuniAraba;
	private List<Object> ayEspNBizkaia;
	private List<Object> ayEspNGipuzkoa;
	private List<Object> ayEspNAraba;
	private List<Object> ayMuniCAire;
	
	public Contenedor(List<List<Object>> ayDatos) {
		ayProv = ayDatos.get(0);
		ayMuniBizkaia = ayDatos.get(1);
		ayMuniGipuzkoa = ayDatos.get(2);
		ayMuniAraba = ayDatos.get(3);
		ayEspNBizkaia = ayDatos.get(4);
		ayEspNGipuzkoa = ayDatos.get(5);
		ayEspNAraba = ayDatos.get(6);
		ayMuniCAire = ayDatos.get(7);
	}
	
	public List<Object> getAyProv() {
		return ayProv;
	}
	
	public List<Object> getAyMuniBizkaia() {
		return ayMuniBizkaia;
	}
	
	public List<Object> getAyMuniGipuzkoa() {
		return ayMuniGipuzkoa;
	}
	
	public List<Object> getAyMuniAraba() {
		return ayMuniAraba;
	}
	
	public List<Object> getAyEspNBizkaia() {
		return ayEspNBizkaia;
	}
	
	public List<Object> getAyEspNGipuzkoa() {
		return ayEspNGipuzkoa;
	}
	
	public List<Object> getAyEspNAraba() {
		return ayEspNAraba;
	}
	
	public List<Object> getAyMuniCAire() {
		return ayMuniCAire;
	}
	
}
