package application.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import application.Main;
import application.constantes.Constantes;

public class Util {


	public static void colores(ArrayList<String[]> colores, ArrayList<Color> coloresColor) {
		ArrayList<String[]> tempColores = Constantes.getColores();

	    colores.clear(); // Limpiar la lista para asegurarnos de que esté vacía
	    colores.addAll(tempColores); // Añadir todos los elementos de la lista tempora

	    for (int i = 0; i < colores.size(); i++) {
	        String colorHEXA = colores.get(i)[1];

	        int alfa = Integer.parseInt(colorHEXA.substring(0, 2), 16);
	        int rojo = Integer.parseInt(colorHEXA.substring(2, 4), 16);
	        int verde = Integer.parseInt(colorHEXA.substring(4, 6), 16);
	        int azul = Integer.parseInt(colorHEXA.substring(6, 8), 16);
	        coloresColor.add(Color.rgb(rojo, verde, azul, alfa / 255.0));
	    }
	}
	
	public static void generarCarpetaDatos()
	{
        
        // Nombre de la carpeta que deseas crear
        String folderName = ".ExcelToSQL";
        
        // Ruta completa a la carpeta en diferentes sistemas operativos
        String folderPath = "";
        
        // Verifica el sistema operativo y configura la ruta adecuada
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            // Windows (AppData)
            String appData = System.getenv("APPDATA");
            folderPath = appData + File.separator + folderName;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
            // Linux / Unix / macOS (Directorio del usuario)
    		String userHome = System.getProperty("user.home");
            folderPath = userHome + File.separator + folderName;
        } else {
            return;
        }
        
        // Crear la carpeta
        File folder = new File(folderPath);
        if(!folder.exists())
        	folder.mkdir();
                
        Main.rutaDatos = folderPath;
	}
    
    public static int contarLineasArchivoEnJar(String rutaArchivo) {
        int contador = 0;

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(rutaArchivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream != null) {
                while ((br.readLine()) != null) {
                    contador++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contador;
    }
    
    /**
     * Verifica si una cadena es numérica.
     * @param str la cadena a verificar
     * @return true si la cadena es numérica, false en caso contrario
     */
    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Regex para números enteros y decimales (positivos y negativos)
        String regex = "-?\\d+(\\.\\d+)?";
        return str.matches(regex);
    }
    
    public static boolean showConfirmationDialog(String message) {
        // Implement a confirmation dialog using JFoenix or JavaFX and return the user's choice
        // For simplicity, returning true here
        return true;
    }
}
