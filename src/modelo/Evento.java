package modelo;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evento {

	private String deporte, localidad, direccion;
	private long idEvento, organizadorEvento;
	private Date fechaEvento, fechaCreacion;
	private String horaEvento;
	private int maximoParticipantes;
	private boolean instalacionesReservadas;
	private float costeEvento, precioPorParticipante;
	private String comentarios;
	private Requisitos requisitos;
	ArrayList<Usuario> listaSolicitantes;
	ArrayList<Usuario> listaDescartados;
	ArrayList<Usuario> listaParticipantes;
	
	public long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}
	public String getDeporte() {
		return deporte;
	}
	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public long getOrganizadorEvento() {
		return organizadorEvento;
	}
	public void setOrganizadorEvento(long organizadorEvento) {
		this.organizadorEvento = organizadorEvento;
	}
	public Date getFechaEvento() {
		return fechaEvento;
	}
	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getHoraEvento() {
		return horaEvento;
	}
	public void setHoraEvento(String horaEvento) {
		this.horaEvento = horaEvento;
	}
	public int getMaximoParticipantes() {
		return maximoParticipantes;
	}
	public void setMaximoParticipantes(int maximoParticipantes) {
		this.maximoParticipantes = maximoParticipantes;
	}
	public boolean isInstalacionesReservadas() {
		return instalacionesReservadas;
	}
	public void setInstalacionesReservadas(boolean instalacionesReservadas) {
		this.instalacionesReservadas = instalacionesReservadas;
	}
	public float getCosteEvento() {
		return costeEvento;
	}
	public void setCosteEvento(float costeEvento) {
		this.costeEvento = costeEvento;
	}
	public float getPrecioPorParticipante() {
		return precioPorParticipante;
	}
	public void setPrecioPorParticipante(float precioPorParticipante) {
		this.precioPorParticipante = precioPorParticipante;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public Requisitos getRequisitos() {
		return requisitos;
	}
	public void setRequisitos(Requisitos requisitos) {
		this.requisitos = requisitos;
	}
	public ArrayList<Usuario> getListaSolicitantes() {
		return listaSolicitantes;
	}
	public void setListaSolicitantes(ArrayList<Usuario> listaSolicitantes) {
		this.listaSolicitantes = listaSolicitantes;
	}
	public ArrayList<Usuario> getListaDescartados() {
		return listaDescartados;
	}
	public void setListaDescartados(ArrayList<Usuario> listaDescartados) {
		this.listaDescartados = listaDescartados;
	}
	public ArrayList<Usuario> getListaParticipantes() {
		return listaParticipantes;
	}
	public void setListaParticipantes(ArrayList<Usuario> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}
	
}
