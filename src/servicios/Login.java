package servicios;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UsuarioDAO;
import modelo.Credenciales;
import seguridad.JwtProvider;

@Path("/login")
public class Login { 
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public Response login(Credenciales credenciales) {
		
		String correo = credenciales.getCorreo();
		String contrasena = credenciales.getContrasena();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		if(usuarioDAO.usuarioValido(correo, contrasena)) {
			JwtProvider jwt = new JwtProvider();
			String token = jwt.generarToken(correo);
			
			return Response
					.status(Status.OK)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.build();
		}
		
		return Response
				.status(Status.UNAUTHORIZED)
				.build();
	}
}
