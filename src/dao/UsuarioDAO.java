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
		
		try {
			Conexion conn = new Conexion();
			String sql = "SELECT EMAILUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
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
		
		boolean valido = false;
		Validaciones validaciones = new Validaciones();
		
		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
			try {
				Conexion conexion = new Conexion();
				String sql = "SELECT EMAILUSUARIO, PASSWORDUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?";
				PreparedStatement ps = conexion.getConnection().prepareStatement(sql);
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
				conexion.closeConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return valido;
	}
	
	public boolean registroUsuario(String correo, String contrasena) {
		boolean registrado = false;
		Conexion conn;
		
		try {
			conn = new Conexion();
			
			if(!existeCorreo(correo)) {
				PasswordHash hash = new PasswordHash();
				String salt = hash.generateSalt();
				hash.generatePassword(contrasena, salt);
		        String hashedString = hash.getHash();
				String sql = "INSERT INTO TABLAUSUARIOS (EMAILUSUARIO, PASSWORDUSUARIO, USUARIOSALT, LISTAAMIGOS, LISTABLOQUEADOS) values(?,?,?, TLISTAPERSONAS(), TLISTAPERSONAS())";
				PreparedStatement insert = conn.getConnection().prepareStatement(sql);
				insert.setString(1, correo);
				insert.setString(2, hashedString);
				insert.setString(3, salt);
				
				int affectedRows = insert.executeUpdate();
				
				if(affectedRows==1) {
					registrado = true;
				}
			}
			
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return registrado;
	}
	
	public Usuario cogerUsuario(String correo) {
		Usuario usuario = new Usuario();
		try {
			Conexion conn = new Conexion();
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
	
	public ArrayList<String> cogerCorreos(){
		
		ArrayList<String> listaCorreos = new ArrayList<>();
		
		Conexion conn;
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
		
		String salt = "";
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT USUARIOSALT FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				salt = rs.getString(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return salt;
	}

}
