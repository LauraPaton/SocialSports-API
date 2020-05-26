package servicios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import dao.ImagenesDAO;
import seguridad.Secured;

import modelo.Evento;

@Path("/perfil")
public class Perfil {

	@GET
	@Path("/eventospendientes")
	@Produces({MediaType.APPLICATION_JSON})
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
			
			listaEventos.add(evento);
		}
		
		return listaEventos;
	}
	
	@GET
	@Path("/suscripcionespendientes")
	@Secured
	public List<Evento> suscripcionesPendientes() {
		return null;
	}
	
	@GET 
	@Path("/eventosrealizados")
	@Secured
	public List<Evento> eventosRealizados() {
		return null;
	}
	
	
	@GET 
	@Path("/datosusuario")
	@Secured
	@Produces({MediaType.APPLICATION_JSON})
	public Response datosUsuario() {
		return null;
	}
	
	@POST
	@Path("/uploadimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)  
    public Response uploadFile(  
            @FormDataParam("file") InputStream uploadedInputStream,  
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		Response.Status responseStatus = Status.UNAUTHORIZED;
		
		ImagenesDAO imagenesDAO = new ImagenesDAO();
		boolean subida = imagenesDAO.uploadImage(uploadedInputStream);
		
		if(subida) responseStatus = Status.OK;
		
		return Response
				.status(responseStatus)
				.build();

	}
	
	@GET
	@Path("/downloadimage")
	@Produces({"image/png", "image/jpeg", "image/jpg"})
	public Response downloadFile() {
		
		ImagenesDAO imagenesDAO = new ImagenesDAO();
		InputStream is = imagenesDAO.downloadImage();
		
		if(is != null) {
			return Response
					.status(Status.OK)
					.entity(is)
					.build();
		}
		
		return Response.status(Status.UNAUTHORIZED).build();
		
	}
}
