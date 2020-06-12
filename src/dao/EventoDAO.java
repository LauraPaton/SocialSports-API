package dao;

import java.util.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import modelo.Evento;
import modelo.Requisitos;
import modelo.Usuario;

public class EventoDAO {
	
	public boolean crearEvento(Evento evento) {
		
		boolean creado = false;
		Conexion conn;
		
		try {
			conn = new Conexion();
			
			String refSql = "select ref(u) from tablausuarios u where emailusuario = ?";
			PreparedStatement refPs = conn.getConnection().prepareStatement(refSql);
			refPs.setString(1, evento.getOrganizadorEvento().getEmailUsuario());
			ResultSet refRs = refPs.executeQuery();
			java.sql.Ref ref = null;
			
			if(refRs.next()) {
				ref = refRs.getRef(1);
			}
			
			String sql = "insert into tablaeventos(idEvento, organizadorEvento, deporte, localidad, direccion, fechaEvento, "
					+ "horaEvento, fechaCreacionEvento, maximoParticipantes, instalacionesReservadas, costeEvento, precioPorParticipante,"
					+ "comentarios, terminado, requisitos, listaSolicitantes, listaDescartados, listaParticipantes) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?, TRequisito(?,?,?,?), TLISTAPERSONAS(), TLISTAPERSONAS(), TLISTAPERSONAS())";
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, evento.getIdEvento());
			ps.setRef(2, ref);
			ps.setString(3, evento.getDeporte());
			ps.setString(4, evento.getLocalidad());
			ps.setString(5, evento.getDireccion());
			if(evento.getFechaEvento() != null) {
				ps.setDate(6, new java.sql.Date(StringToDate(evento.getFechaEvento()).getTime()));  
			}else {
				ps.setDate(6, null);
			}
			ps.setString(7, evento.getHoraEvento());
			ps.setDate(8, new java.sql.Date(StringToDate(evento.getFechaCreacionEvento()).getTime()));
			ps.setInt(9, evento.getMaximoParticipantes());
			ps.setBoolean(10, evento.isInstalacionesReservadas());
			ps.setFloat(11, evento.getCosteEvento());
			ps.setFloat(12, evento.getPrecioPorParticipante());
			ps.setString(13, evento.getComentarios());
			ps.setBoolean(14, evento.isTerminado());
			ps.setInt(15, evento.getRequisitos().getEdadMinima());
			ps.setInt(16, evento.getRequisitos().getEdadMaxima());
			ps.setString(17, evento.getRequisitos().getRequisitoDeGenero());
			ps.setFloat(18, evento.getRequisitos().getReputacionNecesaria());
			
			int n = ps.executeUpdate();
			
			if(n > 0) creado = true;

			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return creado;
	}
	
	public ArrayList<Evento> obtenerEventosPendientes(String correo) {
		
		ArrayList<Evento> listaEventos = new ArrayList<>();
		
		try {
			Conexion conn = new Conexion();
			
			String sqlEventos = "SELECT IDEVENTO FROM TABLAEVENTOS WHERE TERMINADO = 0";
			
			String sql = "select deref(a.COLUMN_VALUE).emailusuario "
					+ "from table(select listaParticipantes from tablaeventos where idevento = ?) a "
					+ "where ? in deref(a.COLUMN_VALUE).emailusuario";
			
			PreparedStatement psEventos = conn.getConnection().prepareStatement(sqlEventos);
			ResultSet rsEventos = psEventos.executeQuery();
			
			while(rsEventos.next()) {
				String id = rsEventos.getString(1);
				
				PreparedStatement ps = conn.getConnection().prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					listaEventos.add(getEvento(id));
				}
				
				rs.close();
				ps.close();
				
			}
			
			rsEventos.close();
			psEventos.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaEventos;
	}
	
	public ArrayList<Evento> obtenerEventosFinalizados(String correo){
		ArrayList<Evento> listaEventos = new ArrayList<>();
		
		try {
			Conexion conn = new Conexion();
			
			String sqlEventos = "SELECT IDEVENTO FROM TABLAEVENTOS WHERE TERMINADO = 1";
			
			String sql = "select deref(a.COLUMN_VALUE).emailusuario "
					+ "from table(select listaParticipantes from tablaeventos where idevento = ?) a "
					+ "where ? in deref(a.COLUMN_VALUE).emailusuario";
			
			PreparedStatement psEventos = conn.getConnection().prepareStatement(sqlEventos);
			ResultSet rsEventos = psEventos.executeQuery();
			
			while(rsEventos.next()) {
				String id = rsEventos.getString(1);
				
				PreparedStatement ps = conn.getConnection().prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, correo);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					listaEventos.add(getEvento(id));
				}
				
				rs.close();
				ps.close();
				
			}
			
