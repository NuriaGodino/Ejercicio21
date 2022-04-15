package controlador;

import java.io.IOException;
import java.rmi.ServerException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import modelo.negocio.GestorUsuario;

@WebServlet("/usuarios/login")
public class Servlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServerException, IOException {
		GestorUsuario gu = new GestorUsuario();
		JSONObject json = new JSONObject();

		String user = request.getParameter("user"); //los parametros que metemos en la query
		String pass = request.getParameter("pass");
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		if(gu.login(user, pass)) {
			json.put("validado", true); //introducimos el valor
			response.getWriter().write(json.toString()); //Escribimos la respuesta
		}else {
			json.put("validado", false);
			response.getWriter().write(json.toString());
		}
		
		response.setContentType("application/JSON;charset=UTF-8");

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServerException, IOException{
		doGet(request, response);
	}
}
