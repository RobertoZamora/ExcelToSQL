package application.constantes;

import java.util.ArrayList;
import java.util.Arrays;

public class Constantes {
	
	public final static int ACCION_SELECCIONAR = 0;
	public final static int ACCION_IGNORAR = 1;
	public final static int ACCION_BLOQUEO = 2;
	
	public final static int SQL_STRING = 0;
	public final static int SQL_DOUBLE = 1;
	public final static int SQL_DATE = 2;
	public final static int SQL_INT = 3;
	public final static int SQL_LONG = 4;
	public final static int SQL_BOOLEANN = 5;
	
	private static final ArrayList<String[]> COLORES = new ArrayList<>(
        Arrays.asList(
            new String[][] {
                { "Amarillo",		"FFFFFF00", "FFFF:FFFF:0" },
                { "Azul",			"FF0070C0", "0:6666:CCCC" },
                { "Azul Claro",		"FF00B0F0", "0:CCCC:FFFF" },
                { "Azul Oscuro", 	"FF002060", "0:3333:6666" },
                { "Blanco",			"FFFFFFFF", "FFFF:FFFF:FFFF" },
                { "Naranja",		"FFFFC000", "FFFF:CCCC:0" },
                { "Purpura",		"FF7030A0", "8080:0:8080" },
                { "Rojo",			"FFFF0000", "FFFF:0:0" },
                { "Rojo Oscuro",	"FFC00000", "9999:3333:0" },
                { "Verde",			"FF92D050", "9999:CCCC:0" },
                { "Verde Oscuro",	"FF00B050", "0:8080:0" },
            }
        )
    );
	
	private static final ArrayList<String> ACCIONES = new ArrayList<>(
			Arrays.asList(
		            new String[] {
		                "MODIFICAR", "IGNORAR", "BLOQUEO"
		            }
		        )	
	);
	
	private static final ArrayList<String> TIPOS_SQL = new ArrayList<>(
			Arrays.asList(
		            new String[] {
		                "string", "double", "date", "int", "long", "boolean"
		            }
		        )	
	);
	
	public static String TABLA_CONFIGURACION = "CONFIGURACIONES";
	public static String CONFIG_COLORES = "CONFIG_COLORES";
	public static String CONFIG_SIMPLIFICADOS = "DATOS_SIMPLIFICADOS";
	public static String TABLAS = "TABLAS";
	public static String BINARIAS = "BINARIAS";
	public static String SIMPLIFICADAS = "DATOS_SIMPLIFICADOS";
	public static String PARAMETRIZADAS = "DATOS_PARAMETRIZADOS";
	public static String TRADUCCIONES = "TRADUCCIONES";
	public static String T_ACCIONES = "ACCIONES";
	public static String CAMPOSTIPO = "CAMPOS_TIPO";
	
	public static ArrayList<String[]> getColores()
	{
		return COLORES;
	}
	
	public static String getAcciones(int indice)
	{
		return ACCIONES.get(indice);
	}
	
	public static String getSqlTipo(int indice)
	{
		return TIPOS_SQL.get(indice);
	}

}
