package enviro.fund.production.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Entity
@Data
public class Kuis {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String soal;
    private List<String> jawaban;
    private String jawabanBenar;
    private int poin;

}
