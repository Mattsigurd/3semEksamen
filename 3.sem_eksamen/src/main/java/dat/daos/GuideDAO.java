package dat.daos;

import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GuideDAO implements IDAO<Guide> {

    private static GuideDAO instance;
    private static EntityManagerFactory emf;

    public GuideDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO(emf);
        }
        return instance;
    }

    @Override
    public List<Guide> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g", Guide.class);
            return query.getResultList();
        }
    }

    @Override
    public Guide getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Guide.class, id);
        }
    }

    @Override
    public Guide create(Guide guide) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(guide);
            em.getTransaction().commit();
            return guide;
        }
    }

    @Override
    public Guide update(int id, Guide update) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                guide.setFirstName(update.getFirstName());
                guide.setLastName(update.getLastName());
                guide.setEmail(update.getEmail());
                guide.setPhone(update.getPhone());
                guide.setYearsOfExperience(update.getYearsOfExperience());
                em.merge(guide);
                em.getTransaction().commit();
            }
            return guide;
        }
    }

    @Override
    public void delete(Guide guide) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide managedGuide = em.find(Guide.class, guide.getId());
            if (managedGuide != null) {
                em.remove(managedGuide);
            }
            em.getTransaction().commit();
        }
    }
}