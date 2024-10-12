package application.modelo;

public class Tabla
{
	private int ID_CONFIG;
	private String PESTAGNA;
	private String NOMBRE_TABLA;
	private String PK_TABLA;
	private Integer LINEA_COLUMNAS;
	private Integer LINEA_BLOQUEOS;
	private Integer LINEA_DATOS;
	private Integer COLUMNA_DATOS;

	public Tabla() {
		super();
	}

	public Tabla(int iD_CONFIG, String pESTAGNA, String nOMBRE_TABLA, String pK_TABLA, Integer lINEA_COLUMNAS,
			Integer lINEA_BLOQUEOS, Integer lINEA_DATOS, Integer cOLUMNA_DATOS) {
		super();
		ID_CONFIG = iD_CONFIG;
		PESTAGNA = pESTAGNA;
		NOMBRE_TABLA = nOMBRE_TABLA;
		PK_TABLA = pK_TABLA;
		LINEA_COLUMNAS = lINEA_COLUMNAS;
		LINEA_BLOQUEOS = lINEA_BLOQUEOS;
		LINEA_DATOS = lINEA_DATOS;
		COLUMNA_DATOS = cOLUMNA_DATOS;
	}

	public int getID_CONFIG() {
		return ID_CONFIG;
	}

	public void setID_CONFIG(int iD_CONFIG) {
		ID_CONFIG = iD_CONFIG;
	}

	public String getPESTAGNA() {
		return PESTAGNA;
	}

	public void setPESTAGNA(String pESTAGNA) {
		PESTAGNA = pESTAGNA;
	}

	public String getNOMBRE_TABLA() {
		return NOMBRE_TABLA;
	}

	public void setNOMBRE_TABLA(String nOMBRE_TABLA) {
		NOMBRE_TABLA = nOMBRE_TABLA;
	}

	public String getPK_TABLA() {
		return PK_TABLA;
	}

	public void setPK_TABLA(String pK_TABLA) {
		PK_TABLA = pK_TABLA;
	}

	public Integer getLINEA_COLUMNAS() {
		return LINEA_COLUMNAS;
	}

	public void setLINEA_COLUMNAS(Integer lINEA_COLUMNAS) {
		LINEA_COLUMNAS = lINEA_COLUMNAS;
	}

	public Integer getLINEA_BLOQUEOS() {
		return LINEA_BLOQUEOS;
	}

	public void setLINEA_BLOQUEOS(Integer lINEA_BLOQUEOS) {
		LINEA_BLOQUEOS = lINEA_BLOQUEOS;
	}

	public Integer getLINEA_DATOS() {
		return LINEA_DATOS;
	}

	public void setLINEA_DATOS(Integer lINEA_DATOS) {
		LINEA_DATOS = lINEA_DATOS;
	}
	
	public Integer getCOLUMNA_DATOS() {
		return COLUMNA_DATOS;
	}

	public void setCOLUMNA_DATOS(Integer cOLUMNA_DATOS) {
		COLUMNA_DATOS = cOLUMNA_DATOS;
	}

	@Override
	public String toString() {
		return PESTAGNA;
	}
}
