package model;

import java.sql.Timestamp;
import javax.ejb.Timeout;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "Participaciones")
public class Participacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParticipacion;
    
    @OneToMany
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;
    
    @OneToMany
    @JoinColumn(name="idProyecto", referencedColumnName = "idProyecto")
    private Proyecto proyecto;
    
    @Column(name = "horasTrabajadas", nullable = false)
    private Double horasTrabajadas;
    
    @Column(name = "fechaRegistro", insertable = false, updatable = false)
    private Timestamp fechaRegistro;

    public Participacion() {
    }

    public Participacion(Usuario usuario, Proyecto proyecto, Double horasTrabajadas, Timestamp fechaRegistro) {
        this.usuario = usuario;
        this.proyecto = proyecto;
        this.horasTrabajadas = horasTrabajadas;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(int idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    
}
