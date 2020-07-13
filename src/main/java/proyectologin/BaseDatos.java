package proyectologin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class BaseDatos {

	/*private static final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/hedima?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USUARIO = "root";
	private static final String CONTRASENIA_BD = "venenoso";*/
	
	private static String CADENA_CONEXION;
	private static String USUARIO;
	private static String CONTRASENIA_BD ;

	// Esta sección es como para inicializar la clase y se puede usar cuando se
	// necesite. Las instrucciones que metamos aquí en la sección static se ejecutan
	// automáticamente cuando se crea una clase de tipo BaseDatos por primera vez.
	
	static {

		try {
			// TODO cargar las properties
			Properties properties = new Properties();
			properties.load(new FileReader("db.properties"));
			CADENA_CONEXION = properties.getProperty("cadenaconexion");
			USUARIO = properties.getProperty("usuariodb");
			CONTRASENIA_BD = properties.getProperty("passworddb");
			
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection obtenerConexion() throws SQLException {
		Connection connection = null;

		connection = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENIA_BD);

		return connection;
	}

	public void liberarRecursos(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void liberarRecursos(Connection connection, Statement statement) {
		try {
			statement.close();
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public List<Usuario> obtenerListaUsuarios() {

		List<Usuario> lu = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		Usuario usuario_aux = null;

		// 1 pillo conexión
		try {
			lu = new ArrayList<Usuario>();
			connection = this.obtenerConexion();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(InstruccionesSelect.SELECCIONAR_TODOS_USUARIOS);

			while (resultSet.next()) {
				// crear el usuario
				usuario_aux = new Usuario(resultSet);
				// add a la lista
				lu.add(usuario_aux);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			liberarRecursos(connection, statement, resultSet);
		}

		return lu;
	}

	/**
	 * Método que consulta el usuario de la base de datos
	 * 
	 * @param nombre el nombre buscado
	 * @param pwd    la password del usuario
	 * @return el usuario si existe o null si no lo encontró
	 * @throws SQLException
	 * 
	 */
	public Usuario login(String nombre, String pwd) throws SQLException {
		Usuario usuario = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id_user = 0;

		// tengo que hacer la consulta
		// 1 pillo conexión
		try {
			connection = this.obtenerConexion();
			ps = connection.prepareStatement(InstruccionesSelect.LOGIN_USUARIOS);
			ps.setString(1, nombre);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			if (rs.next()) {
				// Capturamos el id del usuario que ha hecho login
				id_user = rs.getInt(1);
				// System.out.println("id_user vale " + id_user);
				usuario = new Usuario(id_user, nombre, pwd);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			liberarRecursos(connection, ps, rs);
		}

		return usuario;
	}

	public boolean insertarUsuario(Usuario u) throws Exception {
		boolean usuario_creado = false;
		Connection connection = null;
		PreparedStatement ps = null;
		// ResultSet no es necesario en este caso, porque no estamos consultando nada
		// para utilizar posteriormente
		// ResultSet rs = null;

		try {
			connection = this.obtenerConexion();
			ps = connection.prepareStatement(InstruccionesSelect.INSERTAR_USUARIO);
			ps.setString(1, u.getNombre());
			ps.setString(2, u.getPwd());
			// Siempre que usamos un insert un update o un delete, usamos executeUpdate
			// executeUpdate devuelve 1 si ha afectado a la ddbb o 0 si no devuelve nada
			int nfilas = ps.executeUpdate();
			// hace lo mismo que un if preguntando si nfilas != 0
			usuario_creado = (nfilas != 0);

			// No consigo capturar en rs el update de la ddbb
			/*
			 * if (rs.next()) { usuario_creado = true; }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			liberarRecursos(connection, ps);
		}

		return usuario_creado;
	}

	public boolean borrarUsuario(int id) throws Exception {
		boolean usuario_eliminado = false;
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = this.obtenerConexion();
			ps = connection.prepareStatement(InstruccionesSelect.ELIMINA_USUARIO);
			ps.setInt(1, id);
			int eliminado = ps.executeUpdate();
			usuario_eliminado = (eliminado != 0);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			liberarRecursos(connection, ps);
		}
		return usuario_eliminado;
	}

	/**
	 * Método que busca los usuarios que empiecen por la cadena que reciba
	 * 
	 * @param nombre_buscado es el inicio del patrón de búsqueda
	 * @return una lista vacía si no se recupern resultados o la lista con usuarios
	 *         encontrados
	 * @throws Exception
	 */
	public List<Usuario> buscarUsuarioPorNombre(String nombre_buscado) throws Exception {
		List<Usuario> usuario = new ArrayList<Usuario>();
		Usuario usuario_aux = null;

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		System.out.println("nombre_buscado vale " + nombre_buscado);

		try {
			connection = this.obtenerConexion();
			ps = connection.prepareStatement(InstruccionesSelect.SELECCIONAR_USUARIOS_POR_NOMBRE);
			ps.setString(1, nombre_buscado + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				// crear el usuario
				usuario_aux = new Usuario(rs);
				// add a la lista
				usuario.add(usuario_aux);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		} finally {
			liberarRecursos(connection, ps, rs);
		}

		return usuario;
	}

}
