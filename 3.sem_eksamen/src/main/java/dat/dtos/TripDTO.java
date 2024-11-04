package dat.dtos;

import dat.entities.Trip;
import dat.entities.Category;
import dat.entities.Guide;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TripDTO {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private float longitude;
    private float latitude;
    private float price;
    private Category category;
    private Guide guide;

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.longitude = trip.getLongitude();
        this.latitude = trip.getLatitude();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
        this.guide = trip.getGuide();
    }
    public TripDTO(Long id, String name, LocalTime startTime, LocalTime endTime, float longitude, float latitude, float price, Category category, Guide guide) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.price = price;
        this.category = category;
        this.guide = guide;
    }

}