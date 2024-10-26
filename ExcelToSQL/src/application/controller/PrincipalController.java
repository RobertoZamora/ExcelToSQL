package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.BBDD.ConexionBBDD;
import application.BBDD.DBQuery;
import application.constantes.Constantes;
import application.modelo.ConfigColores;
import application.modelo.Configuracion;
import application.trazas.Trazas;
import application.util.ConfiguracionJsonCreator;
import application.util.FicheroBackUp;
import application.util.LeerFicherosExcel;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PrincipalController
{
	private Trazas log;
	File selectedFile = null;

    @FXML
    private JFXComboBox<Configuracion> comboConfig;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton nuevaConfiguracion;

    @FXML
    private JFXButton borrarConfiguracion;

    @FXML
    private JFXTextField archivoExcel;

    @FXML
    private JFXButton excel;

    @FXML
    private JFXButton configurar;

    @FXML
    private JFXButton ejecutar;

    @FXML
    private JFXButton descargarConfiguracion;

    @FXML
    private JFXButton cargarConfiguracion;

    @FXML
    void abrirConfiguracion(MouseEvent event) {
        try {
        	int selectedIndex = comboConfig.getSelectionModel().getSelectedIndex();
    		if(selectedIndex > 0)
    		{
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Configuracion.fxml"));
	
	            loader.setControllerFactory(controllerClass -> {
	                if (controllerClass == ConfiguracionController.class) {
	                    ConfiguracionController seleccion = new ConfiguracionController();
	                    seleccion.setDato(log, selectedIndex);
	                    return seleccion;
	                }
	                return null;
	            });
	
	            Parent root = loader.load();
	
	            Stage seleccionable = new Stage();
	            seleccionable.setTitle("Configuración");
	
	            Image icon = new Image(Main.class.getResource("/recursos/img/SQL.png").toExternalForm());
	            seleccionable.getIcons().add(icon);
	
	            seleccionable.setScene(new Scene(root));
	
	            // Configura la ventana secundaria como modal
	            seleccionable.initModality(Modality.APPLICATION_MODAL);
	
	            // Configura el evento para establecer el tamaño mínimo
	            seleccionable.setOnShown(new javafx.event.EventHandler<WindowEvent>() {
	                @Override
	                public void handle(WindowEvent event) {
	                    seleccionable.setMinWidth(seleccionable.getWidth());
	                    seleccionable.setMinHeight(seleccionable.getHeight());
	                }
	            });
	
			    // Evita que se pueda cambiar el tamaño de la ventana
	            seleccionable.setResizable(false);
	
	            seleccionable.showAndWait(); // Muestra la ventana y espera hasta que se cierre
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Aviso");
                alert.setContentText("Debe seleccionar una configuracion valida");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
    		}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
            log.log(Level.ERROR, e);
        }
    }

    @FXML
    void borrarConfiguracion(MouseEvent event) {
    	try {
    		int selectedIndex = comboConfig.getSelectionModel().getSelectedIndex();
    		if(selectedIndex > 0)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfirmarController.class) {
                    	ConfirmarController seleccion = new ConfirmarController();
                        seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + comboConfig.getSelectionModel().getSelectedItem() + "' ¿Esta usted seguro?.");
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Buscar Cliente");

                // Verifica la ruta de la imagen
                URL iconURL = Main.class.getResource("/recursos/img/SQL.png");
                if (iconURL == null) {
                    throw new Exception("Icon resource not found");
                }
                Image icon = new Image(iconURL.toExternalForm());
                seleccionable.getIcons().add(icon);

                seleccionable.setScene(new Scene(root));

                // Configura la ventana secundaria como modal
                seleccionable.initModality(Modality.APPLICATION_MODAL);

                // Configura el evento para establecer el tamaño mínimo
                seleccionable.setOnShown(event1 -> {
                    seleccionable.setMinWidth(seleccionable.getWidth());
                    seleccionable.setMinHeight(seleccionable.getHeight());
                });

    		    // Evita que se pueda cambiar el tamaño de la ventana
                seleccionable.setResizable(false);

                seleccionable.showAndWait(); // Muestra la ventana y espera hasta que se cierre
                
                boolean aux = ((ConfirmarController)loader.getController()).getDato();
                
                if(aux)
                {
            		Configuracion confi = comboConfig.getItems().get(comboConfig.getSelectionModel().getSelectedIndex());
            		
            		borrarConfiguracion(confi);
            	    // Eliminar el elemento de la lista de items del ComboBox
            	    comboConfig.getItems().remove(confi);
                }
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Aviso");
                alert.setContentText("Debe seleccionar una configuracion valida");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
    		}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
            e.printStackTrace();
            log.log(Level.ERROR, e);
        }
    }

    @FXML
    void buscarExcel(MouseEvent event)
    {
    	int selectedIndex = comboConfig.getSelectionModel().getSelectedIndex();
		if(selectedIndex > 0)
		{
	    	// Crea un FileChooser
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Selecciona un archivo"); // Título de la ventana

	        FileChooser.ExtensionFilter extFilterImg = new FileChooser.ExtensionFilter("Archivos de Excel (*.xlsx, *.xls)", "*.png", "*.xlsx", "*.xls");
	        
	        // Agrega los filtros al FileChooser
	        fileChooser.getExtensionFilters().addAll(extFilterImg);

	        // Establece el directorio inicial en la carpeta home del usuario
	        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));        

	        // Muestra el diálogo de selección de archivos
	        File selectedFileAux = fileChooser.showOpenDialog(((Stage) excel.getScene().getWindow()));
	        
	        if(selectedFileAux != null)
	        {
	        	selectedFile = selectedFileAux;
	        	archivoExcel.setText(selectedFile.getName());
	        }
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("Debe seleccionar una configuracion valida");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
		}
    }

    @FXML
    void crearConfiguracion(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/PedirDato.fxml"));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == PedirDatoController.class) {
                    PedirDatoController seleccion = new PedirDatoController();
                    seleccion.setDato(log, "Introduzca el texto parametrizado.");
                    return seleccion;
                }
                return null;
            });

            Parent root = loader.load();

            Stage seleccionable = new Stage();
            seleccionable.setTitle("Nombre nueva configuración");

            // Verifica la ruta de la imagen
            URL iconURL = Main.class.getResource("/recursos/img/SQL.png");
            if (iconURL == null) {
                throw new Exception("Icon resource not found");
            }
            Image icon = new Image(iconURL.toExternalForm());
            seleccionable.getIcons().add(icon);

            seleccionable.setScene(new Scene(root));

            // Configura la ventana secundaria como modal
            seleccionable.initModality(Modality.APPLICATION_MODAL);

            // Configura el evento para establecer el tamaño mínimo
            seleccionable.setOnShown(event1 -> {
                seleccionable.setMinWidth(seleccionable.getWidth());
                seleccionable.setMinHeight(seleccionable.getHeight());
            });

		    // Evita que se pueda cambiar el tamaño de la ventana
            seleccionable.setResizable(false);

            seleccionable.showAndWait(); // Muestra la ventana y espera hasta que se cierre
            
            String aux = ((PedirDatoController)loader.getController()).getDato();
			
			if(aux != null && !aux.isEmpty())
			{
				ConexionBBDD conn = new ConexionBBDD(log);
				conn.conectar();
				int id = conn.contarRegistros(Constantes.TABLA_CONFIGURACION) + 1;
				Configuracion config = new Configuracion(id, aux);
				conn.insertarDato(Constantes.TABLA_CONFIGURACION, config);
				ConfigColores coloresMod = new ConfigColores(id, Constantes.getAcciones(Constantes.ACCION_SELECCIONAR), Constantes.getColores().get(0)[1], Constantes.getColores().get(0)[2]);
				conn.insertarDato(Constantes.CONFIG_COLORES, coloresMod);
				ConfigColores coloresIgn = new ConfigColores(id, Constantes.getAcciones(Constantes.ACCION_IGNORAR), Constantes.getColores().get(0)[1], Constantes.getColores().get(0)[2]);
				conn.insertarDato(Constantes.CONFIG_COLORES, coloresIgn);
				ConfigColores coloresBlo = new ConfigColores(id, Constantes.getAcciones(Constantes.ACCION_BLOQUEO), Constantes.getColores().get(0)[1], Constantes.getColores().get(0)[2]);
				conn.insertarDato(Constantes.CONFIG_COLORES, coloresBlo);
				conn.desconectar();
				comboConfig.getItems().add(config);
		    	comboConfig.setValue(config);
			}
            
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
            e.printStackTrace();
            log.log(Level.ERROR, e);
        }
    }

    public void borrarConfiguracion(Configuracion confi)
    {
    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
		String[] auxParam = new String[3];
		auxParam[0] = "ID";
		auxParam[1] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[2] = String.valueOf(confi.getID());
		param.add(auxParam);
		
		conn.eliminarDato(Constantes.TABLA_CONFIGURACION, param);
		
		param.get(0)[0] = "ID_CONFIG";
		
		conn.eliminarDato(Constantes.CONFIG_COLORES, param);
		conn.eliminarDato(Constantes.TABLAS, param);
		conn.eliminarDato(Constantes.BINARIAS, param);
		conn.eliminarDato(Constantes.SIMPLIFICADAS, param);
		conn.eliminarDato(Constantes.PARAMETRIZADAS, param);
		conn.eliminarDato(Constantes.TRADUCCIONES, param);
		conn.eliminarDato(Constantes.T_ACCIONES, param);
		conn.eliminarDato(Constantes.CAMPOSTIPO, param);
    	conn.desconectar(); 
    }

    @FXML
    void generarSql(MouseEvent event) {
    	int selectedIndex = comboConfig.getSelectionModel().getSelectedIndex();
    	
    	if(selectedFile == null)
    	{
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("Debe seleccionar un archivo de excel valido.");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
    	}
    	else if(selectedIndex == 0 )
		{
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("Debe seleccionar una configuracion valida");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
		}
		else
		{
			
			LeerFicherosExcel generadorQuerys = new LeerFicherosExcel((Stage) ejecutar.getScene().getWindow(), comboConfig.getItems().get(selectedIndex).getID(), log);
			try {
				generadorQuerys.generarQuerys(selectedFile);
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setHeaderText(null);
	            alert.setTitle("Error");
	            alert.setContentText(e.getMessage());
	            // Agregar el icono personalizado
	            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
	            alert.showAndWait();
	            e.printStackTrace();
			}

		}
    }
    
    public void setDato(Trazas log)
    {
    	this.log = log;
    }

    @FXML
    void initialize()
    {
    	// Crear un Tooltip
        Tooltip tooltipNuevaConfiguracion = new Tooltip("Crear Nueva Configuración.");
        Tooltip tooltipBorrarConfiguracion = new Tooltip("Borrar Configuración Actual.");
        Tooltip tooltipExcel = new Tooltip("Seleccionar Excel.");
        Tooltip tooltipConfigurar = new Tooltip("Configurar.");
        Tooltip tooltipEjecutar = new Tooltip("Generar Script SQL.");
        
    	nuevaConfiguracion.setTooltip(tooltipNuevaConfiguracion);
    	borrarConfiguracion.setTooltip(tooltipBorrarConfiguracion);
    	excel.setTooltip(tooltipExcel);
    	configurar.setTooltip(tooltipConfigurar);
    	ejecutar.setTooltip(tooltipEjecutar);
    	
    	cargarConfiguraciones();
    }
    
    private void cargarConfiguraciones()
    {
    	// Limpiar los items actuales del ComboBox
    	comboConfig.getItems().clear();
    	
    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	ArrayList<Configuracion> configuraciones = new ArrayList<Configuracion>();
    	configuraciones.add(new Configuracion(0,"Selecciona"));
    	configuraciones.addAll(conn.recuperarDatos(DBQuery.SQL_CONFIGURACIONES, null, Configuracion.class));
    	comboConfig.getItems().addAll(configuraciones);
    	comboConfig.setValue(configuraciones.get(0));
    	conn.desconectar();
    	
    }

    @FXML
    void cargarConfiguracion(MouseEvent event) {
    	FicheroBackUp ficheroBackup = new FicheroBackUp(log);
		ConfiguracionJsonCreator backup = new ConfiguracionJsonCreator(log);
		File fJson = ficheroBackup.seleccionarArchivoBackUp((Stage) descargarConfiguracion.getScene().getWindow());
		backup.leerJsonEInsertarEnBBDD(fJson);
		
		cargarConfiguraciones();
    }

    @FXML
    void descargarConfiguracion(MouseEvent event) {
    	int selectedIndex = comboConfig.getSelectionModel().getSelectedIndex();
    	
    	if(selectedIndex == 0 )
		{
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Aviso");
            alert.setContentText("Debe seleccionar una configuracion valida");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
		}
    	else
    	{
    		String nombre = comboConfig.getValue().getDES_CONFIGURACION();
    		FicheroBackUp escritor = new FicheroBackUp(log);
    		File fileJson = escritor.seleccionarCarpeta((Stage) descargarConfiguracion.getScene().getWindow(), nombre);
    		Configuracion seleccionado = comboConfig.getValue();
    		ConfiguracionJsonCreator backup = new ConfiguracionJsonCreator(seleccionado.getID(), seleccionado.getDES_CONFIGURACION(), log);
    		backup.generarJson(fileJson);
    	}
    }
}
