package application.modelo;

public class Accion {
	private int ID_CONFIG;
	private String NOMBRE_TABLA;
	private String PESTAGNA;
	private String ACCION;
	
	public Accion() {
		super();
	}

	public Accion(int iD_CONFIG, String nOMBRE_TABLA, String aCCION, String pESTAGNA) {
		super();
		ID_CONFIG = iD_CONFIG;
		NOMBRE_TABLA = nOMBRE_TABLA;
		ACCION = aCCION;
		PESTAGNA = pESTAGNA;
	}

	public int getID_CONFIG() {
		return ID_CONFIG;
	}

	public void setID_CONFIG(int iD_CONFIG) {
		ID_CONFIG = iD_CONFIG;
	}

	public String getNOMBRE_TABLA() {
		return NOMBRE_TABLA;
	}

	public void setNOMBRE_TABLA(String nOMBRE_TABLA) {
		NOMBRE_TABLA = nOMBRE_TABLA;
	}

	public String getPESTAGNA() {
		return PESTAGNA;
	}

	public void setPESTAGNA(String pESTAGNA) {
		PESTAGNA = pESTAGNA;
	}

	public String getACCION() {
		return ACCION;
	}

	public void setACCION(String aCCION) {
		ACCION = aCCION;
	}

	@Override
	public String toString() {
		return "Accion [ID_CONFIG=" + ID_CONFIG + ", NOMBRE_TABLA=" + NOMBRE_TABLA + ", ACCION=" + ACCION + "]";
	}
}
