package application;

import java.io.File;

import org.apache.log4j.Level;

import application.BBDD.ConexionBBDD;
import application.controller.PrincipalController;
import application.trazas.Trazas;
import application.util.Util;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {
	public static String rutaDatos = "";
	private Trazas log;
	
    @Override
    public void start(Stage primaryStage) {
    	Util.generarCarpetaDatos();
    	log = new Trazas();
    	log.log(Level.DEBUG, "ruta de datos --> " + rutaDatos);
    	
    	File BBDD = new File(Main.rutaDatos + File.separator + "configuraciones/SQL");
		log.log(Level.DEBUG, "RUTA BBDD: " + BBDD.getAbsolutePath());
		if(!BBDD.exists())
		{
			// Crear una nueva ventana para mostrar la barra de progreso
	        Stage progressStage = new Stage();
	        progressStage.initModality(Modality.APPLICATION_MODAL);
	        progressStage.initStyle(StageStyle.UNDECORATED);

	        ProgressBar progressBar = new ProgressBar();
	        progressBar.setProgress(0);
	        progressBar.setPrefWidth(200); // Personaliza el tamaño
	        
	        Text texto = new Text("Iniciando...");

	        VBox vbox = new VBox();
	        vbox.setAlignment(Pos.CENTER);
	        vbox.getChildren().addAll(new Text("Cargando..."), texto, progressBar);

	        Scene progressScene = new Scene(vbox, 300, 100);
	        progressStage.setScene(progressScene);
	        progressStage.show();	        

	        // Simular un proceso que tarda 10 segundos en completarse
	        Task<Void> task = new Task<Void>() {
	            @Override
	            protected Void call() throws Exception
	            {
	            	int lineas = 0;
	            	lineas += Util.contarLineasArchivoEnJar("recursos/DDL/CONFIGURACIONES.DDL");
		            
	            	ConexionBBDD db = new ConexionBBDD(log);
	            	db.cargaInicial(progressBar, texto, lineas);
	                return null;
	            }
	        };

	        task.setOnSucceeded(e -> {
	            progressStage.close(); // Cierra la ventana de progreso al completar la tarea
	            showMainStage(primaryStage); // Muestra la ventana principal
	        });

	        new Thread(task).start(); // Inicia la tarea en un hilo separado
		}
		else
		{
			showMainStage(primaryStage); // Muestra la ventana principal
		}
    }

    private void showMainStage(Stage primaryStage) {
    	try {
	    	FXMLLoader loader = new FXMLLoader(Main.class.getResource("vistas/Principal.fxml"));
			
			loader.setControllerFactory(controllerClass -> {
                if (controllerClass == PrincipalController.class) {
                	PrincipalController seleccion = new PrincipalController();
                    seleccion.setDato(log);
                    return seleccion;
                }
                return null;
            });
			
			Parent root = loader.load();
			
			primaryStage.setTitle("Excel To SQL");

			Image icon = new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png"));
			primaryStage.getIcons().add(icon);

			primaryStage.setScene(new Scene(root));

			// Configura el evento para establecer el tamaño mínimo
			primaryStage.setOnShown(new javafx.event.EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					primaryStage.setMinWidth(primaryStage.getWidth());
					primaryStage.setMinHeight(primaryStage.getHeight());
				}
			});
			
			primaryStage.setOnCloseRequest(event -> {
				System.exit(0);
			});

		    // Evita que se pueda cambiar el tamaño de la ventana
		    primaryStage.setResizable(false);

			primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	        //log.log(Level.ERROR, e);
	    }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
