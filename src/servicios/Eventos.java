package servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.EventoDAO;
import modelo.Evento;
import seguridad.Secured;

@Path("/eventos")
public class Eventos {
	
	private EventoDAO eventoDAO;

	@Secured
	@POST
	@Path("/crear")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearEvento(Evento evento) {

		eventoDAO = new EventoDAO();
		System.out.println(evento.toString());
		
		if(eventoDAO.crearEvento(evento)) {
			//PROVISIONAL
			//Se metería en los participantes tanto al creador si fuese participante como a los usuarios que haya invitado
			eventoDAO.enviarInvitaciones(evento); 
			
			return Response.status(Status.CREATED).entity(evento).build();
		}
		
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@Secured
	@GET
	@Path("/pendientes/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eventosPendientes(@PathParam("correo") String correo) {
		eventoDAO = new EventoDAO();
		ArrayList<Evento> listaEventos = new ArrayList<>();
		listaEventos = eventoDAO.obtenerEventosPendientes(correo);
		return Response.status(Status.OK).entity(listaEventos).build();
	}
	
	@Secured
	@GET
	@Path("/finalizados/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eventosFinalizados(@PathParam("correo") String correo) {
		eventoDAO = new EventoDAO();
		ArrayList<Evento> listaEventos = new ArrayList<>();
		listaEventos = eventoDAO.obtenerEventosFinalizados(correo);
		return Response.status(Status.OK).entity(listaEventos).build();
	}
	
	@Secured
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEventos(@QueryParam("deporte") String deporte, @QueryParam("localidad") String localidad, @QueryParam("fecha") String fecha,
			@QueryParam("hora") String hora, @QueryParam("reservado") boolean reservado, @QueryParam("reputacion") float reputacion) {
		
		if(deporte == null && localidad == null && fecha == null && hora == null && reservado == false && reputacion == -1.0) {
			return Response.status(Status.BAD_REQUEST).build();
		}else if(deporte.equals("") && localidad.equals("") && fecha.equals("") && hora.equals("") && reservado == false && reputacion == -1.0) {
			return Response.status(Status.BAD_REQUEST).build();
		}else {
			eventoDAO = new EventoDAO();
			ArrayList<Evento> listaEventos = eventoDAO.buscarEventoFiltrado(deporte, localidad, eventoDAO.StringToDate(fecha), hora, reservado, reputacion);
			return Response.status(Status.OK).entity(listaEventos).build();
		}
		
	}
	
	/******DATOS EVENTO******/
	
	@Secured
	@PUT
	@Path("/fecha")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarFecha(@FormParam("idEvento") String idEvento, @FormParam("fecha") String fecha) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarFecha(idEvento, eventoDAO.StringToDate(fecha));
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/hora")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarHora(@FormParam("idEvento") String idEvento, @FormParam("hora") String hora) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarHora(idEvento, hora);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/direccion")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarDireccion(@FormParam("idEvento") String idEvento, @FormParam("direccion") String direccion) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarDireccion(idEvento, direccion);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/maximoparticipantes")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarMaxParticipantes(@FormParam("idEvento") String idEvento, @FormParam("maxParticipantes") int maxParticipantes) {
		eventoDAO = new EventoDAO();

		boolean actualizado = eventoDAO.actualizarMaxParticipantes(idEvento, maxParticipantes);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@PUT
	@Path("/terminarevento")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response actualizarTerminarEvento(@FormParam("idEvento") String idEvento, @FormParam("terminado") boolean terminado) {
		eventoDAO = new EventoDAO();
		int n = 0;
		if(terminado) n = 1;
		
		boolean actualizado = eventoDAO.actualizarTerminarEvento(idEvento, n);
		if(actualizado) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
