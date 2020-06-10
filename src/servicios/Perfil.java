package servicios;

import java.io.InputStream;
import java.util.ArrayList;

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

import modelo.Usuario;

@Path("/perfil")
public class Perfil {
	
	private UsuarioDAO usuarioDAO;
	
	/***********DATOS USUARIO***********/
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/nombre")
	public Response actualizarNombre(@FormParam("correo") String correo, @FormParam("nombre") String nombre) {
		
		usuarioDAO = new UsuarioDAO();
		
		boolean actualizado = usuarioDAO.actualizarNombre(correo, nombre);
		if(actualizado) {
			return Response.status(Status.OK).entity(nombre).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/apellidos")
	public Response actualizarApellidos(@FormParam("correo") String correo, @FormParam("apellidos") String apellidos) {
		
		usuarioDAO = new UsuarioDAO();
	
		boolean actualizado = usuarioDAO.actualizarApellidos(correo, apellidos);
		if(actualizado) {
			return Response.status(Status.OK).entity(apellidos).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/direccion")
	public Response actualizarDireccion(@FormParam("correo") String correo, @FormParam("direccion") String direccion) {
		usuarioDAO = new UsuarioDAO();
		
		boolean actualizado = usuarioDAO.actualizarDireccion(correo, direccion);
		if(actualizado) {
			return Response.status(Status.OK).entity(direccion).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/genero")
	public Response actualizarGenero(@FormParam("correo") String correo, @FormParam("genero") String genero) {
		
		usuarioDAO = new UsuarioDAO();
	
		boolean actualizado = usuarioDAO.actualizarGenero(correo, genero);
		if(actualizado) {
			return Response.status(Status.OK).entity(genero).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/nacimiento")
	public Response actualizarFechaNacimiento(@FormParam("correo") String correo, @FormParam("fecha") String fecha) {
		
		usuarioDAO = new UsuarioDAO();
	
		boolean actualizado = usuarioDAO.actualizarFechaNacimiento(correo, usuarioDAO.StringToDate(fecha));
		if(actualizado) {
			return Response.status(Status.OK).entity(fecha).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/borrarusuario/{correo}")
	public Response borrarUsuario(@PathParam("correo") String correo) {
		
		usuarioDAO = new UsuarioDAO();
		
		boolean borrado = usuarioDAO.borrarUsuario(correo);
		if(borrado) {
			return Response.status(Status.NO_CONTENT).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	/***********AMIGOS***********/
	
	@GET
	@Path("/amigos/{correo}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response listaAmigos(@PathParam("correo") String correo) {
		
		usuarioDAO = new UsuarioDAO();
		ArrayList<Usuario> listaAmigos = usuarioDAO.listaAmigos(correo);
		
		return Response.status(Status.OK).entity(listaAmigos).build();
		
	}
	
	@POST
	@Path("/agregaramigo/{correo}/{correoAmigo}")
	public Response agregarAmigo(@PathParam("correo") String correo, @PathParam("correoAmigo") String correoAmigo) {
		usuarioDAO = new UsuarioDAO();
		
		if(usuarioDAO.agregarAmigo(correo, correoAmigo)) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@DELETE
	@Path("/borraramigo/{correo}/{correoAmigo}")
	public Response borrarAmigo(@PathParam("correo") String correo, @PathParam("correoAmigo") String correoAmigo) {
		
		usuarioDAO = new UsuarioDAO();
		
		System.out.println(correo + " " + correoAmigo);
		
		if(usuarioDAO.borrarAmigo(correo, correoAmigo)) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/***********IMAGENES***********/
	
	@POST
	@Path("/uploadimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)  
    public Response uploadFile(  
            @FormDataParam("file") InputStream uploadedInputStream,  
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		ImagenesDAO imagenesDAO = new ImagenesDAO();
		boolean subida = imagenesDAO.uploadImage(uploadedInputStream);
		
		if(subida){
				return Response.status(Status.OK).build();
		}
		
		return Response.status(Status.CONFLICT).build();

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
