package enviro.fund.production.model;

import enviro.fund.production.enumeration.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@Table(name = "charity_table")
@Entity
public class Charity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private BigDecimal charityAmount;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal fundedMoney;
    @Embedded
    private Address address;
    @OneToOne
    private User host;
    private boolean isVerified;
    private boolean isComplete;
    @ManyToMany
    @JoinTable(name = "charity_donors",
            joinColumns = @JoinColumn(name = "charity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> donors;
}