			rsEventos.close();
			psEventos.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaEventos;
	}
	
	public Evento getEvento(String id) {
		
		Conexion conn;
		Evento evento = new Evento();
		
		try {
			conn = new Conexion();	
			PreparedStatement psEventoPendiente = conn.getConnection().prepareStatement("SELECT * FROM TABLAEVENTOS WHERE IDEVENTO = ?");
			psEventoPendiente.setString(1, id);
			ResultSet rsEventoPendiente = psEventoPendiente.executeQuery();
			
			if(rsEventoPendiente.next()) {
				evento.setIdEvento(id);
				evento.setDeporte(rsEventoPendiente.getString("DEPORTE"));
				evento.setDireccion(rsEventoPendiente.getString("DIRECCION"));
				Date fechaCreacion = rsEventoPendiente.getDate("FECHACREACIONEVENTO");
				if(fechaCreacion != null) evento.setFechaCreacionEvento(fechaCreacion.toString());
				Date fechaEvento = rsEventoPendiente.getDate("FECHAEVENTO");
				if(fechaEvento != null) evento.setFechaEvento(fechaEvento.toString());
				evento.setHoraEvento(rsEventoPendiente.getString("HORAEVENTO"));
				evento.setInstalacionesReservadas(rsEventoPendiente.getBoolean("INSTALACIONESRESERVADAS"));
				evento.setLocalidad(rsEventoPendiente.getString("LOCALIDAD"));
				evento.setTerminado(rsEventoPendiente.getBoolean("TERMINADO"));
				evento.setPrecioPorParticipante(rsEventoPendiente.getFloat("PRECIOPORPARTICIPANTE"));
				evento.setMaximoParticipantes(rsEventoPendiente.getInt("MAXIMOPARTICIPANTES"));
				evento.setCosteEvento(rsEventoPendiente.getFloat("COSTEEVENTO"));
				evento.setComentarios(rsEventoPendiente.getString("COMENTARIOS"));
				
				evento.setOrganizadorEvento(getOrganizador(id));
				setRequisitos(evento, id);
				evento.setListaParticipantes(getParticipantesEvento(id));
				evento.setListaDescartados(getSolicitantesEvento(id));
				evento.setListaSolicitantes(getDescartadosEvento(id));
			}
			
			rsEventoPendiente.close();
			psEventoPendiente.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return evento;
	}
	
	public ArrayList<Usuario> getParticipantesEvento(String idEvento) {
		
		ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select deref(a.COLUMN_VALUE).emailusuario from table(select listaparticipantes from tablaeventos where idevento = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, idEvento);
			ResultSet rs = ps.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while(rs.next()) {
				Usuario usuario = usuarioDAO.cogerUsuario(rs.getString(1));
				listaParticipantes.add(usuario);
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaParticipantes;
	}
	
	public ArrayList<Usuario> getSolicitantesEvento(String idEvento) {
ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select deref(a.COLUMN_VALUE).emailusuario from table(select listasolicitantes from tablaeventos where idevento = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, idEvento);
			ResultSet rs = ps.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while(rs.next()) {
				Usuario usuario = usuarioDAO.cogerUsuario(rs.getString(1));
				listaParticipantes.add(usuario);
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaParticipantes;
	}
	
	public ArrayList<Usuario> getDescartadosEvento(String idEvento) {
		ArrayList<Usuario> listaParticipantes = new ArrayList<Usuario>();
		
		try {
			Conexion conn = new Conexion();
			String SQL = "select deref(a.COLUMN_VALUE).emailusuario from table(select listadescartados from tablaeventos where idevento = ?) a";
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ps.setString(1, idEvento);
			ResultSet rs = ps.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while(rs.next()) {
				Usuario usuario = new Usuario();
				String email = rs.getString(1);
				if(email != null) {
					usuario = usuarioDAO.cogerUsuario(email);
					listaParticipantes.add(usuario);
				}
			}
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaParticipantes;
	}
	
	public void setRequisitos(Evento evento, String idEvento) {
		
		Conexion conn;
		
		try {
			conn = new Conexion();
			String req = "select r.requisitos.edadminima, r.requisitos.edadmaxima, r.requisitos.requisitodegenero, r.requisitos.reputacionnecesaria from tablaeventos r where idevento = ?";
			PreparedStatement psReq = conn.getConnection().prepareStatement(req);
			psReq.setString(1, idEvento);
			ResultSet rsReq = psReq.executeQuery();
			
			if(rsReq.next()) {
				Requisitos requisitos = new Requisitos();
				requisitos.setEdadMinima(rsReq.getInt(1));
				requisitos.setEdadMaxima(rsReq.getInt(2));
				requisitos.setRequisitoDeGenero(rsReq.getString(3));
				requisitos.setReputacionNecesaria(rsReq.getFloat(4));
				evento.setRequisitos(requisitos);
			}
			
			rsReq.close();
			psReq.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Usuario getOrganizador(String idEvento) {
		
		Usuario organizador = null;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement psOrganizador = conn.getConnection().prepareStatement("SELECT deref(organizadorevento).emailusuario FROM TABLAEVENTOS WHERE IDEVENTO = ?");
			psOrganizador.setString(1, idEvento);
			ResultSet rsOrganizador = psOrganizador.executeQuery();
			
			if(rsOrganizador.next()) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				organizador = usuarioDAO.cogerUsuario(rsOrganizador.getObject(1).toString());
			}
			
			rsOrganizador.close();
			psOrganizador.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return organizador;
	}
	
	public boolean actualizarFecha(String idEvento, Date fecha) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET FECHAEVENTO = ? WHERE IDEVENTO = ?");
			ps.setDate(1, new java.sql.Date(fecha.getTime()));
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarHora(String idEvento, String hora) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET HORAEVENTO = ? WHERE IDEVENTO = ?");
			ps.setString(1,  hora);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarDireccion(String idEvento, String direccion) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET DIRECCION = ? WHERE IDEVENTO = ?");
			ps.setString(1,  direccion);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarMaxParticipantes(String idEvento, int maxParticipantes) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET MAXIMOPARTICIPANTES = ? WHERE IDEVENTO = ?");
			ps.setInt(1,  maxParticipantes);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean actualizarTerminarEvento(String idEvento, int terminado) {
		Conexion conn = null;
		boolean actualizado = false;
		
		try {
			conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("UPDATE TABLAEVENTOS SET TERMINADO = ? WHERE IDEVENTO = ?");
			ps.setInt(1,  terminado);
			ps.setString(2, idEvento);
			
			int n = ps.executeUpdate();
			
			if(n > 0) actualizado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	public boolean eliminarParticipante(String idEvento, String correo) {
		boolean eliminado = false;
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement("");
			
			
			int n = ps.executeUpdate();
			
			if(n > 0) eliminado = true;
			
			ps.close();
			conn.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return eliminado;
	}
	
	public void meterParticipantes(String idEvento, String correo) {
		try {
			Conexion conn = new Conexion();
			String sql = "insert into table(select listaParticipantes from tablaeventos where idevento = ?)(select ref(u) from tablausuarios u where u.emailusuario = ?)";
			PreparedStatement ps = null;
			
			ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, idEvento);
			ps.setString(2, correo);
			
			ps.executeUpdate();
			
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void enviarInvitaciones(Evento evento) {
		
		if(evento.getListaParticipantes() != null) {
			for(int i = 0; i < evento.getListaParticipantes().size(); i++) {
				meterParticipantes(evento.getIdEvento(), evento.getListaParticipantes().get(i).getEmailUsuario());
			}
		}
		
	}
	
	public ArrayList<Evento> buscarEventoFiltrado(String deporte, String localidad, Date fecha, String hora, boolean reservado, float reputacion){
		
		ArrayList<Evento> listaEventos = new ArrayList<>();
		
		String SQL = "select idevento from tablaeventos where terminado = 0";
		
		if(deporte != null && !deporte.equals("")) {
			SQL += " and deporte = '" + deporte + "'";
		}
		if(localidad != null && !localidad.equals("")) {
			SQL += " and localidad = '" + localidad + "'";
		}
		if(fecha != null) {
			SQL += " and fechaevento = '" + fecha + "'";
		}
		if(hora != null && !hora.equals("")) {
			SQL += " and horaevento = '" + hora + "'"; 
		}
		if(reservado) {
			SQL += " and instalacionesReservadas = 1";
		}
		if(reputacion != -1.0) {
			SQL += " and deref(organizadorevento).reputacionorganizadorusuario >= '" + reputacion + "'";
		}
		
		System.out.println(SQL);
		
		try {
			Conexion conn = new Conexion();
			PreparedStatement ps = conn.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String idEvento = rs.getString(1);
				Evento evento = getEvento(idEvento);
				listaEventos.add(evento);
			}
			
			rs.close();
			ps.close();
			conn.closeConnection();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return listaEventos;
	}
	
	
	public Date StringToDate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (fecha!=null && !fecha.isEmpty()) {
            try {
            	Date date = (Date) formato.parse(fecha);
            	System.out.println(fecha+" --- "+date.toString());
                return date;
            } catch (ParseException e) {
            	e.printStackTrace();
            }
        }
       return null;
	}
}
