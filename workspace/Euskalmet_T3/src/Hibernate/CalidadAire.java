package Hibernate;
// Generated 29 ene. 2021 9:17:36 by Hibernate Tools 5.4.21.Final

/**
 * CalidadAire generated by hbm2java
 */
public class CalidadAire implements java.io.Serializable {

	private CalidadAireId id;
	private Estaciones estaciones;
	private String calidad;

	public CalidadAire() {
	}

	public CalidadAire(CalidadAireId id, Estaciones estaciones) {
		this.id = id;
		this.estaciones = estaciones;
	}

	public CalidadAire(CalidadAireId id, Estaciones estaciones, String calidad) {
		this.id = id;
		this.estaciones = estaciones;
		this.calidad = calidad;
	}

	public CalidadAireId getId() {
		return this.id;
	}

	public void setId(CalidadAireId id) {
		this.id = id;
	}

	public Estaciones getEstaciones() {
		return this.estaciones;
	}

	public void setEstaciones(Estaciones estaciones) {
		this.estaciones = estaciones;
	}

	public String getCalidad() {
		return this.calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

}
