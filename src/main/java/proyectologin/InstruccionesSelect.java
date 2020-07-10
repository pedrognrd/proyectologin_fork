package proyectologin;

public class InstruccionesSelect {
	
	public final static  String SELECCIONAR_TODOS_USUARIOS= "SELECT * FROM usuarios";
	public final static  String SELECCIONAR_TODOS_USUARIOS_ORDEN_NOMBRE_DESC= "SELECT * FROM hedima.usuarios ORDER BY usuarios.nombre DESC;";
	public final static  String SELECCIONAR_USUARIOS_POR_ID= "SELECT * FROM hedima.usuarios WHERE idusuarios = ?;";
	public final static  String SELECCIONAR_USUARIOS_POR_ID_Y_NOMBRE= "SELECT * FROM hedima.usuarios WHERE idusuarios = ? AND nombre = ?;";

}
