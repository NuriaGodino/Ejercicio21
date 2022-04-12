package modelo.persistencia.interfaces;

import java.util.List;

import modelo.entidad.Usuario;

public interface DaoUsuario {
	
	/**
	 * @author nuria.godino
	 * Metodo que da de alta a un usuario en la BBDD. 
	 * @param u, el usuario a dar de alta
	 * @return true en caso de que se haya dado de alta. false en caso de error
	 */
	boolean alta(Usuario u);
	
	/**
	 * @author nuria.godino
	 * Metodo que busca un usuario en la BBDD segun el user
	 * @param user del usuario a buscar
	 * @return el usuario que se esta buscando
	 */
	Usuario buscarPorUsuario(String user);
	
	/**
	 * @author nuria.godino
	 * @return Te devuelve una lista de los usuarios que hay en la BBDD
	 */
	List<Usuario> listar();
}
