package application.util;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.BBDD.ConexionBBDD;
import application.BBDD.DBQuery;
import application.constantes.Constantes;
import application.modelo.Accion;
import application.modelo.Binaria;
import application.modelo.CamposTipo;
import application.modelo.ConfigColores;
import application.modelo.Simplificada;
import application.modelo.Tabla;
import application.modelo.Traducida;
import application.trazas.Trazas;

import java.io.File;
import java.util.ArrayList;

public class ConfiguracionJsonCreator
{
	private Trazas log;
	private int id;
	private String descripcion;
	

	public ConfiguracionJsonCreator(Trazas log)
	{
		this.log = log;
	}
	
	public ConfiguracionJsonCreator(int idConfig, String descripcion, Trazas log)
	{
		this.id = idConfig;
		this.descripcion = descripcion;
		this.log = log;
	}

    public void generarJson(File fileJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            
            Configuracion config = obtenerConfiguraciones(); // Simulación de datos
            
            mapper.writeValue(fileJson, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para leer JSON y convertirlo en objeto
    public void leerJsonEInsertarEnBBDD(File jsonFile) {
        try {
            // Leer el archivo JSON
            ObjectMapper mapper = new ObjectMapper();

            // Convertir el archivo JSON en un objeto ConfiguracionWrapper
            Configuracion configuracion = mapper.readValue(jsonFile, Configuracion.class);

            // Conectar a la base de datos
            ConexionBBDD conn = new ConexionBBDD(log);
            conn.conectar();

            // Iterar sobre cada configuración y guardar en la BBDD
            insertarConfiguracionEnBBDD(configuracion, conn);

            // Desconectar de la base de datos
            conn.desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertarConfiguracionEnBBDD(Configuracion config, ConexionBBDD conn) {
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam[1] = String.valueOf(config.getDES_CONFIGURACION());
		param.add(auxParam);
		application.modelo.Configuracion configuracion = conn.recuperarDato(DBQuery.SQL_CONFIGURACION, param, application.modelo.Configuracion.class);
    	System.out.println(configuracion);

    	
    	int id = conn.contarRegistros(Constantes.TABLA_CONFIGURACION) + 1;
		configuracion.setID(id);
		conn.insertarDato(Constantes.TABLA_CONFIGURACION, configuracion);
		
		for(int i = 0; i < config.getCONFIG_COLORES().size(); i++)
		{
			ConfigColores color = config.getCONFIG_COLORES().get(i);
			color.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.CONFIG_COLORES, color);			
		}
		
		for(int i = 0; i < config.getACCIONES().size(); i++)
		{
			Accion accion = config.getACCIONES().get(i);
			accion.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.T_ACCIONES, accion);			
		}
		
		for(int i = 0; i < config.getTABLAS().size(); i++)
		{
			Tabla tabla = config.getTABLAS().get(i);
			tabla.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.TABLAS, tabla);			
		}
		
		for(int i = 0; i < config.getBINARIAS().size(); i++)
		{
			Binaria binaria = config.getBINARIAS().get(i);
			binaria.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.BINARIAS, binaria);			
		}
		
		for(int i = 0; i < config.getCAMPOS_TIPO().size(); i++)
		{
			CamposTipo camposTipo = config.getCAMPOS_TIPO().get(i);
			camposTipo.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.CAMPOSTIPO, camposTipo);			
		}
		
