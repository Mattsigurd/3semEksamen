package dat.dtos;

import dat.entities.Guide;
import dat.entities.Trip;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class GuideDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;
    private Set<TripDTO> trips;

    public GuideDTO(Guide guide) {
        this.id = guide.getId();
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.yearsOfExperience = guide.getYearsOfExperience();
        this.trips = guide.getTrips().stream().map(TripDTO::new).collect(Collectors.toSet());
    }

}