package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import model.Proyecto;

public class ProyectoDao {
    private EntityManagerFactory enti = Persistence.createEntityManagerFactory("gestionOnuPU");

    public void guardar(Proyecto proyecto) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        
        try {
            transaccion.begin();
            admin.persist(proyecto);
            transaccion.commit();
        } catch (Exception e) {
            if (transaccion.isActive())  transaccion.rollback();
        } finally {
            admin.close();
        }
    }
    
    public List<Proyecto> listarTodos() {
        String jpql = "SELECT p FROM Proyecto p";
        EntityManager admin = enti.createEntityManager();
        try {
            return admin.createQuery(jpql, Proyecto.class).getResultList();
        } finally {
            admin.close();
        }
    }
    
    public Proyecto buscarPorId(int id) {
        EntityManager admin = enti.createEntityManager();
        try {
            return admin.find(Proyecto.class, id);
        } finally {
            admin.close();
        }
    }
    
    public void actualizarProyecto (Proyecto proyecto) {
        EntityManager admin =  enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        
        try {
            transaccion.begin();
            admin.merge(proyecto);
            transaccion.commit();
        } catch (Exception e) {
            if(transaccion.isActive()) transaccion.rollback();
        } finally {
            admin.close();
        }
    }
    
    public void eliminar(int id) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            Proyecto proyecto = admin.find(Proyecto.class, id);
            if (proyecto != null) {
                admin.remove(proyecto);                
            }
            transaccion.commit();
        } catch (Exception e) {
            if(transaccion.isActive()) transaccion.rollback();
        } finally {
            admin.close();
        }
    }
}
