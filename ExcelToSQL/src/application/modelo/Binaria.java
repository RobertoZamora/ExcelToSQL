package application.modelo;

public class Binaria {
	private int ID_CONFIG;
	private String NOMBRE_TABLA;
	private String PESTAGNA;
	private String COLUMNA_BINARIA;
	
	public Binaria() {
		super();
	}

	public Binaria(int iD_CONFIG, String nOMBRE_TABLA, String cOLUMNA_BINARIA, String pESTAGNA) {
		super();
		ID_CONFIG = iD_CONFIG;
		NOMBRE_TABLA = nOMBRE_TABLA;
		PESTAGNA = pESTAGNA;
		COLUMNA_BINARIA = cOLUMNA_BINARIA;
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

	public String getCOLUMNA_BINARIA() {
		return COLUMNA_BINARIA;
	}

	public void setCOLUMNA_BINARIA(String cOLUMNA_BINARIA) {
		COLUMNA_BINARIA = cOLUMNA_BINARIA;
	}

	@Override
	public String toString() {
		return COLUMNA_BINARIA;
	}
}
