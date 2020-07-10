package proyectologin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {
	
	private static final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/hedima";
	private static final String USUARIO = "root";
	private static final String CONTRASENIA_BD = "nrmnct29";
	
	public void registrarDriver () throws SQLException
	{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	}
	
	
	public Connection obtenerConexion () throws SQLException
	{
		Connection connection = null;
		
			connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENIA_BD);
		
		return connection;
	}
	
	public void liberarRecursos (Connection connection, Statement statement, ResultSet resultSet)
	{
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/*liberarRecursos ()*/
	
	
	

}
