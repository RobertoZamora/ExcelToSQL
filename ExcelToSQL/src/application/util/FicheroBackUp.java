package application.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Level;

import application.Main;
import application.trazas.Trazas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FicheroBackUp {
    private Trazas log;
    
    public FicheroBackUp(Trazas log) {
    	this.log = log;
    }
    
    public File seleccionarCarpeta(Stage stage, String nombre) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        File file = null;

        if (selectedDirectory != null) {
        	LocalDateTime ahora = LocalDateTime.now();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        	String fechaFormateada = ahora.format(formatter);
        	
            // Se seleccionó una carpeta, ahora generamos un archivo de texto plano dentro de ella
            file = new File(selectedDirectory, "BCK_" + nombre + "_" + fechaFormateada);

            if (file.exists()) {
                boolean overwrite = Util.showConfirmationDialog("El fichero ya existe. Este se sobreescribirá. ¿Está seguro?");
                if (!overwrite) {
                    return null;
                }
            }
        }
        return file;
    }
    
    public File seleccionarArchivoBackUp(Stage stage) {
        File selectedFile = null;
        try {
            // Crear un FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo de respaldo (BCK_)");

            // Añadir filtro para archivos que empiecen con BCK_
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de respaldo (BCK_*)", "BCK_*"));

            // Abrir el diálogo para seleccionar un archivo
            selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                // Comprobar que el archivo seleccionado empieza con "BCK_"
                if (!selectedFile.getName().startsWith("BCK_")) {
                	mostrarInfo(stage, "El archivo seleccionado no es un archivo de respaldo válido.");
                    return null; // Archivo no válido
                }
            } else {
            	mostrarInfo(stage, "No se seleccionó ningún archivo.");
            }

        } catch (Exception e) {
            mostrarAlertaError(stage, e);
        }

        return selectedFile; // Archivo válido
    }
    
    private void mostrarAlertaError(Stage stage, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());

        // Agregar el icono personalizado
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));

        alert.showAndWait();
        e.printStackTrace();

        // Registrar el error en el log
        log.log(Level.ERROR, e.getMessage());
    }
    
    private void mostrarInfo(Stage stage, String info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Aviso");
        alert.setContentText(info);

        // Agregar el icono personalizado
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));

        alert.showAndWait();

        // Registrar el error en el log
        log.log(Level.INFO, info);
    }
}
