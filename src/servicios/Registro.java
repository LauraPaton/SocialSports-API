package servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UsuarioDAO;
import seguridad.Validaciones;

@Path("/registro")
public class Registro {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response registro(@FormParam("emailUsuario") String emailUsuario, @FormParam("passwordUsuario") String passwordUsuario) {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Validaciones validaciones = new Validaciones();
		
		System.out.println(emailUsuario + ", " + passwordUsuario);
		
		Response.Status responseStatus = Response.Status.OK;

		if(validaciones.validarCorreo(emailUsuario) && validaciones.validarContrasena(passwordUsuario)) {
			
			if(usuarioDAO.registroUsuario(emailUsuario, passwordUsuario)) {
				responseStatus = Response.Status.CREATED;
			}else if(usuarioDAO.existeCorreo(emailUsuario)){
				return Response
						.status(Status.CONFLICT)
						.entity("This email is already registered.")
						.build();
			}
		}
		
		return Response.status(responseStatus).build();
	}
}


