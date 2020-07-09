package proyectologin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class MainProyectoLogin {
	
	private static void mostrarUsuario2 (ResultSet resultSet) throws SQLException
	{
		System.out.println("Nombre = " +resultSet.getString("nombre"));
		System.out.println("ID = " + resultSet.getInt("idusuarios"));
		System.out.println("Contrasenia  = "  +resultSet.getString("password"));
	}
	
	private static void mostrarUsuario1 (ResultSet resultSet) throws SQLException
	{
		System.out.println("Nombre = " +resultSet.getString(2));
		System.out.println("ID = " + resultSet.getInt(1));
		System.out.println("Contrasenia  = "  +resultSet.getString(3));
	}
	//TODO GENERAR UNA LISTA DE USUARIOS A PARTIR DE LA CONSULTA REALIZADA
	
	private static Usuario generarUsuario (ResultSet resultSet) throws SQLException
	{
		Usuario usuario = null;
		String nombre = null;
		String pass = null;
		int id = 0;
		
			nombre = resultSet.getString(2);
			id = resultSet.getInt(1);
			pass = resultSet.getString(3);
			usuario = new Usuario(id, nombre, pass);
			
		return usuario;
	}
	
	private static void mostrarListaUsuarios (List<Usuario> lUsuarios)
	{
		for (Usuario u : lUsuarios)
		{
			System.out.println(u.toString());
		}
		//System.out.println(lUsuarios);
	}
	

	public static void main(String[] args) {

		List<Usuario> lUsuarios = null;
		Logger logger = Logger.getLogger("mylog");
		Connection connection = null;
		Statement instruccion = null;// connection.createStatement();
		ResultSet resultSet = null;
		Usuario usuario_aux = null;

		// TODO Conectarnos a la base de datos y leer los usuarios
		try {
			lUsuarios = new ArrayList<Usuario>();
			// 1 REGISTRAR /cargar EL DRIVER
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			// 2 OBTENER LA CONEXIÓN
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hedima", "root", "nrmnct29");
			instruccion = connection.createStatement();
			//resultSet = instruccion.executeQuery(InstruccionesSelect.SELECCIONAR_TODOS_USUARIOS);
			resultSet = instruccion.executeQuery(InstruccionesSelect.SELECCIONAR_TODOS_USUARIOS_ORDEN_NOMBRE_DESC);
			while (resultSet.next())// mientras haya filas//registros//tuplas
			{
				//	todo MOSTRAR TODOS LOS CAMPOS DE LA TABLA 
				//System.out.println(resultSet.getString("nombre"));
				//mostrarUsuario1(resultSet);
				//mostrarUsuario2(resultSet);
				usuario_aux = generarUsuario(resultSet);
				lUsuarios.add(usuario_aux);
				
			}
			System.out.println("---mostrar lista ordeanda por nombre bd DESC ---");
			mostrarListaUsuarios(lUsuarios);
			Collections.sort(lUsuarios);
			System.out.println("---mostrar lista ordeanda por password ---");
			mostrarListaUsuarios(lUsuarios);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error al leeer de la base de datos", e);
		} finally 
		{
			try {
				resultSet.close();
				instruccion.close();
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("error al liberar recursos", e);
			}

		}

		/*
		 * Gson gson = new Gson(); Logger logger = Logger.getLogger("mylog");
		 * logger.debug("Usando Maven");
		 * 
		 * Login login = new Login("Pedro", "atitelovoydecir"); String login_json =
		 * gson.toJson(login); //serializar logger.debug("Usando GSON " + login_json);
		 */
		// TODO deserializar

	}
}
