// default package
// Generated 19 ene. 2021 8:41:31 by Hibernate Tools 5.4.21.Final

/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	private Integer codUsuario;
	private String nombre;
	private String contraseña;
	private String PClave;

	public Usuario() {
	}

	public Usuario(String nombre, String contraseña, String PClave) {
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.PClave = PClave;
	}

	public Integer getCodUsuario() {
		return this.codUsuario;
	}

	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return this.contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getPClave() {
		return this.PClave;
	}

	public void setPClave(String PClave) {
		this.PClave = PClave;
	}

}
