package dao;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImagenesDAO {
	
	public boolean uploadImage(InputStream is) {
		
		boolean subida = false;
		
		try {
			Conexion conn = new Conexion();
			
			PreparedStatement ps = conn.getConnection().prepareStatement("INSERT INTO IMAGENES VALUES(?,?)");
			ps.setInt(1, 1);
			ps.setBinaryStream (2, is);
			int n = ps.executeUpdate();
			
			if(n > 0) subida = true;
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return subida;
	}
	
	public InputStream downloadImage() {
		
		InputStream is = null;
		
		try {
			Conexion conn = new Conexion();
			
			PreparedStatement ps = conn.getConnection().prepareStatement("SELECT IMAGEN FROM IMAGENES WHERE ID = 1");
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				is = rs.getBinaryStream(1); 
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return is;
	}
}
