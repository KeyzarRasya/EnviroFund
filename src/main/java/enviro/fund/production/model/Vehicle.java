package enviro.fund.production.model;

import enviro.fund.production.enumeration.VehicleType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private int cost;
    private boolean IsAvailable;
    @OneToOne
    private User renter;
}
