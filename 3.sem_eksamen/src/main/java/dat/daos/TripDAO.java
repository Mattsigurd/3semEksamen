package dat.daos;

import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<Trip>, ITripGuideDAO {

    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public TripDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO(emf);
        }
        return instance;
    }

    @Override
    public List<Trip> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT new dat.dtos.TripDTO(t) FROM Trip t", Trip.class);
            return query.getResultList();
        }
    }

    @Override
    public Trip getById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip;
        }
    }

    @Override
    public Trip create(Trip trip) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();
            return trip;
        }
    }

    @Override
    public Trip update(int id, Trip trip) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip t = em.find(Trip.class, id);
            if (t == null) {
                em.getTransaction().rollback();
                return null;
            }
            t.setName(trip.getName());
            t.setPrice(trip.getPrice());
            t.setLatitude(trip.getLatitude());
            t.setLongitude(trip.getLongitude());
            t.setCategory(trip.getCategory());
            em.getTransaction().commit();
            return t;
        }
    }

    @Override
    public void delete(Trip trip) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip managedTrip = em.find(Trip.class, trip.getId());
            if (managedTrip != null) {
                em.remove(managedTrip);
            }
            em.getTransaction().commit();
        }
    }


    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);
            if (trip != null && guide != null) {
                trip.setGuide(guide);
                em.merge(trip);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t WHERE t.guide.id = :guideId", Trip.class);
            query.setParameter("guideId", guideId);
            List<Trip> trips = query.getResultList();
            return trips.stream().map(TripDTO::new).collect(Collectors.toSet());
        }
    }

    public Boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, integer);
            return trip != null;
        }
    }
}
