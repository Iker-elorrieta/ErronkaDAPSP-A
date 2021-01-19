// default package
// Generated 19 ene. 2021 8:41:31 by Hibernate Tools 5.4.21.Final

/**
 * MuniEspaciosId generated by hbm2java
 */
public class MuniEspaciosId implements java.io.Serializable {

	private int codMuni;
	private int codEnatural;

	public MuniEspaciosId() {
	}

	public MuniEspaciosId(int codMuni, int codEnatural) {
		this.codMuni = codMuni;
		this.codEnatural = codEnatural;
	}

	public int getCodMuni() {
		return this.codMuni;
	}

	public void setCodMuni(int codMuni) {
		this.codMuni = codMuni;
	}

	public int getCodEnatural() {
		return this.codEnatural;
	}

	public void setCodEnatural(int codEnatural) {
		this.codEnatural = codEnatural;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MuniEspaciosId))
			return false;
		MuniEspaciosId castOther = (MuniEspaciosId) other;

		return (this.getCodMuni() == castOther.getCodMuni()) && (this.getCodEnatural() == castOther.getCodEnatural());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodMuni();
		result = 37 * result + this.getCodEnatural();
		return result;
	}

}
