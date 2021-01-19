package Hibernate;
// Generated 19 ene. 2021 14:14:09 by Hibernate Tools 5.4.21.Final

/**
 * MuniEspacios generated by hbm2java
 */
public class MuniEspacios implements java.io.Serializable {

	private MuniEspaciosId id;
	private EspaciosNaturales espaciosNaturales;
	private Municipio municipio;

	public MuniEspacios() {
	}

	public MuniEspacios(MuniEspaciosId id, EspaciosNaturales espaciosNaturales, Municipio municipio) {
		this.id = id;
		this.espaciosNaturales = espaciosNaturales;
		this.municipio = municipio;
	}

	public MuniEspaciosId getId() {
		return this.id;
	}

	public void setId(MuniEspaciosId id) {
		this.id = id;
	}

	public EspaciosNaturales getEspaciosNaturales() {
		return this.espaciosNaturales;
	}

	public void setEspaciosNaturales(EspaciosNaturales espaciosNaturales) {
		this.espaciosNaturales = espaciosNaturales;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

}
