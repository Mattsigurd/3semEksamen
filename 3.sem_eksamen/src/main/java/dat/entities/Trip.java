package dat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private float longitude;
    private float latitude;
    private float price;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Category category;
    @ManyToOne
    private Guide guide;


}
