package dao;

import java.sql.Date;
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
				PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT CORREO, CONTRASENA FROM USUARIOS WHERE CORREO = ?");
				ps.setString(1, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
				
					PasswordHash hash = new PasswordHash(contrasena);
			        String hashedString = hash.getHash();
			        
			        //System.out.println(hashedString);
			        
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
			PreparedStatement sentencia = connection.getConnection().prepareStatement("SELECT * FROM USUARIOS WHERE CORREO = ?");
			sentencia.setString(1, correo);
			
			ResultSet resultset = sentencia.executeQuery();
				
			if(!resultset.next()) {
				PasswordHash hash = new PasswordHash(contrasena);
		        String hashedString = hash.getHash();
		        
		        System.out.println(hashedString);
				
				String sql = "INSERT INTO USUARIOS(ID, CORREO, CONTRASENA, REPUTACIONORGANIZADOR, REPUTACIONPARTICIPANTE, ALTA) VALUES(?,?,?,?,?,?)";
				PreparedStatement insert = connection.getConnection().prepareStatement(sql);
				insert.setLong(1, generarId());
				insert.setString(2, correo); 
				insert.setString(3, hashedString);
				insert.setInt(4, 0);
				insert.setInt(5, 0);
				insert.setDate(6, new Date(new java.util.Date().getTime()));
				
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
	
	private long generarId() {
		
		long id=1;
		
		try {
			Conexion conexion = new Conexion();
			PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT ID FROM USUARIOS ORDER BY 1 DESC");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getLong(1)+1;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public long getId(String correo) {
		long id=0;
		try {
			Conexion conexion = new Conexion();
			PreparedStatement ps = conexion.getConnection().prepareStatement("SELECT ID FROM USUARIOS WHERE CORREO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id=(long)rs.getObject(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
