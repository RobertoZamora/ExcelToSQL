package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import application.trazas.Trazas;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PedirDatoController {
	private Trazas log;
	private String mensajeParam;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label mensaje;

    @FXML
    private JFXTextField texto;

    @FXML
    private JFXButton aceptar;

    @FXML
    private JFXButton cancelar;

    @FXML
    void aceptar(MouseEvent event) {
    	if(!texto.getText().isEmpty())
    	{
    		Stage stage = (Stage) texto.getScene().getWindow();
        	stage.close();
    	}
    	else
    	{
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("INFO");
            alert.setContentText("Debe rellenar el campo de texto.");
            // Agregar el icono personalizado
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/recursos/img/SQL.png")));

            alert.showAndWait();
            log.log(Level.INFO, "Debe rellenar el campo de texto.");
    	}
    }

    @FXML
    void cancelar(MouseEvent event) {
    	Stage stage = (Stage) texto.getScene().getWindow();
        stage.close();
    }
    
    public void setDato(Trazas log, String mensajeParam)
    {
    	this.log = log;
    	this.mensajeParam = mensajeParam;
    }
    
    public String getDato()
    {
    	return texto.getText();
    }

    @FXML
    void initialize() {
    	mensaje.setText(mensajeParam);
    	texto.setText("");

    }
}
