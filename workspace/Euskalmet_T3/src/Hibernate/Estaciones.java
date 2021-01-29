package Hibernate;
// Generated 29 ene. 2021 12:58:21 by Hibernate Tools 5.4.21.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Estaciones generated by hbm2java
 */
public class Estaciones implements java.io.Serializable {

	private int codEstacion;
	private Municipio municipio;
	private String nombre;
	private String direccion;
	private Double coordX;
	private Double coordY;
	private Double latitud;
	private Double longitud;
	private Set calidadAires = new HashSet(0);

	public Estaciones() {
	}

	public Estaciones(int codEstacion, Municipio municipio) {
		this.codEstacion = codEstacion;
		this.municipio = municipio;
	}

	public Estaciones(int codEstacion, Municipio municipio, String nombre, String direccion, Double coordX,
			Double coordY, Double latitud, Double longitud, Set calidadAires) {
		this.codEstacion = codEstacion;
		this.municipio = municipio;
		this.nombre = nombre;
		this.direccion = direccion;
		this.coordX = coordX;
		this.coordY = coordY;
		this.latitud = latitud;
		this.longitud = longitud;
		this.calidadAires = calidadAires;
	}

	public int getCodEstacion() {
		return this.codEstacion;
	}

	public void setCodEstacion(int codEstacion) {
		this.codEstacion = codEstacion;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getCoordX() {
		return this.coordX;
	}

	public void setCoordX(Double coordX) {
		this.coordX = coordX;
	}

	public Double getCoordY() {
		return this.coordY;
	}

	public void setCoordY(Double coordY) {
		this.coordY = coordY;
	}

	public Double getLatitud() {
		return this.latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return this.longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Set getCalidadAires() {
		return this.calidadAires;
	}

	public void setCalidadAires(Set calidadAires) {
		this.calidadAires = calidadAires;
	}

}
