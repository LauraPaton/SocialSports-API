package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Usuario;
import seguridad.PasswordHash;
import seguridad.Validaciones;

public class UsuarioDAO {
	
	public boolean existeCorreo(String correo) {
		
		boolean existe = false;
		Conexion conn = null;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT EMAILUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) existe = true;
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return existe;
	}
	
	public boolean loginUsuario(String correo, String contrasena) {
		
		Conexion conn = null;
		boolean valido = false;
		Validaciones validaciones = new Validaciones();
		
		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
		
			try {
				conn = new Conexion();
				PreparedStatement ps = conn.getConnection().prepareStatement("SELECT EMAILUSUARIO, PASSWORDUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
				ps.setString(1, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
				
					PasswordHash hash = new PasswordHash();
					String salt = getSalt(correo);
					hash.generatePassword(contrasena, salt);
			        String hashedString = hash.getHash();
			        
					if(hashedString.equals(rs.getObject(2))) {
						valido = true;
					}
				}
				rs.close();
				ps.close();
				conn.closeConnection();
			
			
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
			
		return valido;
	}
	
	public boolean actualizarNombre(String correo, String nombre) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET NOMBREUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, nombre);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarApellidos(String correo, String apellidos) {
		
		Conexion conn = null;
		boolean actualizado = false;
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET APELLIDOSUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, apellidos);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarDireccion(String correo, String direccion) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET DIRECCIONUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, direccion);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return actualizado;
	}
	
	public boolean actualizarGenero(String correo, String genero) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET GENEROUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setString(1, genero);
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean borrarUsuario(String correo) {
		
		Conexion conn = null;
		boolean borrado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("DELETE FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) borrado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return borrado;
	}
	
	public boolean registroUsuario(String correo, String contrasena) {
		
		Conexion conn = null;
		boolean registrado = false;
		
		try {
			conn = new Conexion();
			
			if(!existeCorreo(correo)) {
				PasswordHash hash = new PasswordHash();
				String salt = hash.generateSalt();
				hash.generatePassword(contrasena, salt);
		        String hashedString = hash.getHash();
				PreparedStatement insert = conn.getConnection().prepareStatement("INSERT INTO TABLAUSUARIOS (EMAILUSUARIO, PASSWORDUSUARIO, USUARIOSALT, FECHAALTAUSUARIO, LISTAAMIGOS, LISTABLOQUEADOS) values(?,?,?,?, TLISTAPERSONAS(), TLISTAPERSONAS())");
				insert.setString(1, correo);
				insert.setString(2, hashedString);
				insert.setString(3, salt);
				insert.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				
				int affectedRows = insert.executeUpdate();
				
				if(affectedRows==1) {
					registrado = true;
				}
				
				insert.close();
			}
			
			conn.closeConnection();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return registrado;
	}
	
	public Usuario cogerUsuario(String correo){
		
		Conexion conn = null;
		Usuario usuario = new Usuario();
		
		try {
			conn = new Conexion();
			String sql = "SELECT * FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				usuario.setEmailUsuario(rs.getNString("EMAILUSUARIO"));
				usuario.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
				if(usuario.getNombreUsuario() == null) usuario.setNombreUsuario("");
				usuario.setApellidosUsuario(rs.getString("APELLIDOSUSUARIO"));
				usuario.setGeneroUsuario(rs.getString("GENEROUSUARIO"));
				if(usuario.getGeneroUsuario() == null) usuario.setGeneroUsuario("");
				usuario.setDireccionUsuario(rs.getString("DIRECCIONUSUARIO"));
				usuario.setFechaNacimientoUsuario(rs.getDate("FECHANACIMIENTOUSUARIO"));
				usuario.setFechaAltaUsuario(rs.getDate("FECHAALTAUSUARIO"));
				usuario.setReputacionParticipanteUsuario(rs.getFloat("REPUTACIONPARTICIPANTEUSUARIO"));
				usuario.setReputacionOrganizadorUsuario(rs.getFloat("REPUTACIONORGANIZADORUSUARIO"));
				//imagen
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	public ArrayList<Usuario> listaAmigos(String correo) {
		
		Conexion conn = null;
		ArrayList<Usuario> listaAmigos = new ArrayList<>();
		
		try {
			conn = new Conexion();
			String sql = "select deref(usuario).EMAILUSUARIO,"
					+ "deref(usuario).NOMBREUSUARIO,"
					+ "deref(usuario).APELLIDOSUSUARIO,"
					+ "deref(usuario).GENEROUSUARIO"
					+ " from the(select listaamigos from tablausuarios where emailusuario = ?)";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			Usuario amigo;
			
			while(rs.next()) {
				
				amigo = new Usuario();
				
				amigo.setEmailUsuario(rs.getString(1));
				amigo.setNombreUsuario(rs.getString(2));
				amigo.setApellidosUsuario(rs.getString(3));
				amigo.setGeneroUsuario(rs.getString(4));
				
				listaAmigos.add(amigo);
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaAmigos;
	}
	
	
	public ArrayList<String> cogerCorreos() {
		
		Conexion conn = null;
		ArrayList<String> listaCorreos = new ArrayList<>();
		
		try {
			conn = new Conexion();
			String sql = "SELECT EMAILUSUARIO FROM TABLAUSUARIOS";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				listaCorreos.add(rs.getString(1));
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaCorreos;
	}
	
	private String getSalt(String correo) {
		
		Conexion conn = null;
		String salt = "";
	
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT USUARIOSALT FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				salt = rs.getString(1);
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return salt;
	}

}
