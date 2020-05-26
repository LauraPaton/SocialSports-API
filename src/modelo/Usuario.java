package modelo;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	
	private String nombre, contrasena, correo, genero, direccion;
	private Blob fotoPerfil;
	private Date fechaNacimiento, alta;
	double reputacionParticipante, reputacionOrganizador;
	private List<Usuario> listaAmigos, listaBloqueados;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Blob getFotoPerfil() {
		return fotoPerfil;
	}
	public void setFotoPerfil(Blob fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Date getAlta() {
		return alta;
	}
	public void setAlta(Date alta) {
		this.alta = alta;
	}
	public double getReputacionParticipante() {
		return reputacionParticipante;
	}
	public void setReputacionParticipante(double reputacionParticipante) {
		this.reputacionParticipante = reputacionParticipante;
	}
	public double getReputacionOrganizador() {
		return reputacionOrganizador;
	}
	public void setReputacionOrganizador(double reputacionOrganizador) {
		this.reputacionOrganizador = reputacionOrganizador;
	}
	public List<Usuario> getListaAmigos() {
		return listaAmigos;
	}
	public void setListaAmigos(List<Usuario> listaAmigos) {
		this.listaAmigos = listaAmigos;
	}
	public List<Usuario> getListaBloqueados() {
		return listaBloqueados;
	}
	public void setListaBloqueados(List<Usuario> listaBloqueados) {
		this.listaBloqueados = listaBloqueados;
	}
	
}
