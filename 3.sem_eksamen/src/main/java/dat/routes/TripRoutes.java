package dat.routes;

import dat.config.HibernateConfig;
import dat.controllers.TripController;
import dat.daos.TripDAO;
import jakarta.persistence.EntityManagerFactory;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final TripDAO tripDAO = new TripDAO(emf);
    private final TripController tripController = new TripController(tripDAO);

    public EndpointGroup getRoutes(){
        return () -> {
            get("/trips", tripController::getAllTrips);
            get("/trips/{id}", tripController::getTripById);
            post("/trips", tripController::createTrip);
            put("/trips/{id}", tripController::updateTrip);
            delete("/trips/{id}", tripController::deleteTrip);
            put("/trips/{tripId}/guides/{guideId}", tripController::addGuideToTrip);
            post("/trips/populate", tripController::populateTrips);
            get("/trips/category/{category}", tripController::getTripsByCategory);
        };
    }
}
