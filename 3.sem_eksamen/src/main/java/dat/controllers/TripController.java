package dat.controllers;

import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.daos.TripDAO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Message;
import dat.entities.Trip;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TripController {

    private static final Logger log = LoggerFactory.getLogger(TripController.class);
    private final TripDAO dao;

    public TripController(TripDAO dao) {
        this.dao = dao;
    }

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = TripDAO.getInstance(emf);
    }

    public void getAllTrips(Context ctx) {
        try {
            List<Trip> doctors = dao.getAll();
            ctx.res().setStatus(200);
            ctx.json(doctors, TripDTO.class);
        } catch (Exception e) {
            log.error("Error reading all trips: {}", e.getMessage());
            throw new ApiException(500, e.getMessage());
        }
    }

    public void getTripById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Trip trip = dao.getById(id);
            if (trip != null) {
                ctx.status(200).json(new Message(200, "Success"));
            } else {
                ctx.status(404).json(new Message(404, "Trip not found"));
            }
        } catch (Exception e) {
            log.error("Error getting trip by ID: {}", e.getMessage());
            ctx.status(500).json(new Message(500, e.getMessage()));
        }
    }

    public void deleteTrip(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Trip trip = dao.getById(id);
            if (trip != null) {
                dao.delete(trip);
                ctx.status(204);
            } else {
                ctx.status(404).json(new Message(404, "Trip not found"));
            }
        } catch (Exception e) {
            log.error("Error deleting trip: {}", e.getMessage());
            ctx.status(500).json(new Message(500, e.getMessage()));
        }
    }

    public void createTrip(Context ctx) {
        try {
            Trip jsonRequest = ctx.bodyAsClass(Trip.class);
            Trip trip = dao.create(jsonRequest);
            ctx.res().setStatus(201);
            ctx.json(trip, TripDTO.class);
        } catch (Exception e) {
            log.error("Error creating trip: {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void updateTrip(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
            Trip trip = dao.update(id, validateEntity(ctx));
            ctx.res().setStatus(200);
            ctx.json(trip, TripDTO.class);
        } catch (Exception e) {
            log.error("Error updating trip: {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

//    public void deleteTrip(Context ctx) {
//        try {
//            Trip trip = ctx.bodyAsClass(Trip.class);
//            dao.delete(trip);
//            ctx.res().setStatus(204);
//        } catch (Exception e) {
//            log.error("Error deleting trip: {}", e.getMessage());
//            throw new ApiException(500, e.getMessage());
//        }
//    }

    public void addGuideToTrip(Context ctx) {
        try {
            int tripId = Integer.parseInt(ctx.pathParam("tripId"));
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            dao.addGuideToTrip(tripId, guideId);
            ctx.status(200).json(new Message(200, "Guide added to trip"));
        } catch (Exception e) {
            log.error("Error adding guide to trip: {}", e.getMessage());
            ctx.status(500).json(new Message(500, e.getMessage()));
        }
    }

    public void getTripsByGuide(Context ctx) {
        try {
            int guideId = Integer.parseInt(ctx.pathParam("guideId"));
            Set<TripDTO> trips = dao.getTripsByGuide(guideId);
            ctx.status(200).json(new Message(200, "Success"));
        } catch (Exception e) {
            log.error("Error getting trips by guide: {}", e.getMessage());
            ctx.status(500).json(new Message(500, e.getMessage()));
        }
    }
    public void populateTrips(Context ctx) {
        try {
            Populator.populate();
            ctx.status(200).json(new Message(200, "Database populated with trips and guides"));
        } catch (Exception e) {
            log.error("Error populating database: {}", e.getMessage());
            ctx.status(500).json(new Message(500, e.getMessage()));
        }
    }

    public void getTripsByCategory(Context ctx) {
        try {
            String category = ctx.pathParam("category").toUpperCase();
            List<Trip> trips = dao.getAll().stream()
                    .filter(trip -> trip.getCategory().name().equals(category))
                    .collect(Collectors.toList());
            List<TripDTO> tripDTOs = trips.stream().map(TripDTO::new).collect(Collectors.toList());
            ctx.status(200).json(new Message(200, "Success"));
        } catch (Exception e) {
            log.error("Error getting trips by category: {}", e.getMessage());
            ctx.status(500).json(new Message(500, e.getMessage()));
        }
    }

//    public void getGuideTripOverview(Context ctx) {
//        try {
//            List<Guide> guides = dao.getAllGuides();
//            List<Map<String, Object>> overview = guides.stream().map(guide -> {
//                Map<String, Object> guideOverview = new HashMap<>();
//                guideOverview.put("guideId", guide.getId());
//                float totalPrice = guide.getTrips().stream().map(Trip::getPrice).reduce(0.0f, Float::sum);
//                guideOverview.put("totalPrice", totalPrice);
//                return guideOverview;
//            }).collect(Collectors.toList());
//            ctx.status(200).json(new Message<>(200, "Success", overview));
//        } catch (Exception e) {
//            log.error("Error getting guide trip overview: {}", e.getMessage());
//            ctx.status(500).json(new Message<>(500, e.getMessage(), null));
//        }
//    }
    private Trip validateEntity(Context ctx) {
        return ctx.bodyValidator(Trip.class)
                .check(trip -> trip.getName() != null && !trip.getName().isEmpty(), "Missing name")
                .check(trip -> trip.getStartTime() != null, "Missing start time")
                .check(trip -> trip.getEndTime() != null, "Missing end time")
                .check(trip -> trip.getLongitude() != 0, "Missing longitude")
                .check(trip -> trip.getLatitude() != 0, "Missing latitude")
                .check(trip -> trip.getPrice() > 0, "Missing or invalid price")
                .check(trip -> trip.getCategory() != null, "Missing category")
                .check(trip -> trip.getGuide() != null, "Missing guide")
                .get();
    }
    private Boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }
}