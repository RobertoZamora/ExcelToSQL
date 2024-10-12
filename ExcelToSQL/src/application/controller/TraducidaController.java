package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.modelo.Tabla;
import application.modelo.Traducida;
import application.trazas.Trazas;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TraducidaController
{
	private Trazas log;
	private Tabla tabla;
	private Traducida traducidaOri;
	private Traducida traducidaEdit;
	private ArrayList<String> traducidasConfiguradas;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField nameColExcel;

    @FXML
    private JFXTextField nameColBBDD;

    @FXML
    private JFXButton guardar;

    @FXML
    private JFXButton cancelar;

    @FXML
    void cancelar(MouseEvent event)
    {
    	Stage stage = (Stage) nameColExcel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardar(MouseEvent event)
    {
    	String mensajeError = "";
    	String columnaParametrizada = nameColExcel.getText();
		
		boolean esta = false;
		for(int i = 0; i < traducidasConfiguradas.size(); i++)
			if(columnaParametrizada.equalsIgnoreCase(traducidasConfiguradas.get(i)))
				esta = true;
		
		if(esta)
		{
			mensajeError += "\n La traducida que esta intentado crear ya existe, si desea modificarla acceda a ella y editela.";
		}
    	
    	if(nameColExcel.getText().trim().length() == 0
    			|| nameColBBDD.getText().trim().length() == 0)
    	{
    		if(mensajeError.length() > 0)
    			mensajeError += "\n";
    		mensajeError += "Para poder guardar debe rellenar ambos campos.";
    	}
    			
    	if(mensajeError.length() == 0)
    	{
    		if(traducidaEdit == null)
    			traducidaEdit = new Traducida();
    		
    		traducidaEdit.setID_CONFIG(tabla.getID_CONFIG());
    		traducidaEdit.setNOMBRE_TABLA(tabla.getNOMBRE_TABLA());
    		traducidaEdit.setPESTAGNA(tabla.getPESTAGNA());
    		traducidaEdit.setCOLUMNA_EXCEL(nameColExcel.getText().toUpperCase());
    		traducidaEdit.setCOLUMNA_BBDD(nameColBBDD.getText().toUpperCase());
    		
        	Stage stage = (Stage) nameColExcel.getScene().getWindow();
            stage.close();
    	}
    	else
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(mensajeError);
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));
            log.log(Level.INFO, mensajeError);
            alert.showAndWait();
    	}
    }
    
    public void setDato(Trazas log, Tabla tabla, Traducida traducidaOri, ArrayList<String> traducidasConfiguradas)
    {
    	this.log = log;
    	this.tabla = tabla;
    	this.traducidaOri = traducidaOri;
    	this.traducidasConfiguradas = traducidasConfiguradas;
    	
    	if(traducidaOri != null && traducidasConfiguradas != null)
    	{
    		int indice = -1;
    		String nombreActual = traducidaOri.getCOLUMNA_EXCEL();
    		for(int i = 0; i < traducidasConfiguradas.size(); i++)
    		{
    			if(nombreActual.equalsIgnoreCase(traducidasConfiguradas.get(i)))
    			{
    				indice = i;
    			}
    		}
    		
    		if(indice != -1)
    		{
    			this.traducidasConfiguradas.remove(indice);
    		}
    		
    	}
    	
    }
    
    public Traducida getDato()
    {
    	return traducidaEdit;
    }

    @FXML
    void initialize()
    {
    	if(traducidaOri != null)
    	{
    		nameColExcel.setText(traducidaOri.getCOLUMNA_EXCEL());
    		nameColBBDD.setText(traducidaOri.getCOLUMNA_BBDD());
    	}
    }
}
