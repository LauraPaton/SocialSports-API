package servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import seguridad.Secured;

import modelo.Evento;

@Path("/perfil")
public class PerfilUsuario {

	@GET
	@Path("/eventospendientes")
	//@Secured
	public List<Evento> eventosPendientes() {
		
		Evento evento = null;
		List<Evento> listaEventos = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			evento = new Evento();
			evento.setDeporte("Tenis" + String.valueOf(i));
			evento.setFechaEvento(new Date());
			evento.setCosteEvento(6);
			evento.setComentarios("Comentario prueba" + String.valueOf(i));
			evento.setIdEvento(1);
			
			listaEventos.add(evento);
		}
		
		return listaEventos;
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
