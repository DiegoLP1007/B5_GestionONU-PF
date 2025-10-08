package model;
 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
@Entity
@Table(name = "Actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idActividad;
    @ManyToOne
    @JoinColumn(name="idProyecto", referencedColumnName="idProyecto")
    private Proyecto proyecto;
    @Column(name ="nombre", nullable = false)
    private String nombre;
    @Column(name ="descripcion", nullable = false)
    private String descripcion;
    @Column(name ="lugar", nullable = false)
    private String lugar;
    @Column(name="fecha")
    private Date fecha;
    @Column(name ="capacidad", nullable = false)
    private int capacidad;
 
    public Actividad() {
    }
 
    public Actividad(Proyecto proyecto, String nombre, String descripcion, String lugar, Date fecha, int capacidad) {
        this.proyecto = proyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.capacidad = capacidad;
    }
 
    public int getIdActividad() {
        return idActividad;
    }
 
    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
 
    public Proyecto getProyecto() {
        return proyecto;
    }
 
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public String getLugar() {
        return lugar;
    }
 
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
 
    public Date getFecha() {
        return fecha;
    }
 
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 
    public int getCapacidad() {
        return capacidad;
    }
 
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}