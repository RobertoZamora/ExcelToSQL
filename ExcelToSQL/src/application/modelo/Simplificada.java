package application.modelo;

public class Simplificada {
	private int ID_CONFIG;
	private String NOMBRE_TABLA;
	private String PESTAGNA;
	private String COLUMNA_SIMPLIFICADA;
	private String COLUMNA;
	
	public Simplificada() {
		super();
	}

	public Simplificada(int iD_CONFIG, String nOMBRE_TABLA, String cOLUMNA_SIMPLIFICADA, String cOLUMNA, String pESTAGNA) {
		super();
		ID_CONFIG = iD_CONFIG;
		NOMBRE_TABLA = nOMBRE_TABLA;
		PESTAGNA = pESTAGNA;
		COLUMNA_SIMPLIFICADA = cOLUMNA_SIMPLIFICADA;
		COLUMNA = cOLUMNA;
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

	public String getCOLUMNA_SIMPLIFICADA() {
		return COLUMNA_SIMPLIFICADA;
	}

	public void setCOLUMNA_SIMPLIFICADA(String cOLUMNA_SIMPLIFICADA) {
		COLUMNA_SIMPLIFICADA = cOLUMNA_SIMPLIFICADA;
	}

	public String getCOLUMNA() {
		return COLUMNA;
	}

	public void setCOLUMNA(String cOLUMNA) {
		COLUMNA = cOLUMNA;
	}

	@Override
	public String toString() {
		return COLUMNA_SIMPLIFICADA;
	}
}
