package dat.config;

import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.entities.Category;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Populator {

    public static void populate() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        GuideDAO guideDAO = GuideDAO.getInstance(emf);
        TripDAO tripDAO = TripDAO.getInstance(emf);

        // Create guides
        Guide guide1 = new Guide();
        guide1.setFirstName("John");
        guide1.setLastName("Doe");
        guide1.setEmail("john.doe@example.com");
        guide1.setPhone(123456789);
        guide1.setYearsOfExperience(5);
        guideDAO.create(guide1);

        Guide guide2 = new Guide();
        guide2.setFirstName("Jane");
        guide2.setLastName("Smith");
        guide2.setEmail("jane.smith@example.com");
        guide2.setPhone(987654321);
        guide2.setYearsOfExperience(8);
        guideDAO.create(guide2);

        // Create trips
        Trip trip1 = new Trip();
        trip1.setName("Forest Adventure");
        trip1.setStartTime(LocalTime.of(9, 0));
        trip1.setEndTime(LocalTime.of(17, 0));
        trip1.setLongitude(34.0522f);
        trip1.setLatitude(-118.2437f);
        trip1.setPrice(100.0f);
        trip1.setCategory(Category.FOREST);
        trip1.setGuide(guide1);
        tripDAO.create(trip1);

        Trip trip2 = new Trip();
        trip2.setName("City Tour");
        trip2.setStartTime(LocalTime.of(10, 0));
        trip2.setEndTime(LocalTime.of(15, 0));
        trip2.setLongitude(40.7128f);
        trip2.setLatitude(-74.0060f);
        trip2.setPrice(75.0f);
        trip2.setCategory(Category.CITY);
        trip2.setGuide(guide2);
        tripDAO.create(trip2);

        // Add trips to guides
        Set<Trip> trips1 = new HashSet<>();
        trips1.add(trip1);
        guide1.setTrips(trips1);
        guideDAO.update(guide1.getId(), guide1);

        Set<Trip> trips2 = new HashSet<>();
        trips2.add(trip2);
        guide2.setTrips(trips2);
        guideDAO.update(guide2.getId(), guide2);
    }

}