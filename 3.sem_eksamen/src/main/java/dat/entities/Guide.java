package dat.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Guide {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private int yearsOfExperience;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Trip> trips = new HashSet<>();
}
