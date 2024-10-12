package application.util;

import java.io.File;
import java.io.PrintWriter;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FicheroSQL {
	private PrintWriter pw = null;
	
	public FicheroSQL (Stage stage)
	{
		FileChooser fileChooser = new FileChooser();
	    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL Files", "*.sql"));
	    
	    File file = fileChooser.showSaveDialog(stage);
	    
	    if (file != null) {
	        String ruta = file.getAbsolutePath();
	        if (!ruta.endsWith(".sql")) {
	            ruta += ".sql";
	            file = new File(ruta);
	        }
	        
	        if (file.exists()) {
	            boolean overwrite = Util.showConfirmationDialog("El fichero ya existe. Este se sobreescribirá. ¿Está seguro?");
	            if (!overwrite) {
	                return;
	            }
	        }
	        
	        try {
	        	this.pw = new PrintWriter(ruta, "UTF-8");
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }		
	}
	
	public void escribirTraza(String traza)
	{
		this.pw.println(traza);
		this.pw.flush();
	}
	
	/**
	 * Metodo que libera el objeto PrintWriter.
	 */
	public void liberar()
	{
		if(this.pw != null) 
		{
			this.pw.flush();
			this.pw.close();
		}
	}
}
