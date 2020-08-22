package servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import dao.UsuarioDAO;
import modelo.PuntuacionParticipante;
import modelo.Usuario;
import seguridad.JwtProvider;
import seguridad.Secured;

@Path("/perfil")
public class Perfil {
	
	private UsuarioDAO usuarioDAO;
	private JwtProvider jwt;
	
	/***********DATOS USUARIO***********/
	
	@Secured
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/actualizardatos")
	public Response actualizarDatos(@FormParam("nombre") String nombre, @FormParam("apellidos") String apellidos, @FormParam("direccion") String direccion,
			@FormParam("genero") String genero, @FormParam("fechanacimiento") String fechaNacimiento, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		if(nombre == null && apellidos == null && direccion == null && genero == null && fechaNacimiento == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		usuarioDAO.actualizarDatos(correo, nombre, apellidos, direccion, genero, usuarioDAO.StringToDate(fechaNacimiento));
		return Response.status(Status.NO_CONTENT).build();
		
	}
	
	@Secured
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/password")
	public Response actualizarPassword(@FormParam("password") String password, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		boolean actualizado = usuarioDAO.actualizarPassword(correo, password);
		if(actualizado) {
			return Response.status(Status.OK).entity(password).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	@Secured
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/borrarusuario")
	public Response borrarUsuario(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		boolean borrado = usuarioDAO.borrarUsuario(correo);
		if(borrado) {
			return Response.status(Status.NO_CONTENT).build(); 
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
		
	}
	
	/***********AMIGOS***********/
	
	@Secured
	@GET
	@Path("/amigos")
	@Produces({MediaType.APPLICATION_JSON})
	public Response listaAmigos(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		ArrayList<Usuario> listaAmigos = usuarioDAO.listaAmigos(correo);
		
		return Response.status(Status.OK).entity(listaAmigos).build();
		
	}
	
	@Secured
	@GET
	@Path("/bloqueados")
	@Produces({MediaType.APPLICATION_JSON})
	public Response listaBloqueados(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		ArrayList<Usuario> listaBloqueados = usuarioDAO.listaBloqueados(correo);
		
		return Response.status(Status.OK).entity(listaBloqueados).build();
		
	}
	
	@Secured
	@POST
	@Path("/amigos/agregar/{correoAmigo}")
	public Response agregarAmigo(@PathParam("correoAmigo") String correoAmigo, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		if(usuarioDAO.agregarAmigo(correo, correoAmigo)) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@DELETE
	@Path("/amigos/eliminar/{correoAmigo}")
	public Response eliminarAmigo(@PathParam("correoAmigo") String correoAmigo, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		if(usuarioDAO.borrarAmigo(correo, correoAmigo)) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@POST
	@Path("/bloquearusuario/{correoBloqueado}")
	public Response bloquearUsuario(@PathParam("correoBloqueado") String correoBloqueado, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		if(usuarioDAO.bloquearUsuario(correo, correoBloqueado)) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@Secured
	@DELETE
	@Path("/quitarbloqueo/{correoBloqueado}")
	public Response quitarBloqueo(@PathParam("correoBloqueado") String correoBloqueado, @HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		if(usuarioDAO.quitarBloqueo(correo, correoBloqueado)) {
			return Response.status(Status.NO_CONTENT).build();
		}else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	/************REPUTACION************/
	
	@Secured
	@GET
	@Path("/puntuacionparticipante")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getReputacionParticipante(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		return Response.status(Status.OK).entity(usuarioDAO.calcularPuntuacionParticipante(correo)).build();
		
	}
	
	@Secured
	@GET
	@Path("/puntuacionorganizador")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getReputacionOrganizador(@HeaderParam(HttpHeaders.AUTHORIZATION) String token) {
		
		usuarioDAO = new UsuarioDAO();
		jwt = new JwtProvider();
		String correo = jwt.getCorreo(token.substring("Bearer".length()).trim());
		
		return Response.status(Status.OK).entity(usuarioDAO.calcularPuntuacionOrganizador(correo)).build();
		
	}
	
	@Secured
	@POST
	@Path("/insertarpuntuacion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.TEXT_PLAIN})
	public Response insertarPuntuacionParticipante(PuntuacionParticipante puntuacion) {
		
		usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.insertarPuntuacionParticipante(puntuacion))
			return Response.status(Status.CREATED).entity(puntuacion.getEmailUsuarioPuntuado()).build();
		else
			return Response.status(Status.BAD_REQUEST).build();
	}
}
