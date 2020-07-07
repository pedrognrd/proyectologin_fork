package proyectologin;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class MainProyectoLogin {

	public static void main(String[] args) {
		
		Gson gson = new Gson();
		Logger logger = Logger.getLogger("mylog");
		logger.debug("Usando Maven");
		
		Login login = new Login("Pedro", "atitelovoydecir");
		String login_json = gson.toJson(login); //serializar
		logger.debug("Usando GSON " + login_json);
		//TODO deserializar
		
	}
}
