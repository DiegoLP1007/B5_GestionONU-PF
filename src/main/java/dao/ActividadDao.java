package dao;
 
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import model.Actividad;
 
public class ActividadDao {
 
    private EntityManagerFactory enti = Persistence.createEntityManagerFactory("gestionOnuPU");
 
    public void guardar(Actividad actividad) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            admin.persist(actividad);
            transaccion.commit();
        } catch (Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
        } finally {
            admin.close();
        }
    }
 
    public List<Actividad> listarTodos() {
        String jpql = "SELECT a FROM Actividad a";
        EntityManager admin = enti.createEntityManager();
        try {
            return admin.createQuery(jpql, Actividad.class).getResultList();
        } finally {
            admin.close();
        }
    }
 
    public Actividad buscarPorId(int id) {
        EntityManager admin = enti.createEntityManager();
        try {
            return admin.find(Actividad.class, id);
        } finally {
            admin.close();
        }
    }
 
    public void actualizar(Actividad actividad) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            admin.merge(actividad);
            transaccion.commit();
        } catch (Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
        } finally {
            admin.close();
        }
    }
 
    public void eliminar(int id) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            Actividad actividad = admin.find(Actividad.class, id);
            if (actividad != null) {
                admin.remove(actividad);
            }
            transaccion.commit();
        } catch (Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
        } finally {
            admin.close();
        }
    }
}