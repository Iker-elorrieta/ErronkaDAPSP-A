package Hibernate;
// Generated 19 ene. 2021 14:14:09 by Hibernate Tools 5.4.21.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Municipio generated by hbm2java
 */
public class Municipio implements java.io.Serializable {

	private int codMuni;
	private Provincia provincia;
	private String nombre;
	private String descripcion;
	private String foto;
	private Set estacioneses = new HashSet(0);
	private Set muniEspacioses = new HashSet(0);

	public Municipio() {
	}

	public Municipio(int codMuni, Provincia provincia) {
		this.codMuni = codMuni;
		this.provincia = provincia;
	}

	public Municipio(int codMuni, Provincia provincia, String nombre, String descripcion, String foto, Set estacioneses,
			Set muniEspacioses) {
		this.codMuni = codMuni;
		this.provincia = provincia;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.foto = foto;
		this.estacioneses = estacioneses;
		this.muniEspacioses = muniEspacioses;
	}

	public int getCodMuni() {
		return this.codMuni;
	}

	public void setCodMuni(int codMuni) {
		this.codMuni = codMuni;
	}

	public Provincia getProvincia() {
		return this.provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Set getEstacioneses() {
		return this.estacioneses;
	}

	public void setEstacioneses(Set estacioneses) {
		this.estacioneses = estacioneses;
	}

	public Set getMuniEspacioses() {
		return this.muniEspacioses;
	}

	public void setMuniEspacioses(Set muniEspacioses) {
		this.muniEspacioses = muniEspacioses;
	}

}
