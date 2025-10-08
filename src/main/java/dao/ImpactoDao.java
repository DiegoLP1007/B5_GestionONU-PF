package dao;
 
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Impacto;
 
public class ImpactoDao {
 
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestionOnuPU");
 
    public void guardar(Impacto impacto) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(impacto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al guardar impacto: " + e.getMessage());
        } finally {
            em.close();
        }
    }
 
    public Impacto buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Impacto.class, id);
        } finally {
            em.close();
        }
    }
 
    public List<Impacto> listarImpactos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT i FROM Impacto i", Impacto.class).getResultList();
        } finally {
            em.close();
        }
    }
 
    public void actualizar(Impacto impacto) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(impacto);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al actualizar impacto: " + e.getMessage());
        } finally {
            em.close();
        }
    }
 
    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Impacto impacto = em.find(Impacto.class, id);
            if (impacto != null) {
                em.remove(impacto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al eliminar impacto: " + e.getMessage());
        } finally {
            em.close();
        }
    }
 
    // Método opcional: listar impactos por proyecto
    public List<Impacto> listarPorProyecto(int idProyecto) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Impacto> query = em.createQuery(
                "SELECT i FROM Impacto i WHERE i.proyecto.idProyecto = :idProyecto", Impacto.class);
            query.setParameter("idProyecto", idProyecto);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}