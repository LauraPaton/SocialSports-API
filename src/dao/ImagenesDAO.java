package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagenesDAO {
	
	public boolean uploadImage(InputStream is, String correo) {
		
		boolean subida = false;
		
		try {
			Conexion conn = new Conexion();
			
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET FOTOPERFILUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setBinaryStream(1, is);
			ps.setString(2, correo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) subida = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return subida;
	}
	
	public InputStream downloadImage(String correo) {
		
		InputStream is = null;
		
		try {
			Conexion conn = new Conexion();
			
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT FOTOPERFILUSUARIO FROM TABLAUSUARIOS WHERE EMAILUSUARIO = ?");
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				is = rs.getBinaryStream(1);
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return is;
	}
}
