package proyectologin;

public class Login {
	
	private String nombre_usuario;
	private String contrasenia;
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Login(String nombre_usuario, String contrasenia) {
		super();
		this.nombre_usuario = nombre_usuario;
		this.contrasenia = contrasenia;
	}



	public String getNombre_usuario() {
		return nombre_usuario;
	}
	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	

}
