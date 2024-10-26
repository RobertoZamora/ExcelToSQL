package application.util;

public class Parametrizada {
	private int ID_CONFIG;
	private String NOMBRE_TABLA;
	private String PESTAGNA;
	private String COLUMNA_PARAMETRIZADA;
	private String DATO;
	private String TRADUCCION;
	
	public Parametrizada() {
		super();
	}

	public Parametrizada(int iD_CONFIG, String nOMBRE_TABLA, String cOLUMNA_PARAMETRIZADA, String dATO,
			String tRADUCCION, String pESTAGNA) {
		super();
		ID_CONFIG = iD_CONFIG;
		NOMBRE_TABLA = nOMBRE_TABLA;
		PESTAGNA = pESTAGNA;
		COLUMNA_PARAMETRIZADA = cOLUMNA_PARAMETRIZADA;
		DATO = dATO;
		TRADUCCION = tRADUCCION;
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

	public String getCOLUMNA_PARAMETRIZADA() {
		return COLUMNA_PARAMETRIZADA;
	}

	public void setCOLUMNA_PARAMETRIZADA(String cOLUMNA_PARAMETRIZADA) {
		COLUMNA_PARAMETRIZADA = cOLUMNA_PARAMETRIZADA;
	}

	public String getDATO() {
		return DATO;
	}

	public void setDATO(String dATO) {
		DATO = dATO;
	}

	public String getTRADUCCION() {
		return TRADUCCION;
	}

	public void setTRADUCCION(String tRADUCCION) {
		TRADUCCION = tRADUCCION;
	}

	@Override
	public String toString() {
		return COLUMNA_PARAMETRIZADA;
	}
}