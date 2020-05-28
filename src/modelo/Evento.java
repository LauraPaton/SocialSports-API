package modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evento {

    private String deporte, localizacion, horaEvento;
   
    public String getDeporte() {
        return deporte;
    }
    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }
    public String getLocalizacion() {
        return localizacion;
    }
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    public String getHoraEvento() {
        return horaEvento;
    }
    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

}
