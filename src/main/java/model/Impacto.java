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
@Table (name ="Impactos")
public class Impacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idImpacto;
    @ManyToOne
    @JoinColumn(name = "idProyecto", referencedColumnName = "idProyecto")
    private Proyecto proyecto;
    @Column(name="fecha")
    private Date fecha;
    @Column(name ="descripcion", nullable = false)
    private String descripcion;
    @Column(name ="participantes", nullable = false)
    private int participantes;
    @Column(name ="horasTotales", nullable = false)
    private Double horasTotales;
    @Column(name ="resultados", nullable = false)
    private String resultados;
 
    public Impacto() {
    }
 
    public Impacto(Proyecto proyecto, Date fecha, String descripcion, int participantes, Double horasTotales, String resultados) {
        this.proyecto = proyecto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.participantes = participantes;
        this.horasTotales = horasTotales;
        this.resultados = resultados;
    }
 
    public int getIdImpacto() {
        return idImpacto;
    }
 
    public void setIdImpacto(int idImpacto) {
        this.idImpacto = idImpacto;
    }
 
    public Proyecto getProyecto() {
        return proyecto;
    }
 
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
 
    public Date getFecha() {
        return fecha;
    }
 
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public int getParticipantes() {
        return participantes;
    }
 
    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }
 
    public Double getHorasTotales() {
        return horasTotales;
    }
 
    public void setHorasTotales(Double horasTotales) {
        this.horasTotales = horasTotales;
    }
 
    public String getResultados() {
        return resultados;
    }
 
    public void setResultados(String resultados) {
        this.resultados = resultados;
    }
}