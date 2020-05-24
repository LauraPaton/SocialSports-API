package servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import dao.UsuarioDAO;
import modelo.Usuario;
import seguridad.Validaciones;

@Path("/registro")
public class Registro {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Usuario usuario) {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Validaciones validaciones = new Validaciones();
		
		String correo = usuario.getCorreo();
		String contrasena = usuario.getContrasena();
		
		Response.Status responseStatus = null;

		if(validaciones.validarCorreo(correo) && validaciones.validarContrasena(contrasena)) {
			if(usuarioDAO.registroUsuario(correo, contrasena)) {
				responseStatus = Response.Status.CREATED;
			}
		}else {
			responseStatus = Response.Status.UNAUTHORIZED;
		}
		
		return Response.status(responseStatus).build();
	}
}


