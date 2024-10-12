package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import application.Main;
import application.BBDD.ConexionBBDD;
import application.BBDD.DBQuery;
import application.constantes.Constantes;
import application.modelo.CamposTipo;
import application.modelo.ConfigColores;
import application.modelo.Tabla;
import application.trazas.Trazas;
import application.util.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ConfiguracionController {
	private Trazas log;
	private int idConfig = 0;
	private ArrayList<ConfigColores> configColores;
	private ArrayList<Tabla> configTablas;
	private ArrayList<CamposTipo> configCamposTipo;
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton seleccion;

    @FXML
    private JFXButton bloqueos;

    @FXML
    private JFXButton ignorar;

    @FXML
    private JFXComboBox<Tabla> hojas;

    @FXML
    private JFXButton addHoja;

    @FXML
    private JFXButton confHoja;

    @FXML
    private JFXButton delHoja;

    @FXML
    private JFXComboBox<CamposTipo> campos;

    @FXML
    private JFXButton addCampo;

    @FXML
    private JFXButton confCampo;

    @FXML
    private JFXButton delCampo;

    @FXML
    private JFXButton reset;

    @FXML
    void cambioCampos(ActionEvent event)
    {
    	recargarComboCampo();
    }

    @FXML
    void agnadirCampo(MouseEvent event)
    {
    	try {
    		String nombreTabla = hojas.getSelectionModel().getSelectedItem().getNOMBRE_TABLA();
    		CamposTipo camposTipo = new CamposTipo();
    		camposTipo.setID_CONFIG(idConfig);
    		camposTipo.setNOMBRE_TABLA(nombreTabla);
    		
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/ConfiguracionCampos.fxml"));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == ConfiguracionCamposController.class) {
                	ConfiguracionCamposController seleccion = new ConfiguracionCamposController();
                    seleccion.setDato(log, camposTipo);
                    return seleccion;
                }
                return null;
            });

            Parent root = loader.load();

            Stage seleccionable = new Stage();
            seleccionable.setTitle("Configurar Campo");

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
                        
            CamposTipo aux = ((ConfiguracionCamposController)loader.getController()).getDato();
			
			if(aux != null)
			{
				campos.getItems().add(aux);
				campos.setValue(aux);
				
				confCampo.setDisable(false);
	        	delCampo.setDisable(false);
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
    void agnadirHoja(MouseEvent event)
    {
    	try {
    		
    		Tabla tabla = new Tabla();
    		tabla.setID_CONFIG(idConfig);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/ConfiguracionHojas.fxml"));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == ConfiguracionHojasController.class) {
                	ConfiguracionHojasController seleccion = new ConfiguracionHojasController();
                    seleccion.setDato(log, tabla);
                    return seleccion;
                }
                return null;
            });

            Parent root = loader.load();

            Stage seleccionable = new Stage();
            seleccionable.setTitle("Añadir Hoja");

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
                        
            Tabla aux = ((ConfiguracionHojasController)loader.getController()).getDato();
			
			if(aux != null)
			{
				hojas.getItems().add(aux);
				hojas.setValue(aux);
				
				confHoja.setDisable(false);
	        	delHoja.setDisable(false);
	        	addCampo.setDisable(false);
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
    void configurarCampo(MouseEvent event)
    {
    	try {
    		
    		CamposTipo campoAux = campos.getSelectionModel().getSelectedItem();
    		
    		if(campoAux != null)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/ConfiguracionCampos.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfiguracionCamposController.class) {
                    	ConfiguracionCamposController seleccion = new ConfiguracionCamposController();
                        seleccion.setDato(log, campoAux);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Modificar Campo");

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
                
                CamposTipo aux = ((ConfiguracionCamposController)loader.getController()).getDato();
    			
    			if(aux != null)
    			{
    				campos.getItems().remove(campoAux);
    				campos.getItems().add(aux);
    				campos.setValue(aux);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe seleccionar una HOJA.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe seleccionar una HOJA.");
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
    void configurarHoja(MouseEvent event)
    {
    	try {
    		
    		Tabla tabla = hojas.getSelectionModel().getSelectedItem();
    		
    		if(tabla != null)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/ConfiguracionHojas.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfiguracionHojasController.class) {
                    	ConfiguracionHojasController seleccion = new ConfiguracionHojasController();
                        seleccion.setDato(log, tabla);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Modificar Hoja");

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
                
                Tabla aux = ((ConfiguracionHojasController)loader.getController()).getDato();
    			
    			if(aux != null)
    			{
    				hojas.getItems().remove(tabla);
    				hojas.getItems().add(aux);
    				hojas.setValue(aux);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe seleccionar una HOJA.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe seleccionar una HOJA.");
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
    void elegirBloqueos(MouseEvent event) {
    	String[] color = showColorPickerModal();
    	if(color != null)
    	{
    		bloqueos.setStyle("-fx-background-color: #" + color[1].substring(2));
    		log.log(Level.INFO, color[0]);
    		
    		ConexionBBDD conn = new ConexionBBDD(log);
			conn.conectar();
			ArrayList<String[]> param = new ArrayList<String[]>();
		    
			String[] COLOR_HEXA = new String[2];
			COLOR_HEXA[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			COLOR_HEXA[1] = color[1];
			param.add(COLOR_HEXA);
		    
			String[] COLOR_HSSF = new String[2];
			COLOR_HSSF[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			COLOR_HSSF[1] = color[2];
			param.add(COLOR_HSSF);
			
			String[] id_config = new String[2];
			id_config[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
			id_config[1] = String.valueOf(idConfig);
			param.add(id_config);
    
			String[] accion = new String[2];
			accion[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			accion[1] = Constantes.getAcciones(Constantes.ACCION_BLOQUEO);
			param.add(accion);
			
			conn.actualizarDato(DBQuery.SQL_UPDATE_CONFIG_COLORES, param);
			conn.desconectar();
    	}

    }

    @FXML
    void elegirIgnorar(MouseEvent event) {
    	String[] color = showColorPickerModal();
    	if(color != null)
    	{
    		ignorar.setStyle("-fx-background-color: #" + color[1].substring(2));
    		log.log(Level.INFO, color[0]);
    		
    		ConexionBBDD conn = new ConexionBBDD(log);
			conn.conectar();
			ArrayList<String[]> param = new ArrayList<String[]>();
		    
			String[] COLOR_HEXA = new String[2];
			COLOR_HEXA[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			COLOR_HEXA[1] = color[1];
			param.add(COLOR_HEXA);
		    
			String[] COLOR_HSSF = new String[2];
			COLOR_HSSF[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			COLOR_HSSF[1] = color[2];
			param.add(COLOR_HSSF);
			
			String[] id_config = new String[2];
			id_config[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
			id_config[1] = String.valueOf(idConfig);
			param.add(id_config);
    
			String[] accion = new String[2];
			accion[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			accion[1] = Constantes.getAcciones(Constantes.ACCION_IGNORAR);
			param.add(accion);
			
			conn.actualizarDato(DBQuery.SQL_UPDATE_CONFIG_COLORES, param);
			conn.desconectar();
    	}

    }

    @FXML
    void elegirSeleccion(MouseEvent event) {
    	String[] color = showColorPickerModal();
    	if(color != null)
    	{
    		seleccion.setStyle("-fx-background-color: #" + color[1].substring(2));
    		log.log(Level.INFO, color[0]);
    		
    		ConexionBBDD conn = new ConexionBBDD(log);
			conn.conectar();
			ArrayList<String[]> param = new ArrayList<String[]>();
		    
			String[] COLOR_HEXA = new String[2];
			COLOR_HEXA[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			COLOR_HEXA[1] = color[1];
			param.add(COLOR_HEXA);
		    
			String[] COLOR_HSSF = new String[2];
			COLOR_HSSF[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			COLOR_HSSF[1] = color[2];
			param.add(COLOR_HSSF);
			
			String[] id_config = new String[2];
			id_config[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
			id_config[1] = String.valueOf(idConfig);
			param.add(id_config);
    
			String[] accion = new String[2];
			accion[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
			accion[1] = Constantes.getAcciones(Constantes.ACCION_SELECCIONAR);
			param.add(accion);
			
			conn.actualizarDato(DBQuery.SQL_UPDATE_CONFIG_COLORES, param);
			conn.desconectar();
    	}
    }

    @FXML
    void eliminarCampo(MouseEvent event)
    {
    	try {
    		CamposTipo campo = campos.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == ConfirmarController.class) {
                	ConfirmarController seleccion = new ConfirmarController();
                    seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + campo + "' ¿Esta usted seguro?.");
                    return seleccion;
                }
                return null;
            });

            Parent root = loader.load();

            Stage seleccionable = new Stage();
            seleccionable.setTitle("Eliminar Campo");

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
				borrarCampo(campo);
				campos.getItems().remove(campo);
				if(campos.getItems().size() > 0)
				{
					campos.setValue(campos.getItems().get(0));
				}
				else
				{
					confCampo.setDisable(true);
		        	delCampo.setDisable(true);
				}
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
    
    private void borrarHoja(Tabla tabla)
    {
    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();

    	ArrayList<String[]> paramDelTabla = new ArrayList<String[]>();
	    	
    	// parametro 1
		String[] auxParam = new String[3];
		auxParam[0] = "ID_CONFIG";
		auxParam[1] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[2] = String.valueOf(tabla.getID_CONFIG());
		paramDelTabla.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[3];
		auxParam2[0] = "PESTAGNA";
		auxParam2[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[2] = String.valueOf(tabla.getPESTAGNA());
		paramDelTabla.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[3];
		auxParam3[0] = "NOMBRE_TABLA";
		auxParam3[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[2] = String.valueOf(tabla.getNOMBRE_TABLA());
		paramDelTabla.add(auxParam3);    		

		conn.eliminarDato(Constantes.TABLAS, paramDelTabla);

		ArrayList<String[]> param = new ArrayList<String[]>();
		param.add(auxParam);
		param.add(auxParam3);
    	
		conn.eliminarDato(Constantes.BINARIAS, param);
		conn.eliminarDato(Constantes.SIMPLIFICADAS, param);
		conn.eliminarDato(Constantes.PARAMETRIZADAS, param);
		conn.eliminarDato(Constantes.TRADUCCIONES, param);
		conn.eliminarDato(Constantes.T_ACCIONES, param);
		conn.eliminarDato(Constantes.CAMPOSTIPO, param);
    	conn.desconectar(); 
    }
    
    private void borrarCampo(CamposTipo campo)
    {
		ConexionBBDD conn = new ConexionBBDD(log);
		conn.conectar();
		
    	ArrayList<String[]> paramDelTabla = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[3];
		auxParam[0] = "ID_CONFIG";
		auxParam[1] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[2] = String.valueOf(campo.getID_CONFIG());
		paramDelTabla.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[3];
		auxParam2[0] = "NOMBRE_TABLA";
		auxParam2[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[2] = String.valueOf(campo.getNOMBRE_TABLA());
		paramDelTabla.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[3];
		auxParam3[0] = "CAMPO";
		auxParam3[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[2] = String.valueOf(campo.getCAMPO());
		paramDelTabla.add(auxParam3);    		

		conn.eliminarDato(Constantes.CAMPOSTIPO, paramDelTabla);
		
		conn.desconectar();
    }
    
    private void recargarComboCampo()
    {
    	campos.getSelectionModel().clearSelection();  // Limpiar la selección actual
    	campos.getItems().clear();  // Limpiar todos los elementos
    	
    	Tabla tabla = hojas.getSelectionModel().getSelectedItem();
    	
    	if(tabla != null)
    	{
        	ArrayList<String[]> parametros = new ArrayList<String[]>();
        	String[] aux = new String[2];
        	aux[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
        	aux[1] = String.valueOf(tabla.getID_CONFIG());
        	parametros.add(aux);
        	
        	String[] auxTabla = new String[2];
        	auxTabla[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
        	auxTabla[1] = tabla.getNOMBRE_TABLA();
        	parametros.add(auxTabla);

        	ConexionBBDD conn = new ConexionBBDD(log);
        	conn.conectar();
        	configCamposTipo = conn.recuperarDatos(DBQuery.SQL_CAMPOS_TIPO, parametros, CamposTipo.class);
            conn.desconectar();
            
        	if(configCamposTipo.size() > 0)
        	{
            	campos.getItems().addAll(configCamposTipo);
            	campos.setValue(configCamposTipo.get(0));
        	}
        	else
        	{
        		confCampo.setDisable(true);
            	delCampo.setDisable(true);
        	}
    	}
    }

    @FXML
    void eliminarHoja(MouseEvent event)
    {
    	try {
    		
    		Tabla tabla = hojas.getSelectionModel().getSelectedItem();
    		
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

            loader.setControllerFactory(controllerClass -> {
                if (controllerClass == ConfirmarController.class) {
                	ConfirmarController seleccion = new ConfirmarController();
                    seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + tabla + "' ¿Esta usted seguro?.");
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
				borrarHoja(tabla);
				
				hojas.getItems().remove(tabla);
				if(hojas.getItems().size() > 0)
				{
					hojas.setValue(hojas.getItems().get(0));
				}
				else
				{
					confHoja.setDisable(true);
		        	delHoja.setDisable(true);
		        	addCampo.setDisable(true);
					confCampo.setDisable(true);
		        	delCampo.setDisable(true);
		        	
		        	campos.getSelectionModel().clearSelection();  // Limpiar la selección actual
		        	campos.getItems().clear();  // Limpiar todos los elementos
				}
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
    void resetearConfiguracion(MouseEvent event)
    {
    	Stage stage = (Stage) reset.getScene().getWindow();
        stage.close();
    }
    
    public void setDato(Trazas log, int idConfig)
    {
    	this.log = log;
    	this.idConfig = idConfig;
    }
    
    public String[] showColorPickerModal() {
        Stage modalStage = new Stage(StageStyle.DECORATED);
        modalStage.initModality(Modality.APPLICATION_MODAL);

        Image icon = new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png"));
        modalStage.getIcons().add(icon);
        
        // Evita que se pueda cambiar el tamaño de la ventana
        modalStage.setResizable(false);
        
        ArrayList<Color> coloresColor = new ArrayList<Color>();
        ArrayList<String[]> colores = new ArrayList<String[]>();
        Util.colores(colores, coloresColor);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int numCols = 4;

        AtomicReference<String[]> selectedColor = new AtomicReference<>(null);

        for (int i = 0; i < coloresColor.size(); i++) {
            JFXButton colorButton = new JFXButton();
            colorButton.setMinSize(50, 50);
            colorButton.setStyle("-fx-background-color: #" + Constantes.getColores().get(i)[1].substring(2));
            int aux = i;
            colorButton.setOnAction(event -> {
                selectedColor.set(colores.get(aux));
                modalStage.close();
            });

            int colIndex = i % numCols;
            int rowIndex = i / numCols;

            gridPane.add(colorButton, colIndex, rowIndex);
        }

        StackPane root = new StackPane(gridPane);
        Scene scene = new Scene(root, 240, 180);

        modalStage.setScene(scene);
        modalStage.showAndWait();

        return selectedColor.get();
    }

    @FXML
    void initialize() {
    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] aux = new String[2];
    	aux[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	aux[1] = String.valueOf(idConfig);
    	parametros.add(aux);
        configColores = conn.recuperarDatos(DBQuery.SQL_CONFIG_COLORES, parametros, ConfigColores.class);
        configTablas = conn.recuperarDatos(DBQuery.SQL_TABLAS, parametros, Tabla.class);
        
        for(ConfigColores color : configColores)
        {
        	if(Constantes.getAcciones(Constantes.ACCION_SELECCIONAR).equals(color.getACCION()))
        	{
        		seleccion.setStyle("-fx-background-color: #" + color.getCOLOR_HEXA().substring(2));
        	}
        	else if(Constantes.getAcciones(Constantes.ACCION_IGNORAR).equals(color.getACCION()))
        	{
        		ignorar.setStyle("-fx-background-color: #" + color.getCOLOR_HEXA().substring(2));
        	}
        	else if(Constantes.getAcciones(Constantes.ACCION_BLOQUEO).equals(color.getACCION()))
        	{
        		bloqueos.setStyle("-fx-background-color: #" + color.getCOLOR_HEXA().substring(2));
        	}
        }
        
        if(configTablas.size() > 0)
        {
            hojas.getItems().addAll(configTablas);
        	hojas.setValue(configTablas.get(0));
            conn.desconectar();
        	
        	recargarComboCampo();
        }
        else
        {
        	confHoja.setDisable(true);
        	delHoja.setDisable(true);
        	addCampo.setDisable(true);
        	confCampo.setDisable(true);
        	delCampo.setDisable(true);
            conn.desconectar();
        }
    }
}
