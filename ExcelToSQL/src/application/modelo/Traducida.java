package application.modelo;

public class Traducida {
	private int ID_CONFIG;
	private String NOMBRE_TABLA;
	private String PESTAGNA;
	private String COLUMNA_EXCEL;
	private String COLUMNA_BBDD;
	
	public Traducida() {
		super();
	}

	public Traducida(int iD_CONFIG, String nOMBRE_TABLA, String cOLUMNA_EXCEL, String cOLUMNA_BBDD, String pESTAGNA) {
		super();
		ID_CONFIG = iD_CONFIG;
		NOMBRE_TABLA = nOMBRE_TABLA;
		PESTAGNA = pESTAGNA;
		COLUMNA_EXCEL = cOLUMNA_EXCEL;
		COLUMNA_BBDD = cOLUMNA_BBDD;
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

	public String getCOLUMNA_EXCEL() {
		return COLUMNA_EXCEL;
	}

	public void setCOLUMNA_EXCEL(String cOLUMNA_EXCEL) {
		COLUMNA_EXCEL = cOLUMNA_EXCEL;
	}

	public String getCOLUMNA_BBDD() {
		return COLUMNA_BBDD;
	}

	public void setCOLUMNA_BBDD(String cOLUMNA_BBDD) {
		COLUMNA_BBDD = cOLUMNA_BBDD;
	}

	@Override
	public String toString() {
		return COLUMNA_EXCEL + " ─ " + COLUMNA_BBDD;
	}
}
