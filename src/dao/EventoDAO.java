package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EventoDAO {
	
	public static void actualizarTerminarEvento(String idEvento, boolean terminado){
		
		Conexion connection;
		
		try{
			connection = new Conexion();
			
			String sql = "UPDATE tablaeventos SET terminado = ? WHERE idEvento = ?";
			
			PreparedStatement ps = connection.getConnection().prepareStatement(sql);
			
			ps.setBoolean(1, terminado);
			ps.setString(2, idEvento);
			
			ps.close();
			connection.closeConnection();
			
		}catch (ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean usuarioHaPuntuadoEvento(String email, String idEvento){
		
		boolean calificado = false;
		Conexion connection;
		
		try{
			connection = new Conexion();
			PreparedStatement sentencia = connection.getConnection().prepareStatement("SELECT calificacion FROM tablaPuntuacionesEventos WHERE (emailUsuarioEmisor = ?)"
					+ "AND (idEventoFinalizado = ?)");
			
			sentencia.setString(1, email);
			sentencia.setString(2, idEvento);
			
			ResultSet resultset = sentencia.executeQuery();
				
			if(resultset.next()){
				calificado = true;
			}
			
			resultset.close();
			sentencia.close();
			connection.closeConnection();
		}
		
		catch (ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
		
		return calificado;
	}
}
