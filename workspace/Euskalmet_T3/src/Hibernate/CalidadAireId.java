package Hibernate;
// Generated 19 ene. 2021 13:22:22 by Hibernate Tools 5.4.21.Final

import java.util.Date;

/**
 * CalidadAireId generated by hbm2java
 */
public class CalidadAireId implements java.io.Serializable {

	private Date fechaHora;
	private int codEstacion;

	public CalidadAireId() {
	}

	public CalidadAireId(Date fechaHora, int codEstacion) {
		this.fechaHora = fechaHora;
		this.codEstacion = codEstacion;
	}

	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getCodEstacion() {
		return this.codEstacion;
	}

	public void setCodEstacion(int codEstacion) {
		this.codEstacion = codEstacion;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CalidadAireId))
			return false;
		CalidadAireId castOther = (CalidadAireId) other;

		return ((this.getFechaHora() == castOther.getFechaHora()) || (this.getFechaHora() != null
				&& castOther.getFechaHora() != null && this.getFechaHora().equals(castOther.getFechaHora())))
				&& (this.getCodEstacion() == castOther.getCodEstacion());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFechaHora() == null ? 0 : this.getFechaHora().hashCode());
		result = 37 * result + this.getCodEstacion();
		return result;
	}

}
