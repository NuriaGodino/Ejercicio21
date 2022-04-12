package modelo.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Usuario;
import modelo.persistencia.interfaces.DaoUsuario;

public class DaoUsuarioMySQL implements DaoUsuario{
	
	private Connection conexion;
	
	public boolean abrirConexion() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/loginUsuarios";
		String usuario = "root";
		String password = "";
		
		try {
			conexion = DriverManager.getConnection(url, usuario, password);
		}catch(SQLException e) {
			System.out.println("No se ha podido establecer conexion con la BD");
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean cerrarConexion() {
		try {
			conexion.close();
		}catch(SQLException e) {
			System.out.println("No se ha podido establer la conexion con la BD");
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean alta(Usuario u) {
		try {
			if(!abrirConexion()) {
				return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean alta = true;
		String query = "insert into usuarios (ID, USER, PASS) " + " values(?,?,?)";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, u.getId());
			ps.setString(2, u.getUser());
			ps.setString(3, u.getPass());
			
			int numFilasAfectadas = ps.executeUpdate();
			if(numFilasAfectadas == 0) {
				alta = false;
			}
		}catch(SQLException e) {
			System.out.println("Alta --> Error al insertar: " + u);
			alta = false;
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return alta;
	}

	@Override
	public Usuario buscarPorUsuario(String usuario) {
		try {
			if(!abrirConexion()) {
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Usuario u = new Usuario();
		String query = "select ID, USER, PASS from usuarios " + "where USER = ?";
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, usuario);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				u.setId(rs.getInt(1));
				u.setUser(rs.getString(2));
				u.setPass(rs.getString(3));
			}
		}catch(SQLException e) {
			System.out.println("No se encontro el usuario con el user: " + usuario);
			e.printStackTrace();
		}finally {
			cerrarConexion();
		}
		return u;
	}

	@Override
	public List<Usuario> listar() {
		try {
			if(!abrirConexion()) {
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		String query = "select ID, USER, PASS from usuarios";
		Usuario u = new Usuario();
		
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				u.setId(rs.getInt(1));
				u.setUser(rs.getString(2));
				u.setPass(rs.getString(3));
				
				listaUsuarios.add(u);
			}
		}catch(SQLException e) {
			System.out.println("Listar --> Error al obtener los usuarios");
			System.out.println(e.getMessage());
		}finally {
			cerrarConexion();
		}
		return listaUsuarios;
	}
}
