package modelo;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
	
	private String emailUsuario;       
    private String paswordUsuario;     
    private String nombreUsuario;       
    private String apellidosUsuario;
    private String generoUsuario;       
    private String direccionUsuario; 
    private Date fechaNacimientoUsuario;
    private Date fechaAltaUsuario;
    private float reputacionParticipanteUsuario;
    private float reputacionOrganizadorUsuario;
    private String fotoPerfilUsuario;              
    private boolean isOnlineNow;
    private ArrayList<Usuario> listaAmigos;
    private ArrayList<Usuario> listaBloqueados;

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPaswordUsuario() {
        return paswordUsuario;
    }

    public void setPaswordUsuario(String paswordUsuario) {
        this.paswordUsuario = paswordUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidosUsuario() {
        return apellidosUsuario;
    }

    public void setApellidosUsuario(String apellidosUsuario) {
        this.apellidosUsuario = apellidosUsuario;
    }

    public String getGeneroUsuario() {
        return generoUsuario;
    }

    public void setGeneroUsuario(String generoUsuario) {
        this.generoUsuario = generoUsuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public Date getFechaNacimientoUsuario() {
        return fechaNacimientoUsuario;
    }

    public void setFechaNacimientoUsuario(Date fechaNacimientoUsuario) {
        this.fechaNacimientoUsuario = fechaNacimientoUsuario;
    }

    public Date getFechaAltaUsuario() {
        return fechaAltaUsuario;
    }

    public void setFechaAltaUsuario(Date fechaAltaUsuario) {
        this.fechaAltaUsuario = fechaAltaUsuario;
    }

    public float getReputacionParticipanteUsuario() {
        return reputacionParticipanteUsuario;
    }

    public void setReputacionParticipanteUsuario(float reputacionParticipanteUsuario) {
        this.reputacionParticipanteUsuario = reputacionParticipanteUsuario;
    }

    public float getReputacionOrganizadorUsuario() {
        return reputacionOrganizadorUsuario;
    }

    public void setReputacionOrganizadorUsuario(float reputacionOrganizadorUsuario) {
        this.reputacionOrganizadorUsuario = reputacionOrganizadorUsuario;
    }

    public String getFotoPerfilUsuario() {
        return fotoPerfilUsuario;
    }

    public void setFotoPerfilUsuario(String fotoPerfilUsuario) {
        this.fotoPerfilUsuario = fotoPerfilUsuario;
    }

    public boolean isOnlineNow() {
        return isOnlineNow;
    }

    public void setOnlineNow(boolean onlineNow) {
        isOnlineNow = onlineNow;
    }

    public ArrayList<Usuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(ArrayList<Usuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public ArrayList<Usuario> getListaBloqueados() {
        return listaBloqueados;
    }

    public void setListaBloqueados(ArrayList<Usuario> listaBloqueados) {
        this.listaBloqueados = listaBloqueados;
    }
}
