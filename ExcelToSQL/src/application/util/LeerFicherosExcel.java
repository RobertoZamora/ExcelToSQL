package application.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import application.Main;
import application.BBDD.ConexionBBDD;
import application.BBDD.DBQuery;
import application.constantes.Constantes;
import application.modelo.Accion;
import application.modelo.Binaria;
import application.modelo.CamposTipo;
import application.modelo.ConfigColores;
import application.modelo.Parametrizada;
import application.modelo.Simplificada;
import application.modelo.Tabla;
import application.modelo.Traducida;
import application.trazas.Trazas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LeerFicherosExcel {
	private Stage stage; 
	private int ID_CONFIG = 0;
	private Trazas log;
	
	private String MODIFICAR_HEXA = "";
	private String BLOQUEO_HEXA = "";
	private String IGNORAR_HEXA = "";
	private String MODIFICAR_HSSF = "";
	private String BLOQUEO_HSSF = "";
	private String IGNORAR_HSSF = "";
	
	public LeerFicherosExcel(Stage stage, int ID_CONFIG, Trazas log) {
		this.stage = stage;
		this.ID_CONFIG = ID_CONFIG;
		this.log = log;
	}
	
	public boolean esAmarillo(Cell cell) 
	{
	    boolean amarillo = false;
	    
	    CellStyle cellStyle = cell.getCellStyle();
	    if (cellStyle != null) 
	    {
	        if (cellStyle instanceof XSSFCellStyle) 
	        {
	            XSSFColor xssfColor = ((XSSFCellStyle) cellStyle).getFillForegroundColorColor();
	            if (xssfColor != null && xssfColor.getARGBHex() != null) 
	            {
	                if (xssfColor.getARGBHex().equals(MODIFICAR_HEXA)) 
	                {
	                    amarillo = true;
	                }
	            }
	        } 
	        else if (cellStyle instanceof HSSFCellStyle) 
	        {
	            HSSFColor hssfColor = ((HSSFCellStyle) cellStyle).getFillForegroundColorColor();
	            if (hssfColor != null) 
	            {
	                String hexString = hssfColor.getHexString();
	                if (hexString != null && hexString.equals(MODIFICAR_HSSF)) 
	                {
	                    amarillo = true;
	                }
	            }
	        }
	    }
	    return amarillo;
	}
	
	@SuppressWarnings("deprecation")
	public boolean esBloqueado(Cell cell) 
	{
		boolean bloqueado = false;
		
		CellStyle cellStyle = cell.getCellStyle();
		Color color = cellStyle.getFillForegroundColorColor();
		if (color != null) 
		{
			if (color instanceof XSSFColor) 
			{
				if(((XSSFColor) color).getARGBHex().equals(BLOQUEO_HEXA)) 
				{
					bloqueado = true;
				}
			} 
			else if (color instanceof HSSFColor) 
			{
				if (!(color instanceof HSSFColor.AUTOMATIC))
					if(((HSSFColor) color).getHexString().equals(BLOQUEO_HSSF)) 
					{
						bloqueado = true;
					}
			}
		}
		return bloqueado;
	}
	
	@SuppressWarnings("deprecation")
	public boolean esClienteTarifario (Cell cell) 
	{
		boolean verde = false;
		
		CellStyle cellStyle = cell.getCellStyle();
		Color color = cellStyle.getFillForegroundColorColor();
		if (color != null) 
		{
			if (color instanceof XSSFColor) 
			{
				if(((XSSFColor) color).getARGBHex().equals(IGNORAR_HEXA)) 
				{
					verde = true;
				}
			} 
			else if (color instanceof HSSFColor) 
			{
				if (!(color instanceof HSSFColor.AUTOMATIC))
					if(((HSSFColor) color).getHexString().equals(IGNORAR_HSSF)) 
					{
						verde = true;
					}
			}
		}
		return verde;
	}
	
	public int numeroPaginas() {
		ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] aux = new String[2];
    	aux[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	aux[1] = String.valueOf(ID_CONFIG);
    	parametros.add(aux);
    	
    	int numPaginas = conn.recuperarDatos(DBQuery.SQL_TABLAS, parametros, Tabla.class).size();
    	conn.desconectar();
		
    	return numPaginas;
	}
	
	public Tabla datosTabla(String pestagna) {

		ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
		
		
		ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] auxPestagnas = new String[2];
    	auxPestagnas[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	auxPestagnas[1] = String.valueOf(ID_CONFIG);
    	parametros.add(auxPestagnas);
    	
    	String[] auxPestagnas3 = new String[2];
    	auxPestagnas3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    	auxPestagnas3[1] = pestagna;
    	parametros.add(auxPestagnas3);
    	
        Tabla pestagnas = conn.recuperarDato(DBQuery.SQL_TABLAS_PESTAGNA, parametros, Tabla.class);

    	conn.desconectar();
    	
		return pestagnas;
	}
	
	public void cargarColores()
	{
		ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] aux = new String[2];
    	aux[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	aux[1] = String.valueOf(ID_CONFIG);
    	parametros.add(aux);
        ArrayList<ConfigColores> configColores = conn.recuperarDatos(DBQuery.SQL_CONFIG_COLORES, parametros, ConfigColores.class);
    	
		for(ConfigColores color : configColores)
        {
        	if(Constantes.getAcciones(Constantes.ACCION_SELECCIONAR).equals(color.getACCION()))
        	{
        		MODIFICAR_HEXA = color.getCOLOR_HEXA();
        		MODIFICAR_HSSF = color.getCOLOR_HSSF();
        	}
        	else if(Constantes.getAcciones(Constantes.ACCION_IGNORAR).equals(color.getACCION()))
        	{
        		IGNORAR_HEXA = color.getCOLOR_HEXA();
        		IGNORAR_HSSF = color.getCOLOR_HSSF();
        	}
        	else if(Constantes.getAcciones(Constantes.ACCION_BLOQUEO).equals(color.getACCION()))
        	{
        		BLOQUEO_HEXA = color.getCOLOR_HEXA();
        		BLOQUEO_HSSF = color.getCOLOR_HSSF();
        	}
        }
	}
	
	public String tipoCampo(String tabla, String campo) throws Exception {
		ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] aux = new String[2];
    	aux[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	aux[1] = String.valueOf(ID_CONFIG);
    	parametros.add(aux);
    	
    	String[] auxTabla = new String[2];
    	auxTabla[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    	auxTabla[1] = tabla;
    	parametros.add(auxTabla);
    	
    	String[] auxCampo = new String[2];
    	auxCampo[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    	auxCampo[1] = campo;
    	parametros.add(auxCampo);

    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	CamposTipo configCamposTipo = conn.recuperarDato(DBQuery.SQL_CAMPO_TIPO, parametros, CamposTipo.class);
        conn.desconectar();
        
        
        if(configCamposTipo == null)
        	throw new Exception("El campo " + campo + ", no se ha sido configurado");
		
        System.out.println(tabla + "-" + campo);
		return configCamposTipo.getTIPO();
	}
	
	public ArrayList<CamposTipo> todosCampos(String tabla) {
		
		ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] aux = new String[2];
    	aux[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	aux[1] = String.valueOf(ID_CONFIG);
    	parametros.add(aux);
    	
    	String[] auxTabla = new String[2];
    	auxTabla[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    	auxTabla[1] = tabla;
    	parametros.add(auxTabla);

    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	ArrayList<CamposTipo> configCamposTipo = conn.recuperarDatos(DBQuery.SQL_CAMPOS_TIPO, parametros, CamposTipo.class);
        conn.desconectar();
		
		return configCamposTipo;
	}
	
	public boolean accionPermitida(String tabla, String pestagna, String accion) {
		
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 3
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = accion;
		param.add(auxParam4);
		
		boolean permiteAccion = conn.recuperarDato(DBQuery.SQL_ACCION, param, Accion.class) != null;
		
		conn.desconectar();
		
		return permiteAccion;
	}
	
	public boolean esBinario(String tabla, String pestagna, String campo) {
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 4
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = campo;
		param.add(auxParam4);
		
		boolean esBinaria = conn.recuperarDato(DBQuery.SQL_BINARIA, param, Binaria.class) != null;
		
		
		return esBinaria;
	}
	
	public boolean esSimplificado (String tabla, String pestagna, String campo)
	{		
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 4
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = campo;
		param.add(auxParam4);

		boolean esSimplificada = conn.recuperarDato(DBQuery.SQL_SIMPLIFICADA, param, Simplificada.class) != null;
        
		conn.desconectar();
		
		return esSimplificada;
	}
	
	public String[] camposSimplificado (String tabla, String pestagna, String campo)
	{
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 4
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = campo;
		param.add(auxParam4);

		ArrayList<Simplificada> auxSimplificadas = conn.recuperarDatos(DBQuery.SQL_SIMPLIFICADA, param, Simplificada.class);
        
		conn.desconectar();
		
		String[] simplificadas = new String[auxSimplificadas.size() + 1];
		for(int i = 0; i < auxSimplificadas.size(); i++)
		{
			if(i == 0)
				simplificadas[0] = "-";
			simplificadas[i + 1] = auxSimplificadas.get(i).getCOLUMNA();
		}
		
		return simplificadas;
	}
	
	public boolean esParametrizado(String tabla, String pestagna, String campo) {
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 4
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = campo;
		param.add(auxParam4);

		boolean esParametrizada = conn.recuperarDato(DBQuery.SQL_PARAMETRIZADA, param, Parametrizada.class) != null;
        
		conn.desconectar();
		
		return esParametrizada;
	}
	
	public String campoParametrizado(String tabla, String pestagna, String campo, String dato) {
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 4
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = campo;
		param.add(auxParam4);
		
		// parametro 5
		String[] auxParam5 = new String[2];
		auxParam5[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam5[1] = dato;
		param.add(auxParam5);

		Parametrizada parametrizada = conn.recuperarDato(DBQuery.SQL_PARAMETRIZADA_DATO, param, Parametrizada.class);
        
		conn.desconectar();
		
		return parametrizada.getTRADUCCION();
	}
	
	public String campoTraducido(String tabla, String pestagna, String campo) {
		ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(ID_CONFIG);
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = tabla;
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = pestagna;
		param.add(auxParam3);
		
		// parametro 4
		String[] auxParam4 = new String[2];
		auxParam4[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam4[1] = campo;
		param.add(auxParam4);

		Traducida traducida = conn.recuperarDato(DBQuery.SQL_TRADUCIDA, param, Traducida.class);
        
		conn.desconectar();
		
		return traducida != null? traducida.getCOLUMNA_BBDD():campo;
	}
	
	public String obtenerExtension(File selectedFile) {
		return selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
	}
	
	public boolean extensionValida (String extension) {
		return (extension.equals("xls") || extension.equals("xlsx"));
	}
	
	public String parsearNumero (String numeroEntero, String tipo) {
		String numeroParseado = "";
		try {
			
			int numeroInt;
			if(tipo.equals("int")) 
			{
				numeroInt = (int)Double.parseDouble(numeroEntero);
				numeroParseado = String.valueOf(numeroInt);
			}
			else if(tipo.equals("string")  || tipo.equals("char"))
			{
				try {
					numeroInt = (int)Double.parseDouble(numeroEntero);
					if(numeroEntero.indexOf(".") > -1)
					{
						numeroEntero = numeroEntero.substring(0, numeroEntero.indexOf("."));
					}
					numeroParseado = String.valueOf(numeroInt);
					int longitudParseado = numeroParseado.length();
					int longitudOriginal = numeroEntero.length();
					for(int i = 0; i < (longitudOriginal - longitudParseado); i++)
						numeroParseado = "0" + numeroParseado;
				} catch (NumberFormatException e) {
					numeroParseado = numeroEntero;
				}
			}
			else if (tipo.equals("double"))
			{
				double numeroDouble= Double.parseDouble(numeroEntero);
				numeroParseado = String.valueOf(numeroDouble);
			}
		} catch (NumberFormatException e) {
			if(tipo.equals("string"))
				numeroParseado = numeroEntero;
		}
		return numeroParseado;
	}
	
	public String datoATipo (String dato, String tipo) {
		if(tipo.equals("string") || tipo.equals("char"))
		{
			if(!dato.equals("NULL"))
			{
				dato = "'" + dato + "'";
			}
		}
		return dato;
	}
	
	public void generarQuerys(File selectedFile) throws Exception {
		String extension = (selectedFile != null)?obtenerExtension(selectedFile):"";
		
		if(selectedFile != null && extensionValida(extension)) 
		{
			FicheroSQL escritor = new FicheroSQL(stage);
			Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(selectedFile);
			} catch(FileNotFoundException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText(null);
	            alert.setTitle("Error");
	            alert.setContentText("Fichero inaccesible o en uso, Si lo tiene abierto por favor cierrelo y vuelva a intentarlo.");
	            // Agregar el icono personalizado
	            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
	            alert.showAndWait();
	            log.log(Level.ERROR, "Fichero inaccesible o en uso, Si lo tiene abierto por favor cierrelo y vuelva a intentarlo.");
				return;
			}
			cargarColores();
			
			ArrayList<String> campos = new ArrayList<String>();
			for(Sheet sheet : workbook)
			{
				String nombrePestagna = sheet.getSheetName();
				Tabla datosTabla = datosTabla(nombrePestagna);
				
				if(datosTabla == null)
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setHeaderText(null);
		            alert.setTitle("Error");
		            alert.setContentText("La pesetaña " + nombrePestagna + " no esta configurada. Por lo que no sera tratada.");
		            // Agregar el icono personalizado
		            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
		            alert.showAndWait();
		            continue;
				}
				escritor.escribirTraza("--" + sheet.getSheetName());

				Integer FilaBloqueos = datosTabla.getLINEA_BLOQUEOS();

				campos = new ArrayList<String>();
				ArrayList<Boolean> auxBlock = new ArrayList<Boolean>();
				
				int cantCampos = 0;
				
				for (Row row : sheet) 
				{
					boolean modificar = false;
					boolean insertar = true;
					boolean borrar = true;
					ArrayList<String> datos = new ArrayList<String>();
					ArrayList<Boolean> datosAModificar = new ArrayList<Boolean>();
					if((row.getRowNum() >= datosTabla.getLINEA_DATOS()) || (FilaBloqueos != null && row.getRowNum() == FilaBloqueos) || (row.getRowNum() == datosTabla.getLINEA_COLUMNAS())) {
						for (Cell cell : row) {
							if(cell.getColumnIndex() >= datosTabla.getCOLUMNA_DATOS())
							{
								if(row.getRowNum() == datosTabla.getLINEA_COLUMNAS()) 
								{
									campos.add(String.valueOf(cell).trim().toUpperCase());
									cantCampos++;
								}
								else if(FilaBloqueos != null && row.getRowNum() == FilaBloqueos)
								{
									auxBlock.add(String.valueOf(cell).equals("BLOCK"));
								}
								else if(row.getRowNum() >= datosTabla.getLINEA_DATOS())
								{
									if(cell.getColumnIndex() <= cantCampos) {
										if(esAmarillo(cell))
										{
											modificar = true;
											datosAModificar.add(true);
											
											Font cellFont = cell.getSheet().getWorkbook().getFontAt(cell.getCellStyle().getFontIndex());
											if(!cellFont.getStrikeout())
											{
												borrar = false;
											}
										}
										else if((!auxBlock.isEmpty() && auxBlock.get(cell.getColumnIndex() - 1) 
												|| esBloqueado(cell) || esClienteTarifario(cell) || cell.toString().length() == 0))
										{
											datosAModificar.add(false);
										}
										else
										{
											insertar = false;
											datosAModificar.add(false);							
										}
										
										if (!"".equals(String.valueOf(cell)))
										{
											datos.add(String.valueOf(cell).trim());
										}
										else
										{
											datos.add("");
										}
									}
								}
							}
						}
					}
					String[] primaryKeyArray = datosTabla.getPK_TABLA().split("#");
					ArrayList<String> primaryKey = new ArrayList<>(Arrays.asList(primaryKeyArray));

					if(accionPermitida(datosTabla.getNOMBRE_TABLA(), nombrePestagna, "INSERT") && modificar && insertar && !borrar) 
					{
						escritor.escribirTraza(generarInserts(datosTabla.getNOMBRE_TABLA(), nombrePestagna, datos, campos));
					}
					else if(accionPermitida(datosTabla.getNOMBRE_TABLA(), nombrePestagna, "UPDATE") && modificar && !insertar && !borrar) 
					{
						escritor.escribirTraza(generarUpdates(datosTabla.getNOMBRE_TABLA(), nombrePestagna, primaryKey, datos, campos, datosAModificar));
					}
					else if(accionPermitida(datosTabla.getNOMBRE_TABLA(), nombrePestagna, "DELETE") && modificar && insertar && borrar)
					{
						escritor.escribirTraza(generarDeletes(datosTabla.getNOMBRE_TABLA(), nombrePestagna, primaryKey, datos));
					}
				}
			}
			workbook.close();
			escritor.liberar();
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("Fichero sql generado.");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
		} 
		else if ((selectedFile != null && !selectedFile.getName().equals("")) && !extensionValida(extension)) 
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("La extensión del fichero no es valida(xls o xlsx)");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
            log.log(Level.ERROR, "La extensión del fichero no es valida(xls o xlsx)");
		}
		else if(selectedFile == null || selectedFile.getName().equals(""))
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Antes debe seleccionar un fichero Excel.");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
            log.log(Level.ERROR, "Antes debe seleccionar un fichero Excel.");
		}
	}
	
	public String generarInserts(String tabla, String nombrePestagna, ArrayList<String> datos, ArrayList<String> campos) throws Exception {

		String sCampos = "";
		String sValues = "";
		
		for(int i = 0; i < campos.size(); i++) 
		{
			if(sCampos.indexOf(campos.get(i)) == -1)
			{
				String tipo = tipoCampo(tabla, campos.get(i));
				if(tipo != null && (tipo.equals("int") || tipo.equals("double") || tipo.equals("char") || tipo.equals("string"))) 
				{
					if(i != 0)
					{
						sCampos += ", ";
						sValues += ", ";
					}
					if(esParametrizado(tabla, nombrePestagna, campos.get(i).trim()))
					{
						String campo = campoTraducido(tabla, nombrePestagna, datos.get(i).trim());
						String valor = campoParametrizado(tabla, nombrePestagna, campos.get(i).trim(), campo);

						sCampos += campoTraducido(tabla, nombrePestagna, campos.get(i));
						sValues += datoATipo(valor, tipo);
					}
					else if(esSimplificado(tabla, nombrePestagna, campos.get(i)))
					{
						String[] configuracionSimplificado = camposSimplificado(tabla, nombrePestagna, campos.get(i));
						String[] campoMultiple = new String[configuracionSimplificado.length -1];
						if(datos.get(i).indexOf(configuracionSimplificado[0]) == -1) 
						{
							for(int k = 0; k < campoMultiple.length; k++)
							{
								campoMultiple[k] = ((k == 0)?"" + parsearNumero(datos.get(i), tipo):"NULL"); 
							}
						} 
						else 
						{
							String[] auxValores = datos.get(i).split(configuracionSimplificado[0]);
							for(int k = 0; k < campoMultiple.length; k++)
							{
								campoMultiple[k] = "" + parsearNumero(auxValores[k], tipo); 
							}
						}
						for(int k = 0; k < campoMultiple.length; k++)
						{
							if(k != 0) 
							{
								sCampos += ", ";
								sValues += ", ";
							}
							sCampos += campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]);
							sValues += datoATipo(campoMultiple[k], tipo);
						}
					}
					else if(esBinario(tabla, nombrePestagna, campos.get(i))) 
					{
						sCampos += campoTraducido(tabla, nombrePestagna, campos.get(i));
						sValues += "" + (datos.get(i).equals("SI")?datoATipo("1", tipo):datoATipo("0", tipo));
					} 
					else
					{
						String valor = "NULL";
						if(datos.get(i) != null && !datos.get(i).equals("")) 
						{
							valor = parsearNumero(datos.get(i),tipo);
						}
						sCampos += campoTraducido(tabla, nombrePestagna, campos.get(i));
						sValues += datoATipo(valor, tipo);
					}
				} 
				else 
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setHeaderText(null);
		            alert.setTitle("Error");
		            alert.setContentText("El campo " + campos.get(i) + " no ha sido configurado por favor configurelo y pruebe de nuevo.");
		            // Agregar el icono personalizado
		            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
		            alert.showAndWait();
		            log.log(Level.ERROR, "El campo " + campos.get(i) + " no ha sido configurado por favor configurelo y pruebe de nuevo.");
					return "";
				}
			}
		}
		String insert = "INSERT INTO " + tabla + "(" + sCampos + ") VALUES (" + sValues + ");"; 
		return insert;
	
	}
	
	public String generarUpdates(String tabla, String nombrePestagna, ArrayList<String> primaryKey, ArrayList<String> datos, ArrayList<String> campos, ArrayList<Boolean> aModificar) throws Exception {

		String update = "UPDATE " + tabla + " SET ";
		boolean primeraSet = true;
		boolean primeraWere = true;
		String where = "";
		
		for(int i = 0; i < campos.size(); i++) 
		{
			String auxUpdate = generarSetUpdate(tabla, nombrePestagna, campos.get(i), datos.get(i), primeraSet, aModificar.get(i));
			if(auxUpdate.length() > 0)
			{
				primeraSet = false;
				update += auxUpdate;
			}
			String auxWhere = generarWhere(tabla, nombrePestagna, campos.get(i), datos.get(i), primaryKey, primeraWere);
			if(auxWhere.length() > 0)
			{
				primeraWere = false;
				where += auxWhere;
			}
		}
		update += " WHERE " + where + ";";
		return update;
	}
	
	public String generarSetUpdate(String tabla, String nombrePestagna, String campo, String dato, boolean primeraSet, boolean aModificar) throws Exception {
		String update = "";
		String tipo = tipoCampo(tabla, campo);
		if(aModificar) 
		{
			if(tipo != null && (tipo.equals("int") || tipo.equals("double") || tipo.equals("char") || tipo.equals("string"))) 
			{
				if(!primeraSet)
				{
					update += ", ";
				}
				if(esParametrizado(tabla, nombrePestagna, campo.trim()))
				{
					String campoAux = dato.trim();
					String auxValor = campoParametrizado(tabla, nombrePestagna, campo.trim(), campoAux);
					
					update += campoTraducido(tabla, nombrePestagna, campo) + " = " + datoATipo(auxValor, tipo);
					
				}
				else if(esSimplificado(tabla, nombrePestagna, campo))
				{
					String[] configuracionSimplificado = camposSimplificado(tabla, nombrePestagna, campo);
					if(dato.indexOf(configuracionSimplificado[0]) == -1) 
					{
						for(int k = 0; k < (configuracionSimplificado.length -1); k++)
						{
							update += ((k == 0)?campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = " + datoATipo(parsearNumero(dato, tipo),tipo): ", " + campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = NULL");
						}
					} 
					else 
					{
						String[] auxValores = dato.split(configuracionSimplificado[0]);
						for(int k = 0; k < (configuracionSimplificado.length -1); k++)
						{
							auxValores[k] = datoATipo(auxValores[k], tipo);
							update += ((k == 0)?campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = " + parsearNumero(auxValores[k],tipo): ", " + campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = " + parsearNumero(auxValores[k],tipo));
						}
					}
				}
				else if(esBinario(tabla, nombrePestagna, campo)) 
				{
					update += campoTraducido(tabla, nombrePestagna, campo) + " = " + (dato.equals("SI")?datoATipo("1", tipo):datoATipo("0", tipo));
				} 
				else
				{
					String valor = "NULL";
					if(dato != null && !dato.equals("")) 
					{
						valor = parsearNumero(dato,tipo);
					}
					update += campoTraducido(tabla, nombrePestagna, campo) + " = " + datoATipo(valor, tipo);
				}
			}
			else 
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText(null);
	            alert.setTitle("Error");
	            alert.setContentText("El campo " + campo + " no ha sido configurado por favor configurelo y pruebe de nuevo.");
	            // Agregar el icono personalizado
	            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
	            alert.showAndWait();
	            log.log(Level.ERROR, "El campo " + campo + " no ha sido configurado por favor configurelo y pruebe de nuevo.");
				return "";
			}
		}
		
		return update;
	}
	
	public String generarWhere(String tabla, String nombrePestagna, String campo, String dato, ArrayList<String> primaryKey, Boolean primeraWere) throws Exception {
		String where = "";
		String tipo = tipoCampo(tabla, campo);
		if (primaryKey.indexOf(campo) > -1 && where.indexOf(campo) == -1)
		{
			String valor = "";
			String auxValor = "";
			if(!primeraWere)
			{
				where += " AND ";
			}
			
			if(esParametrizado(tabla, nombrePestagna, campo.trim()))
			{
				auxValor = campoParametrizado(tabla, nombrePestagna, campo.trim(), dato.trim()); 
				valor = parsearNumero(auxValor, tipo);
				
				where += campoTraducido(tabla, nombrePestagna, campo) + " = " + datoATipo(valor, tipo);
			}
			else if(esSimplificado(tabla, nombrePestagna, campo))
			{
				String[] configuracionSimplificado = camposSimplificado(tabla, nombrePestagna, campo);
				if(dato.indexOf(configuracionSimplificado[0]) == -1) 
				{
					for(int k = 0; k < (configuracionSimplificado.length -1); k++)
					{
						where += ((k == 0)?campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = " + datoATipo(parsearNumero(dato, tipo),tipo): ", " + campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = NULL");
					}
				} 
				else 
				{
					String[] auxValores = dato.split(configuracionSimplificado[1]);
					for(int k = 0; k < (configuracionSimplificado.length -1); k++)
					{
						auxValores[k] = parsearNumero(auxValores[k], tipo);
						where += ((k == 0)?campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = " + datoATipo(auxValores[k],tipo): ", " + campoTraducido(tabla, nombrePestagna, configuracionSimplificado[k + 1]) + " = " + datoATipo(auxValores[k],tipo));
					}
				}
			}
			else if(esBinario(tabla, nombrePestagna, campo)) 
			{
				where += campoTraducido(tabla, nombrePestagna, campo) + " = " + (dato.equals("SI")?datoATipo("1", tipo):datoATipo("0", tipo));
			} 
			else
			{
				where += campoTraducido(tabla, nombrePestagna, campo) + " = " + datoATipo(parsearNumero(dato, tipo), tipo);
			}
		}
		return where;
	}
	
	public String generarDeletes(String tabla, String nombrePestagna, ArrayList<String> primaryKey, ArrayList<String> datos) throws Exception {
		String delete = "DELETE FROM " + tabla + " WHERE " ;
		boolean primera = true;
		for(int i = 0; i < primaryKey.size(); i++)
		{
			String auxDelete = generarWhere(tabla, nombrePestagna, campoTraducido(tabla, nombrePestagna, primaryKey.get(i)), datos.get(i), primaryKey, primera);
			if(auxDelete.length() > 0)
			{
				primera = false;
				delete += auxDelete;	
			}
		}
		return delete + ";";
	}
}