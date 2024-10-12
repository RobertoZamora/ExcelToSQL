package application.modelo;

public class Configuracion {
	private int ID;
	private String DES_CONFIGURACION;
		
	public Configuracion() {
		super();
	}
	
	public Configuracion(int iD, String dES_CONFIGURACION) {
		super();
		ID = iD;
		DES_CONFIGURACION = dES_CONFIGURACION;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getDES_CONFIGURACION() {
		return DES_CONFIGURACION;
	}
	public void setDES_CONFIGURACION(String dES_CONFIGURACION) {
		DES_CONFIGURACION = dES_CONFIGURACION;
	}

	@Override
	public String toString() {
		return DES_CONFIGURACION;
	}

}
