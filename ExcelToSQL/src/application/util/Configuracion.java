package application.util;

import java.util.List;

import application.modelo.Accion;
import application.modelo.Binaria;
import application.modelo.CamposTipo;
import application.modelo.ConfigColores;
import application.modelo.Simplificada;
import application.modelo.Tabla;
import application.modelo.Traducida;

class Configuracion {
    private int id;
    private String DES_CONFIGURACION;
    private List<ConfigColores> CONFIG_COLORES;
    private List<Tabla> TABLAS;
    private List<Accion> ACCIONES;
    private List<Binaria> BINARIAS;
    private List<Traducida> TRADUCCIONES;
    private List<Simplificada> DATOS_SIMPLIFICADOS;
    private List<Parametrizada> DATOS_PARAMETRIZADOS;
    private List<CamposTipo> CAMPOS_TIPO;
    
	public Configuracion() {
		super();
	}
	public Configuracion(int id, String dES_CONFIGURACION, List<ConfigColores> cONFIG_COLORES, List<Tabla> tABLAS,
			List<Accion> aCCIONES, List<Binaria> bINARIAS, List<Traducida> tRADUCCIONES,
			List<Simplificada> dATOS_SIMPLIFICADOS, List<Parametrizada> dATOS_PARAMETRIZADOS,
			List<CamposTipo> cAMPOS_TIPO) {
		super();
		this.id = id;
		DES_CONFIGURACION = dES_CONFIGURACION;
		CONFIG_COLORES = cONFIG_COLORES;
		TABLAS = tABLAS;
		ACCIONES = aCCIONES;
		BINARIAS = bINARIAS;
		TRADUCCIONES = tRADUCCIONES;
		DATOS_SIMPLIFICADOS = dATOS_SIMPLIFICADOS;
		DATOS_PARAMETRIZADOS = dATOS_PARAMETRIZADOS;
		CAMPOS_TIPO = cAMPOS_TIPO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDES_CONFIGURACION() {
		return DES_CONFIGURACION;
	}
	
	public void setDES_CONFIGURACION(String descripcion) {
		this.DES_CONFIGURACION = descripcion;
	}
	
	public List<ConfigColores> getCONFIG_COLORES() {
		return CONFIG_COLORES;
	}
	
	public void setCONFIG_COLORES(List<ConfigColores> colores) {
		this.CONFIG_COLORES = colores;
	}
	
	public List<Tabla> getTABLAS() {
		return TABLAS;
	}
	
	public void setTABLAS(List<Tabla> tablas) {
		this.TABLAS = tablas;
	}
	
	public List<Accion> getACCIONES() {
		return ACCIONES;
	}
	
	public void setACCIONES(List<Accion> acciones) {
		this.ACCIONES = acciones;
	}
	
	public List<Binaria> getBINARIAS() {
		return BINARIAS;
	}
	
	public void setBINARIAS(List<Binaria> binarias) {
		this.BINARIAS = binarias;
	}
	
	public List<Traducida> getTRADUCCIONES() {
		return TRADUCCIONES;
	}
	
	public void setTRADUCCIONES(List<Traducida> traducciones) {
		this.TRADUCCIONES = traducciones;
	}
	
	public List<Simplificada> getDATOS_SIMPLIFICADOS() {
		return DATOS_SIMPLIFICADOS;
	}
	
	public void setDATOS_SIMPLIFICADOS(List<Simplificada> datosSimplificados) {
		this.DATOS_SIMPLIFICADOS = datosSimplificados;
	}
	
	public List<Parametrizada> getDATOS_PARAMETRIZADOS() {
		return DATOS_PARAMETRIZADOS;
	}
	
	public void setDATOS_PARAMETRIZADOS(List<Parametrizada> datosParametrizados) {
		this.DATOS_PARAMETRIZADOS = datosParametrizados;
	}
	
	public List<CamposTipo> getCAMPOS_TIPO() {
		return CAMPOS_TIPO;
	}
	
	public void setCAMPOS_TIPO(List<CamposTipo> camposTipo) {
		this.CAMPOS_TIPO = camposTipo;
	}
}