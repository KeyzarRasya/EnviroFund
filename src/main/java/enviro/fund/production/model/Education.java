package enviro.fund.production.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Education {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String judul;
    private String materi;
    private User author;
    @OneToMany
    private List<Kuis> kuis;
}
