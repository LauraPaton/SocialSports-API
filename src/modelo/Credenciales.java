package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Credenciales {
	private String correo, contrasena;

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
}
