package application.controller;

import com.jfoenix.controls.JFXButton;

import application.trazas.Trazas;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfirmarController {
	private Trazas log;
	private String texto;
	private boolean dato = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label mensaje;

    @FXML
    private JFXButton aceptar;

    @FXML
    private JFXButton cancelar;

    @FXML
    void aceptar(MouseEvent event) {
    	dato = true;
    	Stage stage = (Stage) mensaje.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelar(MouseEvent event) {
    	dato = false;
    	Stage stage = (Stage) mensaje.getScene().getWindow();
        stage.close();
    }
    
    public void setDato(Trazas log, String texto)
    {
    	this.texto = texto;
    	this.log = log;
    }
    
    public boolean getDato()
    {
    	return dato;
    }

    @FXML
    void initialize() {
    	mensaje.setText(texto);
    	log.log(Level.INFO, "Mensaje --> " + mensaje);
        assert mensaje != null : "fx:id=\"mensaje\" was not injected: check your FXML file 'Confirmar.fxml'.";
        assert aceptar != null : "fx:id=\"aceptar\" was not injected: check your FXML file 'Confirmar.fxml'.";
        assert cancelar != null : "fx:id=\"cancelar\" was not injected: check your FXML file 'Confirmar.fxml'.";

    }
}
