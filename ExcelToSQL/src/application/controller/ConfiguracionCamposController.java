package application.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import application.BBDD.ConexionBBDD;
import application.constantes.Constantes;
import application.modelo.CamposTipo;
import application.trazas.Trazas;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfiguracionCamposController
{
	private Trazas log;
	private CamposTipo camposTipoOri;
	private CamposTipo camposTipoEdit;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField campo;

    @FXML
    private JFXComboBox<String> tipo;

    @FXML
    private JFXButton aceptar;

    @FXML
    private JFXButton cancelar;

    @FXML
    void aceptar(MouseEvent event)
    {
    	if(campo.getText().trim().length() > 0)
    	{
    		camposTipoEdit = new CamposTipo();
    		String tipoConfigurado = tipo.getSelectionModel().getSelectedItem();
    		camposTipoEdit.setID_CONFIG(camposTipoOri.getID_CONFIG());
    		camposTipoEdit.setNOMBRE_TABLA(camposTipoOri.getNOMBRE_TABLA());
    		camposTipoEdit.setCAMPO(campo.getText().trim());
    		camposTipoEdit.setTIPO(tipoConfigurado);
    		
    		ConexionBBDD conn = new ConexionBBDD(log);
    		conn.conectar();
    		
    		ArrayList<String[]> paramDelTabla = new ArrayList<String[]>();
	    	
        	// parametro 1
    		String[] auxParam = new String[3];
    		auxParam[0] = "ID_CONFIG";
    		auxParam[1] = Constantes.getSqlTipo(Constantes.SQL_INT);
    		auxParam[2] = String.valueOf(camposTipoOri.getID_CONFIG());
    		paramDelTabla.add(auxParam);
    		
    		// parametro 2
    		String[] auxParam2 = new String[3];
    		auxParam2[0] = "NOMBRE_TABLA";
    		auxParam2[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    		auxParam2[2] = String.valueOf(camposTipoOri.getNOMBRE_TABLA());
    		paramDelTabla.add(auxParam2);
    		
    		// parametro 3
    		String[] auxParam3 = new String[3];
    		auxParam3[0] = "CAMPO";
    		auxParam3[1] = Constantes.getSqlTipo(Constantes.SQL_STRING);
    		auxParam3[2] = String.valueOf(camposTipoOri.getCAMPO());
    		paramDelTabla.add(auxParam3);    		

    		conn.eliminarDato(Constantes.CAMPOSTIPO, paramDelTabla);
    		
    		conn.insertarDato(Constantes.CAMPOSTIPO, camposTipoEdit);
    		
    		conn.desconectar();
    		
    		cancelar(event);
    	}
    }

    @FXML
    void cancelar(MouseEvent event)
    {
    	Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }
    
    public void setDato(Trazas log, CamposTipo camposTipo)
    {
    	this.log = log;
    	this.camposTipoOri = camposTipo;
    }
    
    public CamposTipo getDato()
    {
    	return camposTipoEdit;
    }

    @FXML
    void initialize()
    {
    	campo.setText(camposTipoOri.getCAMPO() != null && !"".equals(camposTipoOri.getCAMPO().trim()) ?camposTipoOri.getCAMPO():"");
    	String tipoAux = camposTipoOri.getTIPO() != null && !"".equals(camposTipoOri.getTIPO().trim())?camposTipoOri.getTIPO():"string";
    	
    	String tipos[] = {"string", "double", "int"};
        ArrayList<String> pkList = new ArrayList<>(Arrays.asList(tipos));
        
        tipo.getItems().addAll(pkList);
        tipo.setValue(tipoAux);
    }
}
