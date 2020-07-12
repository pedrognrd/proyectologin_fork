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
import java.util.Scanner;

import org.apache.log4j.Logger;

import sun.util.logging.resources.logging;

public class MainProyectoLogin {

	private Logger logger = Logger.getLogger("mylog");

	private static void mostrarUsuario2(ResultSet resultSet) throws SQLException {
		System.out.println("Nombre = " + resultSet.getString("nombre"));
		System.out.println("ID = " + resultSet.getInt("idusuarios"));
		System.out.println("Contrasenia  = " + resultSet.getString("password"));
	}

	private static void mostrarUsuario1(ResultSet resultSet) throws SQLException {
		System.out.println("Nombre = " + resultSet.getString(2));
		System.out.println("ID = " + resultSet.getInt(1));
		System.out.println("Contrasenia  = " + resultSet.getString(3));
	}
	// TODO GENERAR UNA LISTA DE USUARIOS A PARTIR DE LA CONSULTA REALIZADA

	private static Usuario generarUsuario(ResultSet resultSet) throws SQLException {
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

	private static void mostrarListaUsuarios(List<Usuario> lUsuarios) {
		for (Usuario u : lUsuarios) {
			System.out.println(u.toString());
		}
		// System.out.println(lUsuarios);
	}

	public static int pedirId() {
		int id = 0;
		Scanner scaner = null;

		System.out.println("Intro id:");
		scaner = new Scanner(System.in);
		id = scaner.nextInt();

		return id;
	}
	
	public static String pedirNombre() {
		String nombre = null;
		Scanner scaner = null;

		System.out.println("Intro nombre:");
		scaner = new Scanner(System.in);
		nombre = scaner.next();

		return nombre;
	}

	public static String pedirPwd() {
		String pwd = null;
		Scanner scaner = null;

		System.out.println("Introduce pwd:");
		scaner = new Scanner(System.in);
		pwd = scaner.next();

		return pwd;
	}

	private static void mostrarMenu() {

		System.out.println("1. CONSULTAR TODOS LOS USUARIOS");
		System.out.println("2. LOGIN USUARIO");
		System.out.println("3. INSERTAR UN USUARIO NUEVO");
		System.out.println("4. BORRAR UN USUARIO POR SU ID");
		System.out.println("5. SALIR.");
		System.out.println("");// para que nos deje un salto en blanco
		System.out.println("Introduzca opción: ");

	}

	/**
	 * Método que lee la opción introducida por teclado
	 * 
	 * @return el número leído
	 */
	private static int leerOpcion() {
		int opcion_introducida = 0;
		Scanner scanner = null;// variable que necesito para leer de teclado

		scanner = new Scanner(System.in);//
		opcion_introducida = scanner.nextInt();// suponemos que introduce un número. no valildamos! (si mete una letra
												// petaría y el programa acabaría de forma abrupta)

		return opcion_introducida;
	}

	public static void main(String[] args) throws Exception {

		int opcion;

		// Al crear la clase, se ejecuta automáticamente el código "static{}" de
		// BaseDatos.java
		BaseDatos baseDatos = new BaseDatos();

		do {

			// 1 mostramos el menú
			mostrarMenu();
			// 2 leerOpcion
			opcion = leerOpcion();
			// 3 casos: hacemos los caminos para cada elección del usuario
			switch (opcion) {
			case 1: // TOD CONSULTAR TODOS LOS USUARIOS
				// Haremos u so del método
				List<Usuario> lu = baseDatos.obtenerListaUsuarios();
				for (Usuario u : lu) {
					System.out.println(u);
				}
				System.out.println("\n");
				break;

			case 2: // LOGIN
				String nombre = pedirNombre();
				String pwd = pedirPwd();
				try {
					Usuario u = baseDatos.login(nombre, pwd);

					if (u != null) {
						System.out.println(u);
					} else {
						System.out.println("Usuario incorrecto: nombre o pwd erróneo");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;
			case 3: // TODO INSERTAR
				boolean insertar_usuario = false;
				String nombre_nuevo = pedirNombre();
				String pwd_nuevo = pedirPwd();
				Usuario usuario_nuevo = new Usuario(nombre_nuevo, pwd_nuevo);
				
				try {
					insertar_usuario = baseDatos.insertarUsuario(usuario_nuevo);
					System.out.println("usuario_nuevo vale " + usuario_nuevo);
					
					if (insertar_usuario) {
						System.out.println("Usuario creado con éxito\n");
					} else {
						System.out.println("Error al crear usuario\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				break;
			case 4: // TODO BORRAR USUARIO POR ID
				int id_elimina = 0;
				boolean eliminar_usuario = false;
				List<Usuario> lu2 = baseDatos.obtenerListaUsuarios();
				for (Usuario u : lu2) {
					System.out.println(u);
				}
				System.out.println("Elija el ID del usuario a eliminar\n");
				id_elimina = pedirId();
				System.out.println("id_elimina vale " + id_elimina);
				
				try {
					eliminar_usuario = baseDatos.borrarUsuario(id_elimina);					
					if (eliminar_usuario) {
						System.out.println("Usuario eliminado con éxito\n");
					} else {
						System.out.println("Error al eliminar usuario\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 5: // SALIR

				System.out.println("Quiere salir");

				break;

			default:
				System.out.println("Opción no disponible");
				break;
			}

		} while (opcion != 7);

	}
}
