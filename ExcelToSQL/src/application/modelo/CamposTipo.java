package application.modelo;

public class CamposTipo {
	private int ID_CONFIG;
	private String NOMBRE_TABLA;
	private String CAMPO;
	private String TIPO;
	
	public CamposTipo() {
		super();
	}

	public CamposTipo(int iD_CONFIG, String nOMBRE_TABLA, String cAMPO, String tIPO) {
		super();
		ID_CONFIG = iD_CONFIG;
		NOMBRE_TABLA = nOMBRE_TABLA;
		CAMPO = cAMPO;
		TIPO = tIPO;
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

	public String getCAMPO() {
		return CAMPO;
	}

	public void setCAMPO(String cAMPO) {
		CAMPO = cAMPO;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String tIPO) {
		TIPO = tIPO;
	}

	@Override
	public String toString() {
		return NOMBRE_TABLA + "─" + CAMPO;
	}
}
