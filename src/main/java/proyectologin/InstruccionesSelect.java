package proyectologin;

public class InstruccionesSelect {
	
	public final static  String SELECCIONAR_TODOS_USUARIOS= "SELECT * FROM usuarios";
	public final static  String SELECCIONAR_TODOS_USUARIOS_ORDEN_NOMBRE_DESC= "SELECT * FROM hedima.usuarios ORDER BY usuarios.nombre DESC;";
	public final static  String SELECCIONAR_USUARIOS_POR_ID= "SELECT * FROM hedima.usuarios WHERE idusuarios = ?;";
	public final static  String SELECCIONAR_USUARIOS_POR_ID_Y_NOMBRE= "SELECT * FROM hedima.usuarios WHERE idusuarios = ? AND nombre = ?;";
	public final static  String LOGIN_USUARIOS = "SELECT * FROM hedima.usuarios WHERE nombre = ? AND password = ?;";
	public final static  String INSERTAR_USUARIO = "INSERT INTO hedima.usuarios(nombre, password) VALUES(?,?)";
	public final static  String ELIMINA_USUARIO = "DELETE FROM hedima.usuarios WHERE id = ?;";
}
