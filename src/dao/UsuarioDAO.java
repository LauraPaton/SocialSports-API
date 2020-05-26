package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import seguridad.PasswordHash;
import seguridad.Validaciones;

public class UsuarioDAO {
	
	public boolean usuarioValido(String correo, String contrasena) {
		
		boolean valido = false;
		Validaciones validaciones = new Validaciones();
		
		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
			try {
				Conexion conexion = new Conexion();
				PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT CORREO, CONTRASENA FROM USUARIOSPRUEBA WHERE CORREO = ?");
				ps.setString(1, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
				
					PasswordHash hash = new PasswordHash(contrasena);
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
		Conexion connection;
		
		try {
			connection = new Conexion();
			PreparedStatement sentencia = connection.getConnection().prepareStatement("SELECT * FROM USUARIOSPRUEBA WHERE CORREO = ?");
			sentencia.setString(1, correo);
			
			ResultSet resultset = sentencia.executeQuery();
				
			if(!resultset.next()) {
				PasswordHash hash = new PasswordHash(contrasena);
		        String hashedString = hash.getHash();
				
				String sql = "INSERT INTO USUARIOSPRUEBA(CORREO, CONTRASENA) VALUES(?,?)";
				PreparedStatement insert = connection.getConnection().prepareStatement(sql);
				insert.setString(1, correo);
				insert.setString(2, hashedString);
				
				int affectedRows = insert.executeUpdate();
				
				if(affectedRows==1) {
					registrado = true;
				}
			}
			
			resultset.close();
			sentencia.close();
			connection.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return registrado;
	}

}
