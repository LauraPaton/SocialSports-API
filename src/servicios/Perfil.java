package servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import seguridad.Secured;

import modelo.Evento;

@Path("/perfil")
public class Perfil {

	@GET
	@Path("/eventospendientes")
	@Produces({MediaType.APPLICATION_JSON})
	//@Secured
	public Response eventosPendientes() {
		
		Evento evento = null;
		List<Evento> listaEventos = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			evento = new Evento();
			evento.setDeporte("Tenis" + String.valueOf(i));
			evento.setFechaEvento(new Date());
			evento.setCosteEvento(6);
			evento.setComentarios("Comentario prueba" + String.valueOf(i));
			
			listaEventos.add(evento);
		}
		
		return Response
				.status(Status.OK)
				.entity(listaEventos)
				.build();
	}
	
	@GET
	@Path("/suscripcionespendientes")
	@Secured
	public Response suscripcionesPendientes() {
		return null;
	}
	
	@GET 
	@Path("/eventosrealizados")
	@Secured
	public Response eventosRealizados() {
		return null;
	}
	
	
	@GET 
	@Path("/datosusuario")
	@Secured
	@Produces({MediaType.APPLICATION_JSON})
	public Response datosUsuario() {
		return null;
	}

}
