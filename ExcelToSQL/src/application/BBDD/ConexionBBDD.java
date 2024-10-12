package application.BBDD;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Level;

import application.Main;
import application.trazas.Trazas;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ConexionBBDD {

	private Connection connection = null;
	private String driver = "";
	private String dbName = "";
	private String dbParam = "";
	private String connectionURL = "";
	private Trazas log;
	
	public ConexionBBDD(Trazas log)
	{
		super();
		this.log = log;
		
		driver = "org.apache.derby.jdbc.EmbeddedDriver";
		dbName = Main.rutaDatos + File.separator + "configuraciones/SQL";
		dbParam = "create=true"; // la base de datos se creará si no existe todavía
		connectionURL = "jdbc:derby:" + dbName + ";" + dbParam;
		
		String nuevaRutaLogsErrores = Main.rutaDatos + File.separator + "logs/derby.log";
				
		System.setProperty("derby.stream.error.file", nuevaRutaLogsErrores);
	}
	
	public void cargaInicial(ProgressBar progressBar, Text texto, int lineas)
	{
		File BBDD = new File(Main.rutaDatos + File.separator + "configuraciones/SQL");
		log.log(Level.DEBUG, "RUTA BBDD: " + BBDD.getAbsolutePath());
		if(!BBDD.exists())
		{
			int i = 0;
			log.log(Level.DEBUG, "La base de datos no existe procedemos a crearla.");
			connection = null;

			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(connectionURL);
								
				InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("recursos/DDL/CONFIGURACIONES.DDL");
				BufferedReader reader = null;
			        
		        try {
	            	Platform.runLater(() -> texto.setText("Creamos las tablas."));
		        	reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		            
		            String linea;
		            while((linea = reader.readLine())!=null)
		            {
			            log.log(Level.DEBUG, linea);
		            	String consulta = linea;
		            	actualizacion(consulta);
		            	final double progress = ((double) (i + 1) / lineas); // Incrementa la barra de progreso
	                    Platform.runLater(() -> progressBar.setProgress(progress));
		            	i++;
		            }
		            
		        } catch (NullPointerException e) {
		        	log.log(Level.ERROR, "No se ha seleccionado ningún fichero");
		        } catch (Exception e) {
		            log.log(Level.ERROR, e.getMessage());
		        }
		        finally
		        {
		        	try{                    
		                if( null != reader )
		                	reader.close();
		                
		             }catch (Exception e2){ 
		                e2.printStackTrace();
		             }
		        }
			} catch (java.lang.ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				log.log(Level.ERROR, e);
			} finally {
				try {
					connection.close();
				} catch (Throwable t) {
				}
			}
		}
	}
	
	public void conectar() {
		try {
			connection = DriverManager.getConnection(connectionURL);
			if (connection == null)
				throw new Exception("Error en la conexion a la BBDD");
			log.log(Level.DEBUG, "Conexión exitosa!");
			connection.setAutoCommit(false);
		} catch (Exception ex) {
			log.log(Level.ERROR, ex.getClass().getName() + ": " + ex.getMessage());
			log.log(Level.ERROR, "Error en la conexión");
		}
	}
	
	public void desconectar()
	{
		try {
			connection.commit();
			connection.close();
        } catch (SQLException ex) {
        	log.log(Level.ERROR, ex.getClass().getName() + ": " + ex.getMessage());
        	log.log(Level.ERROR, "Error en la conexión");
        }
	}
	
	private void actualizacion(String consulta) throws Exception
	{
		PreparedStatement ps = null;
		
		try {
			ps = prepareStatement(consulta);
			ps.execute();
			
			commit();			
		} catch (SQLException e) {
			try {
				rollback();
			} catch (SQLException e1) {
				log.log(Level.ERROR, e1.getMessage());	
				log.log(Level.ERROR, e1);
			}
			log.log(Level.ERROR, e.getMessage());	
			log.log(Level.ERROR, e);
			throw new Exception("Hubo un error durante la carga de la BBDD");
		}
		finally
		{
			try {
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				log.log(Level.ERROR, e.getMessage());	
				log.log(Level.ERROR, e);
			}
		}
	}
	
	public <T> ArrayList<T> recuperarDatos(String sql, ArrayList<String[]> parametros, Class<T> clazz) {
	    ArrayList<T> results = new ArrayList<>();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = connection.prepareStatement(sql);

	        if (parametros != null) {
	            for (int i = 0; i < parametros.size(); i++) {
	                String[] parametro = parametros.get(i);
	                String tipo = parametro[0];
	                switch (tipo) {
	                    case "string":
	                        ps.setString(i + 1, parametro[1]);
	                        break;
	                    case "int":
	                        ps.setInt(i + 1, Integer.parseInt(parametro[1]));
	                        break;
	                    case "double":
	                        ps.setDouble(i + 1, Double.parseDouble(parametro[1]));
	                        break;
	                    case "date":
	                        ps.setDate(i + 1, Date.valueOf(parametro[1]));
	                        break;
	                    default:
	                        System.err.println(tipo + " --> tipo no válido.");
	                }
	            }
	        }

	        rs = ps.executeQuery();
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int columnCount = rsmd.getColumnCount();

	        while (rs.next()) {
	            T instance = clazz.getDeclaredConstructor().newInstance();
	            for (int i = 1; i <= columnCount; i++) {
	                String columnName = rsmd.getColumnName(i);
	                Field field = clazz.getDeclaredField(columnName);
	                field.setAccessible(true);
	                Object value = rs.getObject(i);
	                if (value != null) {
	                    if (field.getType() == int.class) {
	                        field.set(instance, rs.getInt(i));
	                    } else if (field.getType() == double.class) {
	                        field.set(instance, rs.getDouble(i));
	                    } else if (field.getType() == float.class) {
	                        field.set(instance, rs.getFloat(i));
	                    } else if (field.getType() == long.class) {
	                        field.set(instance, rs.getLong(i));
	                    } else if (field.getType() == boolean.class) {
	                        field.set(instance, rs.getBoolean(i));
	                    } else if (field.getType() == String.class) {
	                        field.set(instance, rs.getString(i));
	                    } else if (field.getType() == Date.class) {
	                        field.set(instance, rs.getDate(i));
	                    } else if (field.getType() == Timestamp.class) {
	                        field.set(instance, rs.getTimestamp(i));
	                    } else {
	                        // Aquí puedes agregar más tipos según necesites
	                        field.set(instance, value);
	                    }
	                }
	            }
	            results.add(instance);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	    return results;
	}
	
	public <T> T recuperarDato(String sql, ArrayList<String[]> parametros, Class<T> clazz) {
	    T result = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        ps = connection.prepareStatement(sql);

	        if (parametros != null) {
	            for (int i = 0; i < parametros.size(); i++) {
	                String[] parametro = parametros.get(i);
	                String tipo = parametro[0];
	                switch (tipo) {
	                    case "string":
	                        ps.setString(i + 1, parametro[1]);
	                        break;
	                    case "int":
	                        ps.setInt(i + 1, Integer.parseInt(parametro[1]));
	                        break;
	                    case "double":
	                        ps.setDouble(i + 1, Double.parseDouble(parametro[1]));
	                        break;
	                    case "date":
	                        ps.setDate(i + 1, Date.valueOf(parametro[1]));
	                        break;
	                    default:
	                        System.err.println(tipo + " --> tipo no válido.");
	                }
	            }
	        }

	        rs = ps.executeQuery();
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int columnCount = rsmd.getColumnCount();

	        if (rs.next()) {
	            result = clazz.getDeclaredConstructor().newInstance();
	            for (int i = 1; i <= columnCount; i++) {
	                String columnName = rsmd.getColumnName(i);
	                Field field = clazz.getDeclaredField(columnName);
	                field.setAccessible(true);
	                Object value = rs.getObject(i);
	                if (value != null) {
	                    if (field.getType() == int.class) {
	                        field.setInt(result, rs.getInt(i));
	                    } else if (field.getType() == double.class) {
	                        field.setDouble(result, rs.getDouble(i));
	                    } else if (field.getType() == float.class) {
	                        field.setFloat(result, rs.getFloat(i));
	                    } else if (field.getType() == long.class) {
	                        field.setLong(result, rs.getLong(i));
	                    } else if (field.getType() == boolean.class) {
	                        field.setBoolean(result, rs.getBoolean(i));
	                    } else if (field.getType() == String.class) {
	                        field.set(result, rs.getString(i));
	                    } else if (field.getType() == Date.class) {
	                        field.set(result, rs.getDate(i));
	                    } else if (field.getType() == Timestamp.class) {
	                        field.set(result, rs.getTimestamp(i));
	                    } else {
	                        field.set(result, value);
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	    return result;
	}
	
	public int actualizarDato(String sql, ArrayList<String[]> parametros) {
        PreparedStatement ps = null;
        int affectedRows = 0;
        try {
            ps = connection.prepareStatement(sql);

            if (parametros != null) {
                for (int i = 0; i < parametros.size(); i++) {
                    String[] parametro = parametros.get(i);
                    String tipo = parametro[0];
                    switch (tipo) {
                        case "string":
                            ps.setString(i + 1, parametro[1]);
                            break;
                        case "double":
                            ps.setDouble(i + 1, Double.parseDouble(parametro[1]));
                            break;
                        case "date":
                            ps.setDate(i + 1, Date.valueOf(parametro[1]));
                            break;
                        case "int":
                            ps.setInt(i + 1, Integer.parseInt(parametro[1]));
                            break;
                        case "long":
                            ps.setLong(i + 1, Long.parseLong(parametro[1]));
                            break;
                        case "boolean":
                            ps.setBoolean(i + 1, Boolean.parseBoolean(parametro[1]));
                            break;
                        default:
                            System.err.println(tipo + " --> tipo no válido.");
                    }
                }
            }

            affectedRows = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return affectedRows;
    }
	
	public <T> boolean insertarDato(String tabla, T entidad) {
	    PreparedStatement ps = null;
	    try {
	        // Obtener los campos y valores de la entidad
	        Field[] campos = entidad.getClass().getDeclaredFields();
	        StringBuilder sql = new StringBuilder("INSERT INTO ");
	        sql.append(tabla).append(" (");

	        // Agregar los nombres de los campos a la consulta SQL
	        for (Field campo : campos) {
	            campo.setAccessible(true);
	            sql.append(campo.getName()).append(",");
	        }
	        sql.deleteCharAt(sql.length() - 1); // Eliminar la última coma
	        sql.append(") VALUES (");

	        // Agregar los placeholders para los valores a la consulta SQL
	        for (int i = 0; i < campos.length; i++) {
	            sql.append("?,");
	        }
	        sql.deleteCharAt(sql.length() - 1); // Eliminar la última coma
	        sql.append(")");

	        // Preparar la consulta
	        ps = connection.prepareStatement(sql.toString());

	        // Establecer los valores en el PreparedStatement
	        for (int i = 0; i < campos.length; i++) {
	            Field campo = campos[i];
	            Object valor = campo.get(entidad);
	            ps.setObject(i + 1, valor);
	        }

	        // Ejecutar la consulta de inserción
	        int filasInsertadas = ps.executeUpdate();
	        return filasInsertadas > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}
	
	public int contarRegistros(String tabla) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        // Preparar la consulta SQL para contar los registros
	        String sql = "SELECT COUNT(*) FROM " + tabla;
	        ps = connection.prepareStatement(sql);

	        // Ejecutar la consulta
	        rs = ps.executeQuery();

	        // Obtener el resultado
	        if (rs.next()) {
	            return rs.getInt(1);
	        } else {
	            return 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        if (rs != null) {
	            try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}

	public boolean eliminarDato(String tabla, ArrayList<String[]> parametros) {
	    PreparedStatement ps = null;
	    try {
	        // Construir la consulta SQL de eliminación
	        StringBuilder sql = new StringBuilder("DELETE FROM ");
	        sql.append(tabla).append(" WHERE ");

	        // Agregar las condiciones basadas en los parámetros
	        for (int i = 0; i < parametros.size(); i++) {
	            String[] parametro = parametros.get(i);
	            String campo = parametro[0];
	            sql.append(campo).append(" = ? AND ");
	        }
	        sql.delete(sql.length() - 5, sql.length()); // Eliminar el último " AND "

	        // Preparar la consulta
	        ps = connection.prepareStatement(sql.toString());

	        // Establecer los valores en el PreparedStatement
	        for (int i = 0; i < parametros.size(); i++) {
	            String[] parametro = parametros.get(i);
	            String tipo = parametro[1];
	            String valor = parametro[2];
	            switch (tipo) {
	                case "string":
	                    ps.setString(i + 1, valor);
	                    break;
	                case "double":
	                    ps.setDouble(i + 1, Double.parseDouble(valor));
	                    break;
	                case "date":
	                    ps.setDate(i + 1, Date.valueOf(valor));
	                    break;
	                case "int":
	                    ps.setInt(i + 1, Integer.parseInt(valor));
	                    break;
	                case "long":
	                    ps.setLong(i + 1, Long.parseLong(valor));
	                    break;
	                case "boolean":
	                    ps.setBoolean(i + 1, Boolean.parseBoolean(valor));
	                    break;
	                default:
	                    System.err.println(tipo + " --> tipo no válido.");
	            }
	        }

	        // Ejecutar la consulta de eliminación
	        int filasEliminadas = ps.executeUpdate();
	        return filasEliminadas > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (ps != null) {
	            try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
	        }
	    }
	}
	
	public PreparedStatement prepareStatement(String sentencia) throws SQLException
	{
		return connection.prepareStatement(sentencia);
	}
	
	public void rollback() throws SQLException
	{
		connection.rollback();
	}
	
	public void commit() throws SQLException
	{
		connection.commit();
	}
}