		for(int i = 0; i < config.getDATOS_PARAMETRIZADOS().size(); i++)
		{
			Parametrizada parametrizada = config.getDATOS_PARAMETRIZADOS().get(i);
			parametrizada.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.PARAMETRIZADAS, parametrizada);			
		}
		
		for(int i = 0; i < config.getDATOS_SIMPLIFICADOS().size(); i++)
		{
			Simplificada simplificada = config.getDATOS_SIMPLIFICADOS().get(i);
			simplificada.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.SIMPLIFICADAS, simplificada);			
		}
		
		for(int i = 0; i < config.getTRADUCCIONES().size(); i++)
		{
			Traducida traducida = config.getTRADUCCIONES().get(i);
			traducida.setID_CONFIG(id);
			
			conn.insertarDato(Constantes.TRADUCCIONES, traducida);			
		}
    }
    
    // Método simulado que devuelve una lista de configuraciones
    private Configuracion obtenerConfiguraciones() {
        Configuracion aux = new Configuracion();
        aux.setId(id);
        aux.setDES_CONFIGURACION(descripcion);
        
        

        ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	
    	ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] paramID = new String[2];
    	paramID[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	paramID[1] = String.valueOf(id);
    	parametros.add(paramID);
    	aux.setCONFIG_COLORES(conn.recuperarDatos(DBQuery.SQL_CONFIG_COLORES, parametros, ConfigColores.class));
        aux.setTABLAS(conn.recuperarDatos(DBQuery.SQL_TABLAS, parametros, Tabla.class));
        
        String tablas = "";
        for(int i = 0; i < aux.getTABLAS().size(); i++)
        {
        	if(tablas.length() > 0)
        	{
        		tablas += ";";
        	}
        	
        	if(!tablas.contains(aux.getTABLAS().get(i).getNOMBRE_TABLA()))
        	{
            	tablas += aux.getTABLAS().get(i).getNOMBRE_TABLA();
            	           	
            	ArrayList<String[]> param = new ArrayList<String[]>();
            	
            	// parametro 1
        		String[] auxParam = new String[2];
        		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
        		auxParam[1] = String.valueOf(id);
        		param.add(auxParam);
        		
        		// parametro 2
        		String[] auxParam2 = new String[2];
        		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
        		auxParam2[1] = String.valueOf(aux.getTABLAS().get(i).getNOMBRE_TABLA());
        		param.add(auxParam2);
        		
        		if(aux.getCAMPOS_TIPO() == null)
        			aux.setCAMPOS_TIPO(new ArrayList<CamposTipo>());
        		aux.getCAMPOS_TIPO().addAll(conn.recuperarDatos(DBQuery.SQL_CAMPOS_TIPO, param, CamposTipo.class));
        	}
        	
        	ArrayList<String[]> param = new ArrayList<String[]>();
        	
        	// parametro 1
    		String[] auxParam = new String[2];
    		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    		auxParam[1] = String.valueOf(id);
    		param.add(auxParam);
    		
    		// parametro 2
    		String[] auxParam2 = new String[2];
    		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    		auxParam2[1] = String.valueOf(aux.getTABLAS().get(i).getNOMBRE_TABLA());
    		param.add(auxParam2);
    		
    		// parametro 3
    		String[] auxParam3 = new String[2];
    		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    		auxParam3[1] = String.valueOf(aux.getTABLAS().get(i).getPESTAGNA());
    		param.add(auxParam3);
    		
    		if(aux.getBINARIAS() == null)
    			aux.setBINARIAS(new ArrayList<Binaria>());
    		aux.getBINARIAS().addAll(conn.recuperarDatos(DBQuery.SQL_BINARIAS, param, Binaria.class));

    		if(aux.getDATOS_SIMPLIFICADOS() == null)
    			aux.setDATOS_SIMPLIFICADOS(new ArrayList<Simplificada>());
    		aux.getDATOS_SIMPLIFICADOS().addAll(conn.recuperarDatos(DBQuery.SQL_SIMPLIFICADAS, param, Simplificada.class));

    		if(aux.getTRADUCCIONES() == null)
    			aux.setTRADUCCIONES(new ArrayList<Traducida>());
    		aux.getTRADUCCIONES().addAll(conn.recuperarDatos(DBQuery.SQL_TRADUCIDAS, param, Traducida.class));

    		if(aux.getACCIONES() == null)
    			aux.setACCIONES(new ArrayList<Accion>());
    		aux.getACCIONES().addAll(conn.recuperarDatos(DBQuery.SQL_ACCIONES, param, Accion.class));

    		if(aux.getDATOS_PARAMETRIZADOS() == null)
    			aux.setDATOS_PARAMETRIZADOS(new ArrayList<Parametrizada>());
    		aux.getDATOS_PARAMETRIZADOS().addAll(conn.recuperarDatos(DBQuery.SQL_PARAMETRIZADAS, param, Parametrizada.class));
        }
    	conn.desconectar();

        return aux;
    }
}
