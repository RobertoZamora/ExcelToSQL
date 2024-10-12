package application.modelo;

public class ConfigColores {
	private int ID_CONFIG;
	private String ACCION;
	private String COLOR_HEXA;
	private String COLOR_HSSF;
	
	public ConfigColores() {
		super();
	}

	public ConfigColores(int iD_CONFIG, String aCCION, String cOLOR_HEXA, String cOLOR_HSSF) {
		super();
		ID_CONFIG = iD_CONFIG;
		ACCION = aCCION;
		COLOR_HEXA = cOLOR_HEXA;
		COLOR_HSSF = cOLOR_HSSF;
	}

	public int getID_CONFIG() {
		return ID_CONFIG;
	}

	public void setID_CONFIG(int iD_CONFIG) {
		ID_CONFIG = iD_CONFIG;
	}

	public String getACCION() {
		return ACCION;
	}

	public void setACCION(String aCCION) {
		ACCION = aCCION;
	}

	public String getCOLOR_HEXA() {
		return COLOR_HEXA;
	}

	public void setCOLOR_HEXA(String cOLOR_HEXA) {
		COLOR_HEXA = cOLOR_HEXA;
	}

	public String getCOLOR_HSSF() {
		return COLOR_HSSF;
	}

	public void setCOLOR_HSSF(String cOLOR_HSSF) {
		COLOR_HSSF = cOLOR_HSSF;
	}
}
