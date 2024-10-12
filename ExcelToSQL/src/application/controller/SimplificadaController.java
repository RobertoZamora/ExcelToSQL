package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.modelo.Simplificada;
import application.modelo.Tabla;
import application.trazas.Trazas;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SimplificadaController {
	private Trazas log;
	private Tabla tabla;
	private ArrayList<Simplificada> datos;
	private ArrayList<Simplificada> datosEdit;
	private ArrayList<String> simplificadasConfiguradas;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField colSimple;

    @FXML
    private JFXComboBox<String> comboColBBDD;

    @FXML
    private JFXButton addColBBDD;

    @FXML
    private JFXButton delColBBDD;

    @FXML
    private JFXButton guardar;

    @FXML
    private JFXButton cancelar;

    @FXML
    void agnadirColuBBDD(MouseEvent event)
    {
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
            seleccionable.setTitle("Columna en BBDD");

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
				aux = aux.toUpperCase();
				ArrayList<String> auxList = new ArrayList<String>();
				auxList.add(aux);
				comboColBBDD.getItems().addAll(auxList);
				comboColBBDD.setValue(auxList.get(0));
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
    void borrarColBBDD(MouseEvent event)
    {
    	try {
			String selectedItem = comboColBBDD.getSelectionModel().getSelectedItem();
            
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
                	comboColBBDD.getItems().remove(selectedItem);
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
    void cancelar(MouseEvent event)
    {
    	Stage stage = (Stage) colSimple.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardar(MouseEvent event) {
    	
    	String mensajeError = "";
    	
    	if(colSimple.getText().trim().length() == 0)
    	{
    		mensajeError += "El campo excel debe estar relleno.";
    	}
    	
    	if(comboColBBDD.getItems().size() < 2)
    	{
    		mensajeError += "\n El campo BBDD debe tener al menos dos campos.";
    	}
    	
    	String columnaParametrizada = colSimple.getText();
		
		boolean esta = false;
		for(int i = 0; i < simplificadasConfiguradas.size(); i++)
			if(columnaParametrizada.equalsIgnoreCase(simplificadasConfiguradas.get(i)))
				esta = true;
		
		if(esta)
		{
			mensajeError += "\n La simplificada que esta intentado crear ya existe, si desea modificarla acceda a ella y editela.";
		}
    	
    	if(mensajeError.length() > 0)
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(mensajeError);
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
            log.log(Level.ERROR,mensajeError);
    	}
    	else
    	{
    		int auxIdTabla = tabla.getID_CONFIG();
    		String auxNombreTabla = tabla.getNOMBRE_TABLA();
    		String nombrePestagna = tabla.getPESTAGNA();
    		
    		datos = new ArrayList<Simplificada>();
    		
    		// rellenamos objeto
    		for(int i = 0; i < comboColBBDD.getItems().size(); i++)
    		{
    			Simplificada aux = new Simplificada();
    			aux.setID_CONFIG(auxIdTabla);
    			aux.setNOMBRE_TABLA(auxNombreTabla);
    			aux.setPESTAGNA(nombrePestagna);
    			aux.setCOLUMNA_SIMPLIFICADA(colSimple.getText().toUpperCase());
    			aux.setCOLUMNA(comboColBBDD.getItems().get(i));
    			datos.add(aux);
    		}
    		
    		// cerramos ventana
    		Stage stage = (Stage) colSimple.getScene().getWindow();
            stage.close();
    	}

    }
    
    public void setDato(Trazas log, Tabla tabla, ArrayList<Simplificada> datosEdit, ArrayList<String> simplificadasConfiguradas)
    {
    	this.log = log;
    	this.tabla = tabla;
    	this.datosEdit = datosEdit;
    	this.simplificadasConfiguradas = simplificadasConfiguradas;

    	if(datosEdit != null && simplificadasConfiguradas != null)
    	{
    		int indice = -1;
    		String nombreActual = datosEdit.get(0).getCOLUMNA_SIMPLIFICADA();
    		for(int i = 0; i < simplificadasConfiguradas.size(); i++)
    		{
    			if(nombreActual.equalsIgnoreCase(simplificadasConfiguradas.get(i)))
    			{
    				indice = i;
    			}
    		}
    		
    		if(indice != -1)
    		{
    			this.simplificadasConfiguradas.remove(indice);
    		}
    		
    	}
    }
    
    public ArrayList<Simplificada> getDato()
    {
    	return datos;
    }

    @FXML
    void initialize()
    {
    	if(datosEdit != null && datosEdit.size() > 0)
    	{
    		colSimple.setText(datosEdit.get(0).getCOLUMNA_SIMPLIFICADA());
    		ArrayList<String> auxList = new ArrayList<String>();
    		for(Simplificada simple : datosEdit)
    			auxList.add(simple.getCOLUMNA());
    		comboColBBDD.getItems().addAll(auxList);
			comboColBBDD.setValue(auxList.get(0));
    	}
    }
}
