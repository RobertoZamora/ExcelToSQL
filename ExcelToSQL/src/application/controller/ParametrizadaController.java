package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import application.modelo.Parametrizada;
import application.modelo.Tabla;
import application.trazas.Trazas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ParametrizadaController
{
	private Trazas log;
	private Tabla tabla;
	private ArrayList<Parametrizada> parametrizadasOri;
	private ArrayList<Parametrizada> parametrizadasEdit;
	private ArrayList<String> parametrizadasConfiguradas;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton addTraduccion;

    @FXML
    private JFXButton delTraduccion;

    @FXML
    private JFXButton guardar;

    @FXML
    private JFXButton cancelar;

    @FXML
    private JFXTreeTableView<Parametrizada> tablaParam;

    @FXML
    private JFXTextField columParam;

    @FXML
    void agnadirTraduccion(MouseEvent event)
    {
    	if(columParam.getText().trim().length() > 0)
    	{
    		try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/application/vistas/PedirDato.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    if (controllerClass == PedirDatoController.class) {
                        PedirDatoController seleccion = new PedirDatoController();
                        seleccion.setDato(log, "Introduzca el texto para Dato.");
                        return seleccion;
                    }
                    return null;
                });

                Parent root = loader.load();

                Stage seleccionable = new Stage();
                seleccionable.setTitle("Dato");

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
                
                String Dato = ((PedirDatoController)loader.getController()).getDato();
    			
    			if(Dato != null && !Dato.isEmpty())
    			{
    				Dato = Dato.toUpperCase();				
    				
    				FXMLLoader loader2 = new FXMLLoader(Main.class.getResource("/application/vistas/PedirDato.fxml"));

    				loader2.setControllerFactory(controllerClass -> {
    	                if (controllerClass == PedirDatoController.class) {
    	                    PedirDatoController seleccion = new PedirDatoController();
    	                    seleccion.setDato(log, "Introduzca el texto para traduccion.");
    	                    return seleccion;
    	                }
    	                return null;
    	            });

    	            Parent root2 = loader2.load();

    	            Stage seleccionable2 = new Stage();
    	            seleccionable2.setTitle("Traduccion");

    	            seleccionable2.getIcons().add(icon);

    	            seleccionable2.setScene(new Scene(root2));

    	            // Configura la ventana secundaria como modal
    	            seleccionable2.initModality(Modality.APPLICATION_MODAL);

    	            // Configura el evento para establecer el tamaño mínimo
    	            seleccionable2.setOnShown(event1 -> {
    	            	seleccionable2.setMinWidth(seleccionable.getWidth());
    	            	seleccionable2.setMinHeight(seleccionable.getHeight());
    	            });

    			    // Evita que se pueda cambiar el tamaño de la ventana
    	            seleccionable2.setResizable(false);

    	            seleccionable2.showAndWait(); // Muestra la ventana y espera hasta que se cierre
    				
    				String Traducción = ((PedirDatoController)loader2.getController()).getDato();
    				
    				if(Traducción != null && !Traducción.isEmpty())
    				{
    					Traducción = Traducción.toUpperCase();
    					
    			    	Parametrizada nuevaParametrizada = new Parametrizada(
    			                tabla.getID_CONFIG(), tabla.getNOMBRE_TABLA(), columParam.getText().toUpperCase(), Dato, Traducción, tabla.getPESTAGNA()
    			    	);

    			        // Añade el nuevo objeto a la lista subyacente
    			        tablaParam.getRoot().getChildren().add(new TreeItem<>(nuevaParametrizada));
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
    	else
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debe rellenar primero el nombre de la columna a parametrizar.");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
    	}    	
    }

    @FXML
    void borrarTraducir(MouseEvent event) {
    	 // Obtiene la fila seleccionada
        TreeItem<Parametrizada> selectedItem = tablaParam.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Elimina la fila de la lista subyacente
            tablaParam.getRoot().getChildren().remove(selectedItem);
        }
    }

    @FXML
    void cancelar(MouseEvent event)
    {
    	Stage stage = (Stage) columParam.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardar(MouseEvent event)
    {
    	if(tablaParam.getRoot().getChildren().size() > 0)
    	{
    		String columnaParametrizada = columParam.getText();
    		
    		boolean esta = false;
    		for(int i = 0; i < parametrizadasConfiguradas.size(); i++)
    			if(columnaParametrizada.equalsIgnoreCase(parametrizadasConfiguradas.get(i)))
    				esta = true;
    		
    		if(!esta)
    		{
            	if(parametrizadasEdit == null)
            		parametrizadasEdit = new ArrayList<Parametrizada>();
            	
            	TreeItem<Parametrizada> root = tablaParam.getRoot();
                if (root != null) {
                    for (TreeItem<Parametrizada> child : root.getChildren()) {
                    	Parametrizada aux = child.getValue();
                    	aux.setCOLUMNA_PARAMETRIZADA(columParam.getText().trim());
                    	parametrizadasEdit.add(aux);
                    }
                }
                Stage stage = (Stage) columParam.getScene().getWindow();
                stage.close();
    		}
    		else
    		{
    			Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("La columna que intenta parametrizar ya se encuentra parametrizada, si desea añadir mas campos a esta editela.");
                // Agregar el icono personalizado
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
                alert.showAndWait();
    		}
    		
    	}
    	else
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Para poder guardar debe al menos configurar una parametrizacion.");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            alert.showAndWait();
    	}
    }
    
    public void setDato(Trazas log, Tabla tabla, ArrayList<Parametrizada> parametrizadasOri, ArrayList<String> parametrizadasConfiguradas)
    {
    	this.log = log;
    	this.tabla = tabla;
    	this.parametrizadasOri = parametrizadasOri;
    	this.parametrizadasConfiguradas = parametrizadasConfiguradas;
    	
    	if(parametrizadasOri != null && parametrizadasConfiguradas != null)
    	{
    		int indice = -1;
    		String nombreActual = parametrizadasOri.get(0).getCOLUMNA_PARAMETRIZADA();
    		for(int i = 0; i < parametrizadasConfiguradas.size(); i++)
    		{
    			if(nombreActual.equalsIgnoreCase(parametrizadasConfiguradas.get(i)))
    			{
    				indice = i;
    			}
    		}
    		
    		if(indice != -1)
    		{
    			parametrizadasConfiguradas.remove(indice);
    		}
    		
    	}
    }
    
    public ArrayList<Parametrizada> getDato()
    {
    	return parametrizadasEdit;
    }

    @FXML
    void initialize()
    {
    	// Configura la columna "DATO"
        JFXTreeTableColumn<Parametrizada, String> datoColumn = new JFXTreeTableColumn<>("Dato");
        datoColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Parametrizada, String> param) ->
            new SimpleStringProperty(param.getValue().getValue().getDATO()));
        
        // Configura la columna "TRADUCCION"
        JFXTreeTableColumn<Parametrizada, String> traduccionColumn = new JFXTreeTableColumn<>("Traducción");
        traduccionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Parametrizada, String> param) ->
            new SimpleStringProperty(param.getValue().getValue().getTRADUCCION()));

        // Añade las columnas a la tabla
        List<JFXTreeTableColumn<Parametrizada, ?>> columnas = new ArrayList<>();
        columnas.add(datoColumn);
        columnas.add(traduccionColumn);
        tablaParam.getColumns().setAll(columnas);

        // Configura el ancho de las columnas para que ocupen el 49% cada una
        datoColumn.prefWidthProperty().bind(tablaParam.widthProperty().multiply(0.485));
        traduccionColumn.prefWidthProperty().bind(tablaParam.widthProperty().multiply(0.485));

        if(parametrizadasOri == null)
        	parametrizadasOri = new ArrayList<Parametrizada>();
        else
        	columParam.setText(parametrizadasOri.get(0).getCOLUMNA_PARAMETRIZADA());        
        
        // Crea una lista de datos
        ObservableList<Parametrizada> parametros = FXCollections.observableArrayList(parametrizadasOri);

        // Crea un root y añade los datos a la tabla
        TreeItem<Parametrizada> root = new RecursiveTreeItem<>(parametros, RecursiveTreeObject::getChildren);
        root.setExpanded(true);

        // Establece el root en la tabla
        tablaParam.setRoot(root);
        tablaParam.setShowRoot(false);
    }
}
