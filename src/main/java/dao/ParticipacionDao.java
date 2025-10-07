package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import model.Participacion;

public class ParticipacionDao {
    private EntityManagerFactory enti = Persistence.createEntityManagerFactory("gestionOnuPU");

    
    public void guardar(Participacion participacion) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            admin.persist(participacion);
            transaccion.commit();
        } catch (Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
        } finally {
            admin.close();
        }
    }
    
    public List<Participacion> listarTodos() {
        String jpql = "SELECT p FROM Participaciones p";
        EntityManager admin = enti.createEntityManager();
        try {
            return admin.createQuery(jpql, Participacion.class).getResultList();
        } finally {
            admin.close();
        }
    }
    
    public Participacion buscarPorId(int id) {
        EntityManager admin = enti.createEntityManager();
        try {
            return admin.find(Participacion.class, id);
        } finally {
            admin.close();
        }
    }
    
    public void Actualizar(Participacion participacion) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            admin.merge(participacion);
            transaccion.commit();
        } catch (Exception e) {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
        } finally {
            admin.close();
        }
    }
    
    public void Eliminar(int id) {
        EntityManager admin = enti.createEntityManager();
        EntityTransaction transaccion = admin.getTransaction();
        try {
            transaccion.begin();
            Participacion participacion = admin.find(Participacion.class, id);
            if (participacion != null) {
                admin.remove(participacion);
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
