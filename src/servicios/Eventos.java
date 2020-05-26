package servicios;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.Conexion;
import dao.EventoDAO;
import modelo.Evento;
import seguridad.Secured;

@Path("/eventos")
public class Eventos {

	@Secured
	@POST
	@Path("/crear")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crearEvento(Evento evento) {
		
		Response.Status responseStatus = Status.UNAUTHORIZED;
		
		//if(todo es valido){
		
			EventoDAO eventoDAO = new EventoDAO();
			boolean creado = eventoDAO.crearEvento(evento);
			
			if(creado) {
				responseStatus = Status.CREATED;
			}
			
		//}
			
		return Response
				.status(responseStatus)
				.build();
		
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
