package Hibernate;
// Generated 26 ene. 2021 12:32:07 by Hibernate Tools 5.4.21.Final

/**
 * MuniEspacios generated by hbm2java
 */
public class MuniEspacios implements java.io.Serializable {

	private int codMuniEspacios;
	private EspaciosNaturales espaciosNaturales;
	private Municipio municipio;

	public MuniEspacios() {
	}

	public MuniEspacios(int codMuniEspacios, EspaciosNaturales espaciosNaturales, Municipio municipio) {
		this.codMuniEspacios = codMuniEspacios;
		this.espaciosNaturales = espaciosNaturales;
		this.municipio = municipio;
	}

	public int getCodMuniEspacios() {
		return this.codMuniEspacios;
	}

	public void setCodMuniEspacios(int codMuniEspacios) {
		this.codMuniEspacios = codMuniEspacios;
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
