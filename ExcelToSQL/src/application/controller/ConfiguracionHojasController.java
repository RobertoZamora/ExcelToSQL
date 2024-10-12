package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.BBDD.ConexionBBDD;
import application.BBDD.DBQuery;
import application.constantes.Constantes;
import application.modelo.Accion;
import application.modelo.Binaria;
import application.modelo.CamposTipo;
import application.modelo.Parametrizada;
import application.modelo.Simplificada;
import application.modelo.Tabla;
import application.modelo.Traducida;
import application.trazas.Trazas;
import application.util.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.log4j.Level;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfiguracionHojasController
{
	private Trazas log;
	private Tabla tablaOri;
	private Tabla tablaEdit;
	private ArrayList<Binaria> binariasOri;
	private ArrayList<Simplificada> simplificadasOri;
	private ArrayList<Simplificada> simplificadasEdit;
	private ArrayList<Parametrizada> parametrizadasOri;
	private ArrayList<Parametrizada> parametrizadasEdit;
	private ArrayList<Traducida> traducidasOri;
	private ArrayList<Traducida> traducidasEdit;
	private ArrayList<Accion> accionesOri;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView ToolTipPestagna;

    @FXML
    private JFXTextField nombrePestagna;

    @FXML
    private ImageView ToolTipTabla;

    @FXML
    private JFXTextField nombreTabla;

    @FXML
    private ImageView ToolTipColumnas;

    @FXML
    private JFXTextField filaColumnas;

    @FXML
    private ImageView ToolTipBloqueos;

    @FXML
    private JFXTextField filaBloqueos;

    @FXML
    private ImageView ToolTipFilaDatos;

    @FXML
    private ImageView ToolTipColumnaDatos;

    @FXML
    private JFXTextField columnaDatos;

    @FXML
    private JFXTextField filaDatos;

    @FXML
    private ImageView ToolTipPK;

    @FXML
    private JFXComboBox<String> comboColumnPK;

    @FXML
    private JFXButton addColumnPK;

    @FXML
    private JFXButton delColumnPK;

    @FXML
    private ImageView ToolTipAccionesTablas;

    @FXML
    private JFXCheckBox insertCheck;

    @FXML
    private JFXCheckBox updateCheck;

    @FXML
    private JFXCheckBox delteCheck;

    @FXML
    private ImageView ToolTipBinarias;

    @FXML
    private JFXComboBox<String> comboColumnBin;

    @FXML
    private JFXButton addColumnBin;

    @FXML
    private JFXButton delColumnBin;

    @FXML
    private ImageView ToolTipSimplificadas;

    @FXML
    private JFXComboBox<String> ComboSimpli;

    @FXML
    private JFXButton addColumnSimp;

    @FXML
    private JFXButton editColumnSimp;

    @FXML
    private JFXButton delColumnSimp;

    @FXML
    private ImageView ToolTipParametrizadas;

    @FXML
    private JFXComboBox<String> ComboParam;

    @FXML
    private JFXButton addColumnParm;

    @FXML
    private JFXButton editColumnaParm;

    @FXML
    private JFXButton delColumnParm;

    @FXML
    private ImageView ToolTipTraColumn;

    @FXML
    private JFXComboBox<String> ComboTraducc;

    @FXML
    private JFXButton addTradColumn;

    @FXML
    private JFXButton editTradColumn;

    @FXML
    private JFXButton delTradColumn;

    @FXML
    private JFXButton guardar;

    @FXML
    private JFXButton reset;

    @FXML
    private JFXButton cancelar;

    @FXML
    void agnadirColumnBin(MouseEvent event)
    {
    	try
    	{
    		if(nombreTabla.getText().trim().length() > 0 && nombrePestagna.getText().trim().length() > 0)
    		{
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
	            seleccionable.setTitle("Columna Binaria");
	
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
					boolean esta = false;
					for(int i = 0; i < comboColumnBin.getItems().size() && !esta; i++)
					{
						if(comboColumnBin.getItems().get(i).equalsIgnoreCase(aux))
						{
							esta = true;
						}
					}
					
					if(!esta)
					{
						aux = aux.toUpperCase();
						ArrayList<String> auxList = new ArrayList<String>();
						auxList.add(aux);
						comboColumnBin.getItems().addAll(auxList);
						comboColumnBin.setValue(auxList.get(0));
					}
					else
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
		                alert.setHeaderText(null);
		                alert.setTitle("Error");
		                alert.setContentText("El campo ya existe.");
		                // Agregar el icono personalizado
		                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
		                alert.showAndWait();
		                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
					}
				}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Antes debe rellenar el nombre de la tabla y de la pestaña.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
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
    void agnadirColumnPK(MouseEvent event)
    {
    	try
    	{
    		if(nombreTabla.getText().trim().length() > 0 && nombrePestagna.getText().trim().length() > 0)
    		{
    			ArrayList<String[]> parametros = new ArrayList<String[]>();
            	String[] auxId = new String[2];
            	auxId[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
            	auxId[1] = String.valueOf(tablaOri.getID_CONFIG());
            	parametros.add(auxId);
            	
            	String[] auxTabla = new String[2];
            	auxTabla[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
            	auxTabla[1] = nombreTabla.getText().trim();
            	parametros.add(auxTabla);

            	ConexionBBDD conn = new ConexionBBDD(log);
            	conn.conectar();
            	ArrayList<CamposTipo> configCamposTipo = conn.recuperarDatos(DBQuery.SQL_CAMPOS_TIPO, parametros, CamposTipo.class);
                conn.desconectar();
    			
                if(configCamposTipo.size() > 0)
                {
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
                    seleccionable.setTitle("Columna Primary Key");

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
        				boolean esta = false;
    					for(int i = 0; i < comboColumnPK.getItems().size() && !esta; i++)
    					{
    						if(comboColumnPK.getItems().get(i).equalsIgnoreCase(aux))
    						{
    							esta = true;
    						}
    					}
    					
    					if(!esta)
    					{
    						
    						for(int i = 0; i < configCamposTipo.size(); i++)
    							if(aux.trim().equalsIgnoreCase(configCamposTipo.get(i).getCAMPO()))
    								esta = true;
    						
    						if(esta)
    						{
        	    				aux = aux.toUpperCase();
        	    				ArrayList<String> auxList = new ArrayList<String>();
        	    				auxList.add(aux);
        	    				comboColumnPK.getItems().addAll(auxList);
        	    				comboColumnPK.setValue(auxList.get(0));
    						}
    						else
    						{
    							Alert alert = new Alert(Alert.AlertType.ERROR);
        		                alert.setHeaderText(null);
        		                alert.setTitle("Error");
        		                alert.setContentText("El campo introducido no esta entre los configurados para esta tabla.");
        		                // Agregar el icono personalizado
        		                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        		                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
        		                alert.showAndWait();
        		                log.log(Level.ERROR, "El campo introducido no esta entre los configurados para esta tabla.");
    						}

    					}
    					else
    					{
    						Alert alert = new Alert(Alert.AlertType.ERROR);
    		                alert.setHeaderText(null);
    		                alert.setTitle("Error");
    		                alert.setContentText("El campo ya existe.");
    		                // Agregar el icono personalizado
    		                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    		                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
    		                alert.showAndWait();
    		                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
    					}	
        			}
                }
                else
                {
                	Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setHeaderText(null);
	                alert.setTitle("Error");
	                alert.setContentText("Antes de poder configurar la primary key debes configurar los campos para esta tabla.");
	                // Agregar el icono personalizado
	                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
	                alert.showAndWait();
	                log.log(Level.ERROR, "Antes de poder configurar la primary key debes configurar los campos para esta tabla.");
                }
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Antes debe rellenar el nombre de la tabla y de la pestaña.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
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
    void agnadirColumnParm(MouseEvent event)
    {
    	try
    	{
    		if(nombreTabla.getText().trim().length() > 0 && nombrePestagna.getText().trim().length() > 0)
    		{
    			Tabla tablaAux = new Tabla();
    			tablaAux.setID_CONFIG(tablaOri.getID_CONFIG());
    			tablaAux.setNOMBRE_TABLA(nombreTabla.getText().trim());
    			tablaAux.setPESTAGNA(nombrePestagna.getText().trim());
    			
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Parametrizada.fxml"));
    			
    			ObservableList<String> items = ComboParam.getItems();
    			ArrayList<String> arrayList = new ArrayList<>(items);

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ParametrizadaController.class) {
                    	ParametrizadaController seleccion = new ParametrizadaController();
                        seleccion.setDato(log, tablaAux, null, arrayList);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Añadir nueva Parametrizada");

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
                
                ArrayList<Parametrizada> aux = ((ParametrizadaController)loader.getController()).getDato();
    			
    			if(aux != null &&  aux.size() > 0)
    			{
    				if(parametrizadasEdit == null)
    					parametrizadasEdit = new ArrayList<Parametrizada>();
    				parametrizadasEdit.addAll(aux);
    				
    				String simpleNueva = aux.get(0).getCOLUMNA_PARAMETRIZADA();
    				
    				ComboParam.getItems().add(simpleNueva);
    				ComboParam.setValue(simpleNueva);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Antes debe rellenar el nombre de la tabla y de la pestaña.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
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
    void agnadirColumnSimp(MouseEvent event) {
    	try
    	{
    		if(nombreTabla.getText().trim().length() > 0 && nombrePestagna.getText().trim().length() > 0)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Simplificada.fxml"));
    			
    			ObservableList<String> items = ComboSimpli.getItems();
    			ArrayList<String> arrayList = new ArrayList<>(items);
    			
    			Tabla tablaAux = new Tabla();
    			tablaAux.setID_CONFIG(tablaOri.getID_CONFIG());
    			tablaAux.setNOMBRE_TABLA(nombreTabla.getText().trim());
    			tablaAux.setPESTAGNA(nombrePestagna.getText().trim());
    			
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == SimplificadaController.class) {
                    	SimplificadaController seleccion = new SimplificadaController();
                        seleccion.setDato(log, tablaAux, null, arrayList);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Simplificadas");

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
                
                ArrayList<Simplificada> aux = ((SimplificadaController)loader.getController()).getDato();
    			
    			if(aux != null &&  aux.size() > 0)
    			{
    				if(simplificadasEdit == null)
    					simplificadasEdit = new ArrayList<Simplificada>();
    				simplificadasEdit.addAll(aux);
    				
    				String simpleNueva = aux.get(0).getCOLUMNA_SIMPLIFICADA();
    				
    				ComboSimpli.getItems().add(simpleNueva);
    				ComboSimpli.setValue(simpleNueva);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Antes debe rellenar el nombre de la tabla y de la pestaña.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
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
    void editColumnSimp(MouseEvent event)
    {
    	try
    	{
    		if(ComboSimpli.getItems().size() > 0)
    		{
    			
    			Tabla tablaAux = new Tabla();
    			tablaAux.setID_CONFIG(tablaOri.getID_CONFIG());
    			tablaAux.setNOMBRE_TABLA(nombreTabla.getText().trim());
    			tablaAux.setPESTAGNA(nombrePestagna.getText().trim());
    			
    			String optionReplace = ComboSimpli.getSelectionModel().getSelectedItem();
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Simplificada.fxml"));
    			
    			ObservableList<String> items = ComboSimpli.getItems();
    			ArrayList<String> arrayList = new ArrayList<>(items);

    			ArrayList<Simplificada> auxEdit = simplificadasEdit.stream()
    	                .filter(simplificada -> simplificada.getCOLUMNA_SIMPLIFICADA().equals(optionReplace))
    	                .collect(Collectors.toCollection(ArrayList::new));
    			
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == SimplificadaController.class) {
                    	SimplificadaController seleccion = new SimplificadaController();
                        seleccion.setDato(log, tablaAux, auxEdit, arrayList);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Columna Primary Key");

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
                
                ArrayList<Simplificada> aux = ((SimplificadaController)loader.getController()).getDato();
    			
    			if(aux != null &&  aux.size() > 0)
    			{
    				simplificadasEdit.removeAll(auxEdit);
    				
    				if(simplificadasEdit == null)
    					simplificadasEdit = new ArrayList<Simplificada>();
    				simplificadasEdit.addAll(aux);
    				
    				String simpleNueva = aux.get(0).getCOLUMNA_SIMPLIFICADA();
    				
    				ComboSimpli.getItems().remove(optionReplace);
    				ComboSimpli.getItems().add(simpleNueva);
    				ComboSimpli.setValue(simpleNueva);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Para poder editar una columna simplificada primero debe existir una ya creada.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Para poder editar una columna simplificada primero debe existir una ya creada.");
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
    void agnadirTradColumn(MouseEvent event)
    {
    	try
    	{
    		if(nombreTabla.getText().trim().length() > 0)
    		{
    			Tabla tablaAux = new Tabla();
    			tablaAux.setID_CONFIG(tablaOri.getID_CONFIG());
    			tablaAux.setNOMBRE_TABLA(nombreTabla.getText().trim());
    			tablaAux.setPESTAGNA(nombrePestagna.getText().trim());
    			
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Traducida.fxml"));
    			
    			ObservableList<String> items = ComboTraducc.getItems();
    			ArrayList<String> arrayList = new ArrayList<>(items);

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == TraducidaController.class) {
                    	TraducidaController seleccion = new TraducidaController();
                        seleccion.setDato(log, tablaAux, null, arrayList);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Añadir nueva Traduccion");

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
                
                Traducida aux = ((TraducidaController)loader.getController()).getDato();
    			
    			if(aux != null)
    			{
    				if(traducidasEdit == null)
    					traducidasEdit = new ArrayList<Traducida>();
    				traducidasEdit.add(aux);
    				
    				String simpleNueva = aux.getCOLUMNA_EXCEL();
    				
    				ComboTraducc.getItems().add(simpleNueva);
    				ComboTraducc.setValue(simpleNueva);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Antes debe rellenar el nombre de la tabla.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Antes debe rellenar el nombre de la tabla.");
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
    void borrarColumnBin(MouseEvent event)
    {   		
    	try {
			String selectedItem = comboColumnBin.getSelectionModel().getSelectedItem();
            
    		if(selectedItem != null)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfirmarController.class) {
                    	ConfirmarController seleccion = new ConfirmarController();
                        seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + selectedItem + "' ¿Esta usted seguro?.");
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
                	comboColumnBin.getItems().remove(selectedItem);
                	if(comboColumnBin.getItems().size() > 0)
                		comboColumnBin.setValue(comboColumnBin.getItems().get(0));
                }
			}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe elegir un campo.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe elegir un campo.");
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
    void borrarColumnPK(MouseEvent event)
    {
    	try
    	{
    		String selectedItem = comboColumnPK.getSelectionModel().getSelectedItem();
            
    		if(selectedItem != null)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfirmarController.class) {
                    	ConfirmarController seleccion = new ConfirmarController();
                        seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + selectedItem + "' ¿Esta usted seguro?.");
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
                	comboColumnPK.getItems().remove(selectedItem);
                	
                	if(comboColumnPK.getItems().size() > 0)
                		comboColumnPK.setValue(comboColumnPK.getItems().get(0));
                }
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe elegir un campo.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe elegir un campo.");
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
    void borrarColumnParm(MouseEvent event)
    {
    	try
    	{
			String selectedItem = ComboParam.getSelectionModel().getSelectedItem();
            
    		if(selectedItem != null)
    		{

    			String optionReplace = ComboParam.getSelectionModel().getSelectedItem();

    			ArrayList<Parametrizada> auxEdit = parametrizadasEdit.stream()
    	                .filter(parametrizada -> parametrizada.getCOLUMNA_PARAMETRIZADA().equals(optionReplace))
    	                .collect(Collectors.toCollection(ArrayList::new));
    			
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfirmarController.class) {
                    	ConfirmarController seleccion = new ConfirmarController();
                        seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + selectedItem + "' ¿Esta usted seguro?.");
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
                	ComboParam.getItems().remove(selectedItem);
                	parametrizadasEdit.removeAll(auxEdit);
                	
                	if(ComboParam.getItems().size() > 0)
                		ComboParam.setValue(ComboParam.getItems().get(0));
                }
			}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe elegir un campo.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe elegir un campo.");
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
    void borrarColumnSimp(MouseEvent event)
    {
    	try
    	{
			String selectedItem = ComboSimpli.getSelectionModel().getSelectedItem();
            
    		if(selectedItem != null)
    		{
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));

    			String optionReplace = ComboSimpli.getSelectionModel().getSelectedItem();

    			ArrayList<Simplificada> auxEdit = simplificadasEdit.stream()
    	                .filter(simplificada -> simplificada.getCOLUMNA_SIMPLIFICADA().equals(optionReplace))
    	                .collect(Collectors.toCollection(ArrayList::new));
    			
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfirmarController.class) {
                    	ConfirmarController seleccion = new ConfirmarController();
                        seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + selectedItem + "' ¿Esta usted seguro?.");
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
                	ComboSimpli.getItems().remove(selectedItem);
                	simplificadasEdit.removeAll(auxEdit);
                	
                	if(ComboSimpli.getItems().size() > 0)
                		ComboSimpli.setValue(ComboSimpli.getItems().get(0));
                }
			}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe elegir un campo.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe elegir un campo.");
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
    void borrarTradColumn(MouseEvent event)
    {
    	try
    	{
			String selectedItem = ComboTraducc.getSelectionModel().getSelectedItem();
            
    		if(selectedItem != null)
    		{
    			String optionReplace = ComboTraducc.getSelectionModel().getSelectedItem();
    			
    			ArrayList<Traducida> auxEdit = traducidasEdit.stream()
    	                .filter(parametrizada -> parametrizada.getCOLUMNA_EXCEL().equals(optionReplace))
    	                .collect(Collectors.toCollection(ArrayList::new));
    			
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Confirmar.fxml"));
    			
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ConfirmarController.class) {
                    	ConfirmarController seleccion = new ConfirmarController();
                        seleccion.setDato(log, "Se va a proceder a borrar la configuracion '" + selectedItem + "' ¿Esta usted seguro?.");
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

    				traducidasEdit.removeAll(auxEdit);
    				ComboTraducc.getItems().remove(selectedItem);
                	
                	if(ComboTraducc.getItems().size() > 0)
                		ComboTraducc.setValue(ComboSimpli.getItems().get(0));
                }
			}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Debe elegir un campo.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Debe elegir un campo.");
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
    void cancelar(MouseEvent event)
    {
    	Stage stage = (Stage) nombrePestagna.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarColumnParm(MouseEvent event)
    {
    	try
    	{
    		if(ComboParam.getItems().size() > 0)
    		{
    			Tabla tablaAux = new Tabla();
    			tablaAux.setID_CONFIG(tablaOri.getID_CONFIG());
    			tablaAux.setNOMBRE_TABLA(nombreTabla.getText().trim());
    			tablaAux.setPESTAGNA(nombrePestagna.getText().trim());
    			
    			String optionReplace = ComboParam.getSelectionModel().getSelectedItem();
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Parametrizada.fxml"));

    			ArrayList<Parametrizada> auxEdit = parametrizadasEdit.stream()
    	                .filter(parametrizada -> parametrizada.getCOLUMNA_PARAMETRIZADA().equals(optionReplace))
    	                .collect(Collectors.toCollection(ArrayList::new));
    			
    			ObservableList<String> items = ComboParam.getItems();
    			ArrayList<String> arrayList = new ArrayList<>(items);
    			
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == ParametrizadaController.class) {
                    	ParametrizadaController seleccion = new ParametrizadaController();
                        seleccion.setDato(log, tablaAux, auxEdit, arrayList);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Editar Parametrizada");

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
                
                ArrayList<Parametrizada> aux = ((ParametrizadaController)loader.getController()).getDato();
    			
    			if(aux != null &&  aux.size() > 0)
    			{
    				parametrizadasEdit.removeAll(auxEdit);
    				
    				if(parametrizadasEdit == null)
    					parametrizadasEdit = new ArrayList<Parametrizada>();
    				parametrizadasEdit.addAll(aux);
    				
    				String simpleNueva = aux.get(0).getCOLUMNA_PARAMETRIZADA();
    				
    				ComboParam.getItems().remove(optionReplace);
    				ComboParam.getItems().add(simpleNueva);
    				ComboParam.setValue(simpleNueva);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Para poder editar una columna Parametrizada primero debe existir una ya creada.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Para poder editar una columna Parametrizada primero debe existir una ya creada.");
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
    void editarTradColumn(MouseEvent event)
    {
    	try
    	{
    		if(ComboTraducc.getItems().size() > 0)
    		{
    			Tabla tablaAux = new Tabla();
    			tablaAux.setID_CONFIG(tablaOri.getID_CONFIG());
    			tablaAux.setNOMBRE_TABLA(nombreTabla.getText().trim());
    			tablaAux.setPESTAGNA(nombrePestagna.getText().trim());
    			
    			String optionReplace = ComboTraducc.getSelectionModel().getSelectedItem();
    			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/Traducida.fxml"));
    			
    			ObservableList<String> items = ComboTraducc.getItems();
    			ArrayList<String> arrayList = new ArrayList<>(items);

    			ArrayList<Traducida> auxEdit = traducidasEdit.stream()
    	                .filter(parametrizada -> parametrizada.getCOLUMNA_EXCEL().equals(optionReplace))
    	                .collect(Collectors.toCollection(ArrayList::new));
    			
                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == TraducidaController.class) {
                    	TraducidaController seleccion = new TraducidaController();
                        seleccion.setDato(log, tablaAux, auxEdit.get(0), arrayList);
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Editar Traducida");

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
                
                Traducida aux = ((TraducidaController)loader.getController()).getDato();
    			
    			if(aux != null)
    			{
    				
    				if(traducidasEdit == null)
    					traducidasEdit = new ArrayList<Traducida>();
    				
    				traducidasEdit.removeAll(auxEdit);
    				traducidasEdit.add(aux);
    				
    				String simpleNueva = aux.getCOLUMNA_EXCEL();
    				
    				ComboTraducc.getItems().remove(optionReplace);
    				ComboTraducc.getItems().add(simpleNueva);
    				ComboTraducc.setValue(simpleNueva);
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Para poder editar una columna Traducida primero debe existir una ya creada.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
                log.log(Level.ERROR, "Para poder editar una columna Traducida primero debe existir una ya creada.");
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
    void guardar(MouseEvent event)
    {
		
    	ConexionBBDD conn = new ConexionBBDD(log);
    	conn.conectar();
    	
    	String textoError = "";
    	
    	// validamos que el nombre de la pestaña esta relleno
    	if(nombrePestagna.getText().trim().length() == 0)
    	{
    		textoError = "El campo nombre pestaña debe estar relleno.";
    	}
        
    	// validamos que el nombre de la tabla esta relleno
    	if(nombreTabla.getText().trim().length() == 0)
    	{
    		if(textoError.trim().length() > 0)
    			textoError += "\n";
    		textoError += "El campo nombre tabla debe estar relleno.";
    	}
    	
        // validar que la pestaña no existe
    	ArrayList<String[]> parametros = new ArrayList<String[]>();
    	String[] auxPestagnas = new String[2];
    	auxPestagnas[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
    	auxPestagnas[1] = String.valueOf(tablaOri.getID_CONFIG());
    	parametros.add(auxPestagnas);
    	
    	String[] auxPestagnas3 = new String[2];
    	auxPestagnas3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    	auxPestagnas3[1] = String.valueOf(nombrePestagna.getText().trim());
    	parametros.add(auxPestagnas3);
    	
        ArrayList<Tabla> pestagnas = conn.recuperarDatos(DBQuery.SQL_TABLAS_PESTAGNA, parametros, Tabla.class);
    	
        if(pestagnas.size() > 0 && tablaOri.getPESTAGNA() == null)
    	{
    		if(textoError.trim().length() > 0)
    			textoError += "\n";
    		textoError += "El nombre de la pestaña ya existe.";
    	}
    	
    	// validamos que el campo de columnas este relleno y sea numerico
    	if(filaColumnas.getText().trim().length() == 0)
    	{
    		if(textoError.trim().length() > 0)
    			textoError += "\n";
    		textoError += "El campo fila de columnas debe estar relleno.";
    	}
    	else if(!Util.isNumber(filaColumnas.getText().trim()))
    	{
    		if(textoError.trim().length() > 0)
    			textoError += "\n";
    		textoError += "El campo fila de columnas debe ser numerico.";
    	}
    	
    	// validamos que el campo de datos este relleno y sea numerico
    	if(filaDatos.getText().trim().length() == 0)
    	{
    		if(textoError.trim().length() > 0)
    			textoError += "\n";
    		textoError += "El campo fila de datos debe estar relleno.";
    	}
    	else if(!Util.isNumber(filaDatos.getText().trim()))
    	{
    		if(textoError.trim().length() > 0)
    			textoError += "\n";
    		textoError += "El campo fila de datos debe ser numerico.";
    	}
    	
    	if(filaBloqueos.getText().trim().length() == 0)
    	{
    		filaBloqueos.setText("0");
    	}
    	
    	if(textoError.isEmpty())
    	{
    		if(tablaEdit == null)
    			tablaEdit = new Tabla();
    		
    		tablaEdit.setID_CONFIG(tablaOri.getID_CONFIG());
    		tablaEdit.setPESTAGNA(nombrePestagna.getText().trim().toUpperCase());
    		tablaEdit.setNOMBRE_TABLA(nombreTabla.getText().trim().toUpperCase());
    		tablaEdit.setLINEA_COLUMNAS(Integer.parseInt(filaColumnas.getText().trim()) == 0?null:Integer.parseInt(filaColumnas.getText().trim()) - 1);
    		tablaEdit.setLINEA_BLOQUEOS(Integer.parseInt(filaBloqueos.getText().trim()) == 0?null:Integer.parseInt(filaBloqueos.getText().trim()) - 1);
    		tablaEdit.setLINEA_DATOS   (Integer.parseInt(filaDatos.getText().trim()) == 0?null:Integer.parseInt(filaDatos.getText().trim()) - 1);
    		tablaEdit.setCOLUMNA_DATOS   (Integer.parseInt(columnaDatos.getText().trim()));
    		
    		String PK = "";
    		for(int i = 0; i < comboColumnPK.getItems().size(); i++)
    		{
    			PK += comboColumnPK.getItems().get(i);
    			if(i != comboColumnPK.getItems().size() - 1)
    			{
    				PK += "#";
    			}
    		}
    		tablaEdit.setPK_TABLA(PK);    		
    		
    		ArrayList<Binaria> binarias = new ArrayList<Binaria>();
    		
    		for(int i = 0; i < comboColumnBin.getItems().size(); i++)
    		{
    			Binaria aux = new Binaria();
    			aux.setID_CONFIG(tablaEdit.getID_CONFIG());
    			aux.setNOMBRE_TABLA(tablaEdit.getNOMBRE_TABLA());
    			aux.setPESTAGNA(tablaEdit.getPESTAGNA());
    			aux.setCOLUMNA_BINARIA(comboColumnBin.getItems().get(i));
    			binarias.add(aux);
    		}
	    	
	    	ArrayList<String[]> paramDelTabla = new ArrayList<String[]>();
	    	
	    	// parametro 1
    		String[] auxParam = new String[3];
    		auxParam[0] = "ID_CONFIG";
    		auxParam[1] = Constantes.getSqlTipo(Constantes.SQL_INT);
    		auxParam[2] = String.valueOf(tablaOri.getID_CONFIG());
    		paramDelTabla.add(auxParam);
    		
    		// parametro 2
    		String[] auxParam2 = new String[3];
    		auxParam2[0] = "PESTAGNA";
    		auxParam2[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    		auxParam2[2] = String.valueOf(tablaOri.getPESTAGNA());
    		paramDelTabla.add(auxParam2);
    		
    		// parametro 3
    		String[] auxParam3 = new String[3];
    		auxParam3[0] = "NOMBRE_TABLA";
    		auxParam3[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    		auxParam3[2] = String.valueOf(tablaOri.getNOMBRE_TABLA());
    		paramDelTabla.add(auxParam3);    		
    		
    		// ELIMINAMOS TABLA ANTES DE INSERTAR
    		conn.eliminarDato(Constantes.TABLAS, paramDelTabla);
	    	
    		// INSERTAMOS LA TABLA
	    	conn.insertarDato(Constantes.TABLAS, tablaEdit);

    		// ELIMINAMOS BINARAS ANTES DE INSERTAR
	    	conn.eliminarDato(Constantes.BINARIAS, paramDelTabla);
    		
    		// INSERTAMOS LAS BINARIAS CONFIGURADAS
	    	for(Binaria binaria : binarias)
	    	{
		    	conn.insertarDato(Constantes.BINARIAS, binaria);
	    	}

    		// ELIMINAMOS SIMPLIFICADAS ANTES DE INSERTAR
	    	conn.eliminarDato(Constantes.SIMPLIFICADAS, paramDelTabla);
    		
    		if(simplificadasEdit == null)
    			simplificadasEdit = new ArrayList<Simplificada>();
    		
    		// INSERTAMOS LAS SIMPLIFICADAS CONFIGURADAS
	    	for(Simplificada simplificada : simplificadasEdit)
	    	{
		    	conn.insertarDato(Constantes.SIMPLIFICADAS, simplificada);
	    	}

    		// ELIMINAMOS PARAMETRIZADAS ANTES DE INSERTAR
	    	conn.eliminarDato(Constantes.PARAMETRIZADAS, paramDelTabla);
    		
    		if(parametrizadasEdit == null)
    			parametrizadasEdit = new ArrayList<Parametrizada>();
    		
    		// INSERTAMOS LAS PARAMETRIZADAS CONFIGURADAS
	    	for(Parametrizada parametrizada : parametrizadasEdit)
	    	{
		    	conn.insertarDato(Constantes.PARAMETRIZADAS, parametrizada);
	    	}
	    	
	    	// ELIMINAMOS TRADUCIDAS ANTES DE INSERTAR
	    	conn.eliminarDato(Constantes.TRADUCCIONES, paramDelTabla);
    		
    		if(traducidasEdit == null)
    			traducidasEdit = new ArrayList<Traducida>();
    		
    		// INSERTAMOS LAS TRADUCIDAS CONFIGURADAS
	    	for(Traducida traducida : traducidasEdit)
	    	{
		    	conn.insertarDato(Constantes.TRADUCCIONES, traducida);
	    	}
	    	
	    	// ELIMINAMOS TRADUCIDAS ANTES DE INSERTAR
	    	conn.eliminarDato(Constantes.T_ACCIONES, paramDelTabla);
	    	
    		ArrayList<Accion> acciones = new ArrayList<Accion>();
	    	
    		if(insertCheck.isSelected())
    		{
    			Accion aux = new Accion();
    			aux.setID_CONFIG(tablaEdit.getID_CONFIG());
    			aux.setNOMBRE_TABLA(tablaEdit.getNOMBRE_TABLA());
    			aux.setPESTAGNA(tablaEdit.getPESTAGNA());
    			aux.setACCION("INSERT");
    			acciones.add(aux);
    		}
    		
    		if(updateCheck.isSelected())
    		{
    			Accion aux = new Accion();
    			aux.setID_CONFIG(tablaEdit.getID_CONFIG());
    			aux.setNOMBRE_TABLA(tablaEdit.getNOMBRE_TABLA());
    			aux.setPESTAGNA(tablaEdit.getPESTAGNA());
    			aux.setACCION("UPDATE");
    			acciones.add(aux);
    		}
    		
    		if(delteCheck.isSelected())
    		{
    			Accion aux = new Accion();
    			aux.setID_CONFIG(tablaEdit.getID_CONFIG());
    			aux.setNOMBRE_TABLA(tablaEdit.getNOMBRE_TABLA());
    			aux.setPESTAGNA(tablaEdit.getPESTAGNA());
    			aux.setACCION("DELETE");
    			acciones.add(aux);
    		}
    		
    		for(Accion accion : acciones)
	    	{
		    	conn.insertarDato(Constantes.T_ACCIONES, accion);
	    	}
    		
	    	conn.desconectar();    		

        	Stage stage = (Stage) nombrePestagna.getScene().getWindow();
            stage.close();    		
    	}
    	else
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(textoError);
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
            log.log(Level.INFO, textoError);
	    	conn.desconectar();
    	}
    }

    @FXML
    void reset(MouseEvent event)
    {
    	cargarDatosOriginales();
    }
    
    public void setDato(Trazas log, Tabla tablaOri)
    {
    	this.log = log;
    	this.tablaOri = tablaOri;
    }
    
    public Tabla getDato()
    {
    	return tablaEdit;
    }
    
    public void cargarDatosOriginales()
    {
    	if(tablaOri != null)
        {
            nombrePestagna.setText(tablaOri.getPESTAGNA() != null?tablaOri.getPESTAGNA():"");
            nombreTabla.setText(tablaOri.getNOMBRE_TABLA() != null?tablaOri.getNOMBRE_TABLA():"");
            
            filaColumnas.setText(tablaOri.getLINEA_COLUMNAS() == null?"0":String.valueOf(tablaOri.getLINEA_COLUMNAS() + 1));
            filaBloqueos.setText(tablaOri.getLINEA_BLOQUEOS() == null?"0":String.valueOf(tablaOri.getLINEA_BLOQUEOS() + 1));
            filaDatos.setText(tablaOri.getLINEA_DATOS() == null?"0":String.valueOf(tablaOri.getLINEA_DATOS() + 1));
            columnaDatos.setText(tablaOri.getCOLUMNA_DATOS() == null?"0":String.valueOf(tablaOri.getCOLUMNA_DATOS()));

            comboColumnPK.getSelectionModel().clearSelection();  // Limpiar la selección actual
            comboColumnPK.getItems().clear();  // Limpiar todos los elementos
            String sPk = tablaOri.getPK_TABLA();
            if(sPk != null)
            {
                String pk[] = sPk.split("#");
                ArrayList<String> pkList = new ArrayList<>(Arrays.asList(pk));
                
                comboColumnPK.getItems().addAll(pkList);
                comboColumnPK.setValue(pkList.get(0));
            }
    		    		
    		comboColumnBin.getSelectionModel().clearSelection();  // Limpiar la selección actual
    		comboColumnBin.getItems().clear();  // Limpiar todos los elementos
    		if(binariasOri.size() > 0) 
    		{
    			ArrayList<String> auxBin = new ArrayList<String>();
    			for(Binaria binaria : binariasOri)
    			{
    				auxBin.add(binaria.getCOLUMNA_BINARIA());
    			}
        		comboColumnBin.getItems().addAll(auxBin);
        		comboColumnBin.setValue(auxBin.get(0));
    		}
    		
    		ComboSimpli.getSelectionModel().clearSelection();  // Limpiar la selección actual
    		ComboSimpli.getItems().clear();  // Limpiar todos los elementos
    		if(simplificadasOri.size() > 0)
    		{
    			ArrayList<String> auxSim = new ArrayList<String>();
    			String actual = "";
    			if(simplificadasEdit == null)
    				simplificadasEdit = new ArrayList<Simplificada>();
    			
    			for(Simplificada simplificada : simplificadasOri)
    			{
    				if(!simplificada.getCOLUMNA_SIMPLIFICADA().equals(actual))
    				{
        				auxSim.add(simplificada.getCOLUMNA_SIMPLIFICADA());
        				actual = simplificada.getCOLUMNA_SIMPLIFICADA();
    				}
    				Simplificada aux = new Simplificada();
            		aux.setID_CONFIG(simplificada.getID_CONFIG());
            		aux.setNOMBRE_TABLA(simplificada.getNOMBRE_TABLA());
            		aux.setPESTAGNA(simplificada.getPESTAGNA());
            		aux.setCOLUMNA_SIMPLIFICADA(simplificada.getCOLUMNA_SIMPLIFICADA());
            		aux.setCOLUMNA(simplificada.getCOLUMNA());
            		simplificadasEdit.add(aux);
    			}
        		ComboSimpli.getItems().addAll(auxSim);
        		ComboSimpli.setValue(auxSim.get(0));
    		}    		
   		
    		ComboParam.getSelectionModel().clearSelection();  // Limpiar la selección actual
    		ComboParam.getItems().clear();  // Limpiar todos los elementos
    		if(parametrizadasOri.size() > 0)
    		{
    			ArrayList<String> auxSim = new ArrayList<String>();
    			String actual = "";
    			if(parametrizadasEdit == null)
    				parametrizadasEdit = new ArrayList<Parametrizada>();
    			
    			for(Parametrizada parametrizada : parametrizadasOri)
    			{
    				if(!parametrizada.getCOLUMNA_PARAMETRIZADA().equals(actual))
    				{
        				auxSim.add(parametrizada.getCOLUMNA_PARAMETRIZADA());
        				actual = parametrizada.getCOLUMNA_PARAMETRIZADA();
    				}
    				Parametrizada aux = new Parametrizada();
            		aux.setID_CONFIG(parametrizada.getID_CONFIG());
            		aux.setNOMBRE_TABLA(parametrizada.getNOMBRE_TABLA());
            		aux.setPESTAGNA(parametrizada.getPESTAGNA());
            		aux.setCOLUMNA_PARAMETRIZADA(parametrizada.getCOLUMNA_PARAMETRIZADA());
            		aux.setDATO(parametrizada.getDATO());
            		aux.setTRADUCCION(parametrizada.getTRADUCCION());
            		parametrizadasEdit.add(aux);
    			}
        		ComboParam.getItems().addAll(auxSim);
        		ComboParam.setValue(auxSim.get(0));
    		}    		

    		ComboTraducc.getSelectionModel().clearSelection();  // Limpiar la selección actual
    		ComboTraducc.getItems().clear();  // Limpiar todos los elementos
    		if(traducidasOri.size() > 0)
    		{
    			ArrayList<String> auxSim = new ArrayList<String>();
    			String actual = "";
    			if(traducidasEdit == null)
    				traducidasEdit = new ArrayList<Traducida>();
    			
    			for(Traducida traducida : traducidasOri)
    			{
    				if(!traducida.getCOLUMNA_EXCEL().equals(actual))
    				{
        				auxSim.add(traducida.getCOLUMNA_EXCEL());
        				actual = traducida.getCOLUMNA_EXCEL();
    				}
    				Traducida aux = new Traducida();
            		aux.setID_CONFIG(traducida.getID_CONFIG());
            		aux.setNOMBRE_TABLA(traducida.getNOMBRE_TABLA());
            		aux.setPESTAGNA(traducida.getPESTAGNA());
            		aux.setCOLUMNA_BBDD(traducida.getCOLUMNA_BBDD());
            		aux.setCOLUMNA_EXCEL(traducida.getCOLUMNA_EXCEL());
            		traducidasEdit.add(aux);
    			}
    			ComboTraducc.getItems().addAll(auxSim);
    			ComboTraducc.setValue(auxSim.get(0));
    		}
    		

    		for(Accion accion : accionesOri)
	    	{
    			String value = accion.getACCION();
    			switch(value)
    			{
					case "INSERT":
						insertCheck.setSelected(true);
						break;
					case "UPDATE":
						updateCheck.setSelected(true);
						break;
					case "DELETE":
						delteCheck.setSelected(true);
						break;
    			}
	    	}
        }
    }

    @FXML
    void initialize()
    {
        
        ConexionBBDD conn = new ConexionBBDD(log);
        conn.conectar();
    	
    	ArrayList<String[]> param = new ArrayList<String[]>();
    	
    	// parametro 1
		String[] auxParam = new String[2];
		auxParam[0] = Constantes.getSqlTipo(Constantes.SQL_INT);
		auxParam[1] = String.valueOf(tablaOri.getID_CONFIG());
		param.add(auxParam);
		
		// parametro 2
		String[] auxParam2 = new String[2];
		auxParam2[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam2[1] = String.valueOf(tablaOri.getNOMBRE_TABLA());
		param.add(auxParam2);
		
		// parametro 3
		String[] auxParam3 = new String[2];
		auxParam3[0] = Constantes.getSqlTipo(Constantes.SQL_STRING);
		auxParam3[1] = String.valueOf(tablaOri.getPESTAGNA());
		param.add(auxParam3);
		
		binariasOri = conn.recuperarDatos(DBQuery.SQL_BINARIAS, param, Binaria.class);
		simplificadasOri = conn.recuperarDatos(DBQuery.SQL_SIMPLIFICADAS, param, Simplificada.class);
		traducidasOri = conn.recuperarDatos(DBQuery.SQL_TRADUCIDAS, param, Traducida.class);
		accionesOri = conn.recuperarDatos(DBQuery.SQL_ACCIONES, param, Accion.class);
		parametrizadasOri = conn.recuperarDatos(DBQuery.SQL_PARAMETRIZADAS, param, Parametrizada.class);
        
		conn.desconectar();
    	cargarDatosOriginales();
    }
}
