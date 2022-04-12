package modelo.negocio;

import java.util.List;

import modelo.entidad.Usuario;
import modelo.persistencia.DaoUsuarioMySQL;
import modelo.persistencia.interfaces.DaoUsuario;

public class GestorUsuario {
	private DaoUsuario daoUsuario = new DaoUsuarioMySQL();

	public int alta(Usuario u) {
		if (u.getUser() == null || u.getPass() == null) {
			return 0; // en caso de que falten los parametros user y pass
		} else if (u.getUser().equals(daoUsuario.buscarPorUsuario(u.getUser()).getUser())) {
			return 1; // en caso de que el usuario ya exista
		} else {
			boolean alta = daoUsuario.alta(u);
			if (alta) {
				return 2;
			} else {
				return 3;
			}
		}
	}

	public Usuario buscarPorUsuario(String usuario) {
		return daoUsuario.buscarPorUsuario(usuario);
	}

	public boolean login(String user, String pass) {
		if(daoUsuario.buscarPorUsuario(user).getUser().equals(user) && daoUsuario.buscarPorUsuario(user).getPass().equals(pass)) {
			return true;
		}else {
			return false;
		}
	}

	public List<Usuario> listar() {
		return daoUsuario.listar();
	}
}
