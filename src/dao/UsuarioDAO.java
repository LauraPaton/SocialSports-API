package dao;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			conn.getConnection().commit();
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
			conn.getConnection().commit();
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
			conn.getConnection().commit();
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
			conn.getConnection().commit();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarFechaNacimiento(String correo, Date fecha) {
		
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAUSUARIOS SET FECHANACIMIENTOUSUARIO = ? WHERE EMAILUSUARIO = ?");
			ps.setDate(1, new java.sql.Date(fecha.getTime()));
			ps.setString(2, correo);
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.getConnection().commit();
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
			conn.getConnection().commit();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return borrado;
	}
	
	public boolean agregarAmigo(String correo, String correoAmigo) {
		
		boolean agregado = false;
		
		try {
			Conexion conn = new Conexion();
			String SQL = "insert into "
					+ "table(select listaamigos from tablausuarios where emailusuario = ?)"
					+ "(select ref(u) from tablausuarios u where u.emailusuario = ?)";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, correo);
			ps.setString(2, correoAmigo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) agregado = true;
			
			ps.close();
			conn.getConnection().commit();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return agregado;
	}
	
	public boolean borrarAmigo(String correo, String correoAmigo) {
		
		boolean borrado = false;
		
		try {
			Conexion conn = new Conexion();
			String SQL = "delete "
					+ "from table(select listaamigos from tablausuarios where emailusuario = ?) a"
					+ "where deref(a.COLUMN_VALUE).emailusuario = ?";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, correo);
			ps.setString(2, correoAmigo);
			
			int n = ps.executeUpdate();
			
			if(n > 0) borrado = true;
			
			ps.close();
			conn.getConnection().commit();
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
				usuario.setPasswordUsuario(null);
				usuario.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
				usuario.setApellidosUsuario(rs.getString("APELLIDOSUSUARIO"));
				usuario.setGeneroUsuario(rs.getString("GENEROUSUARIO"));
				usuario.setDireccionUsuario(rs.getString("DIRECCIONUSUARIO"));
				java.util.Date fechaNacimiento = rs.getDate("FECHANACIMIENTOUSUARIO");
				if(fechaNacimiento != null) usuario.setFechaNacimientoUsuario(fechaNacimiento.toString());
				java.util.Date fechaAlta = rs.getDate("FECHAALTAUSUARIO");
				if(fechaAlta != null) usuario.setFechaAltaUsuario(fechaAlta.toString());
				usuario.setReputacionParticipanteUsuario(rs.getFloat("REPUTACIONPARTICIPANTEUSUARIO"));
				usuario.setReputacionOrganizadorUsuario(rs.getFloat("REPUTACIONORGANIZADORUSUARIO"));
				usuario.setFotoPerfilUsuario(null);
				usuario.setListaAmigos(listaAmigos(correo));
				usuario.setListaBloqueados(new ArrayList<>());
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
			String sql = "select deref(a.COLUMN_VALUE).EMAILUSUARIO,"
					+ "deref(a.COLUMN_VALUE).NOMBREUSUARIO,"
					+ "deref(a.COLUMN_VALUE).APELLIDOSUSUARIO,"
					+ "deref(a.COLUMN_VALUE).GENEROUSUARIO,"
					+ "deref(a.COLUMN_VALUE).FECHANACIMIENTOUSUARIO"
					+ " from table(select u.listaamigos from tablausuarios u where u.emailusuario = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, correo);
			ResultSet rs = ps.executeQuery();
			
			Usuario amigo;
			
			while(rs.next()) {
				
				amigo = new Usuario();
				
				Object objectEmail = rs.getObject(1);
				if(objectEmail != null) amigo.setEmailUsuario(objectEmail.toString());
				else amigo.setEmailUsuario(null);
				
				Object objectNombre = rs.getObject(2);
				if(objectNombre != null) amigo.setNombreUsuario(objectNombre.toString());
				else amigo.setNombreUsuario(null);
				
				Object objectApellidos = rs.getObject(3);
				if(objectApellidos != null) amigo.setApellidosUsuario(objectApellidos.toString());
				else amigo.setApellidosUsuario(null);
				
				Object objectGenero = rs.getObject(4);
				if(objectGenero != null) amigo.setGeneroUsuario(objectGenero.toString());
				else amigo.setGeneroUsuario(null);
				
				Object objectFecha = rs.getObject(5);
				if(objectFecha != null) amigo.setFechaNacimientoUsuario(objectFecha.toString());
				else amigo.setFechaNacimientoUsuario(null);
				
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
	
	public Date StringToDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fecha!=null && !fecha.isEmpty()) {
            try {
            	Date date = formato.parse(fecha);
            	System.out.println(fecha+" --- "+date.toString());
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
