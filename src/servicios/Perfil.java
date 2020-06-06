package servicios;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import dao.ImagenesDAO;
import dao.UsuarioDAO;
import seguridad.Secured;

import modelo.Evento;
import modelo.Usuario;

@Path("/perfil")
public class Perfil {
	
	private UsuarioDAO usuarioDAO;
	
	@GET
	@Path("/amigos/{correo}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response listaAmigos(@PathParam("correo") String correo) {
		
		usuarioDAO = new UsuarioDAO();
		ArrayList<Usuario> listaAmigos;
		
		listaAmigos = usuarioDAO.listaAmigos(correo);
		
		return Response.status(Status.OK).entity(listaAmigos).build();
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/nombre")
	public Response actualizarNombre(@FormParam("correo") String correo, @FormParam("nombre") String nombre) {
		
		usuarioDAO = new UsuarioDAO();
		System.out.println(correo + " " + nombre);
		
		boolean actualizado = usuarioDAO.actualizarNombre(correo, nombre);
		if(actualizado) {
			return Response.status(Status.OK).entity(nombre).build(); 
		}else {
			return Response.status(Status.CONFLICT).entity("El nombre no ha podido actualizarse").build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/apellidos")
	public Response actualizarApellidos(@FormParam("correo") String correo, @FormParam("apellidos") String apellidos) {
		usuarioDAO = new UsuarioDAO();
		System.out.println(correo + " " + apellidos);
	
		boolean actualizado = usuarioDAO.actualizarApellidos(correo, apellidos);
		if(actualizado) {
			return Response.status(Status.OK).entity(apellidos).build(); 
		}else {
			return Response.status(Status.CONFLICT).entity("El apellido no ha podido actualizarse").build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/direccion")
	public Response actualizarDireccion(@FormParam("correo") String correo, @FormParam("direccion") String direccion) {
		usuarioDAO = new UsuarioDAO();
		System.out.println(correo + " " + direccion);
		
		boolean actualizado = usuarioDAO.actualizarDireccion(correo, direccion);
		if(actualizado) {
			return Response.status(Status.OK).entity(direccion).build(); 
		}else {
			return Response.status(Status.CONFLICT).entity("La dirección no ha podido actualizarse").build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/genero")
	public Response actualizarGenero(@FormParam("correo") String correo, @FormParam("genero") String genero) {
		usuarioDAO = new UsuarioDAO();
		System.out.println(correo + " " + genero);
	
		boolean actualizado = usuarioDAO.actualizarGenero(correo, genero);
		if(actualizado) {
			return Response.status(Status.OK).entity(genero).build(); 
		}else {
			return Response.status(Status.CONFLICT).entity("El género no ha podido actualizarse").build();
		}
		
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/borrarusuario/{correo}")
	public Response borrarUsuario(@PathParam("correo") String correo) {
		
		System.out.println(correo);
		
		usuarioDAO = new UsuarioDAO();
		
		boolean borrado = usuarioDAO.borrarUsuario(correo);
		if(borrado) {
			return Response.status(Status.NO_CONTENT).build(); 
			//204 - La petición se ha completado con éxito pero su respuesta no tiene ningún contenido
		}else {
			return Response.status(Status.CONFLICT).entity("El usuario no ha sido borrado").build();
			//409 - Esta respuesta puede ser enviada cuando una petición tiene conflicto con el estado actual del servidor.
		}
		
	}
	

	@GET
	@Path("/eventospendientes")
	@Produces({MediaType.APPLICATION_JSON})
	@Secured
	public Response eventosPendientes() {
		return null;
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
