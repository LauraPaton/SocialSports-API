package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
				String sql = "insert into tablausuarios(EMAILUSUARIO, PASSWORDUSUARIO, USUARIOSALT, USUARIO, LISTAAMIGOS, LISTABLOQUEADOS) values(?,?,?,Tpersona(?,null,null,null,null,null,sysdate, 0, 0, null, 0), tlistapersonas(), tlistapersonas())";
				
				PreparedStatement insert = conn.getConnection().prepareStatement(sql);
				insert.setString(1, correo);
				insert.setString(2, hashedString);
				insert.setString(3, salt);
				insert.setInt(4, generarId());
				
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
	
	public int getId(String correo) {
		
		int id=0;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT T.USUARIO.ID_USUARIO FROM TABLAUSUARIOS T WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	private int generarId() {

		int id=1;

		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT T.USUARIO.ID_PERSONA FROM TABLAUSUARIOS T ORDER BY 1 DESC");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt(1)+1;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return id;
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
