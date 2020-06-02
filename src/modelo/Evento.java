package modelo;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Evento{

    private String idEvento;    
    private Usuario organizadorEvento;
    private String deporte;     
    private String localidad;  
    private String direccion;   
    private Date fechaEvento;
    private String horaEvento;          
    private Date fechaCreacionEvento;
    private int maximoParticipantes;
    private boolean instalacionesReservadas;
    private float costeEvento;
    private float precioPorParticipante;
    private String comentarios;            
    private Requisitos requisitos;
    private boolean terminado;
    private List<Usuario> listaSolicitantes;
    private List<Usuario> listaDescartados;
    private List<Usuario> listaParticipantes;

    public Evento() {
    	
    }
    
    public Evento(String idEvento, Usuario organizadorEvento, String deporte, String localidad, String direccion, Date fechaEvento, String horaEvento, Date fechaCreacionEvento, int maximoParticipantes, boolean instalacionesReservadas, float costeEvento, float precioPorParticipante, String comentarios, Requisitos requisitos, boolean terminado, ArrayList<Usuario> listaSolicitantes, ArrayList<Usuario> listaDescartados, ArrayList<Usuario> listaParticipantes) {
        this.idEvento = idEvento;
        this.organizadorEvento = organizadorEvento;
        this.deporte = deporte;
        this.localidad = localidad;
        this.direccion = direccion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.fechaCreacionEvento = fechaCreacionEvento;
        this.maximoParticipantes = maximoParticipantes;
        this.instalacionesReservadas = instalacionesReservadas;
        this.costeEvento = costeEvento;
        this.precioPorParticipante = precioPorParticipante;
        this.comentarios = comentarios;
        this.requisitos = requisitos;
        this.terminado = terminado;
        this.listaSolicitantes = listaSolicitantes;
        this.listaDescartados = listaDescartados;
        this.listaParticipantes = listaParticipantes;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public Usuario getOrganizadorEvento() {
        return organizadorEvento;
    }

    public void setOrganizadorEvento(Usuario organizadorEvento) {
        this.organizadorEvento = organizadorEvento;
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

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public Date getFechaCreacionEvento() {
        return fechaCreacionEvento;
    }

    public void setFechaCreacionEvento(Date fechaCreacionEvento) {
        this.fechaCreacionEvento = fechaCreacionEvento;
    }

    public int getMaximoParticipantes() {
        return maximoParticipantes;
    }

    public void setMaximoParticipantes(int maximoParticipantes) {
        this.maximoParticipantes = maximoParticipantes;
    }

    public boolean getInstalacionesReservadas() {
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

    public boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public List<Usuario> getListaSolicitantes() {
        return listaSolicitantes;
    }

    public void setListaSolicitantes(List<Usuario> listaSolicitantes) {
        this.listaSolicitantes = listaSolicitantes;
    }

    public List<Usuario> getListaDescartados() {
        return listaDescartados;
    }

    public void setListaDescartados(ArrayList<Usuario> listaDescartados) {
        this.listaDescartados = listaDescartados;
    }

    public List<Usuario> getListaParticipantes() {
        return listaParticipantes;
    }

    public void setListaParticipantes(List<Usuario> listaParticipantes) {
        this.listaParticipantes = listaParticipantes;
    }
}
