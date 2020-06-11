package servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	
	//@Secured
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
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarEvento() {
		return null;
	}
	
	@Secured
	@POST
	@Path("/unirse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unirseEvento() {
		return null;
	}
	
}
