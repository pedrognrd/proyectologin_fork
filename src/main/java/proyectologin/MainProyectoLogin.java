package proyectologin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		BaseDatos baseDatos = null;
		PreparedStatement preparedStatement = null;
		
		/**
		 * PreparedStatement pstmt =
conn.prepareStatement("Select * from
employees where employee_id = ?");
pstmt.setInt(1, 110);
rset = pstmt.executeQuery();
		 */
		
		try {
			lUsuarios = new ArrayList<Usuario>();
			// 1 REGISTRAR /cargar EL DRIVER
			baseDatos = new BaseDatos();
			baseDatos.registrarDriver();
			// 2 OBTENER LA CONEXIÓN
			connection = baseDatos.obtenerConexion();
			
			//preparedStatement = connection.prepareStatement(InstruccionesSelect.SELECCIONAR_USUARIOS_POR_ID);
			preparedStatement = connection.prepareStatement(InstruccionesSelect.SELECCIONAR_USUARIOS_POR_ID_Y_NOMBRE);
			int id_usuario = 5;//pedir vosotros el id al usuario de nuestro programa
			preparedStatement.setInt(1, id_usuario);
			preparedStatement.setString(2, "pedro");
			
			//TODO haced el login/ la autenticación de la aplicacion
			//pedir al usuario su nombre y contraseña y decid si está regustrado en la base de datos o no
			//si está registrado mostrais su id, su nombre y su contraseña
			//si no, mostrais un mensaje de Acceso denegado
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				mostrarUsuario1 (resultSet);
				
			} else {
				System.out.println("LA CONSULTA NO RECUPERÓ RESULTADOS");
			}
			
		/*	
			//instruccion = connection.createStatement();
			
			//resultSet = instruccion.executeQuery(InstruccionesSelect.SELECCIONAR_TODOS_USUARIOS);
			resultSet = instruccion.executeQuery(InstruccionesSelect.SELECCIONAR_TODOS_USUARIOS_ORDEN_NOMBRE_DESC);
			while (resultSet.next())// mientras haya filas//registros//tuplas
			{
				
				usuario_aux = generarUsuario(resultSet);
				lUsuarios.add(usuario_aux);
				
			}
			System.out.println("---mostrar lista ordeanda por nombre bd DESC ---");
			mostrarListaUsuarios(lUsuarios);
			Collections.sort(lUsuarios);
			System.out.println("---mostrar lista ordeanda por password ---");
			mostrarListaUsuarios(lUsuarios);
*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error al leeer de la base de datos", e);
		} finally 
		{
			//baseDatos.liberarRecursos(connection, instruccion, resultSet);
			baseDatos.liberarRecursos(connection, preparedStatement, resultSet);

		}

		

	}
}
