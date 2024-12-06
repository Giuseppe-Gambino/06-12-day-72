package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NamedQuery(name="Trova_tutto_Prestito", query="SELECT a FROM Prestito a")
public class Prestito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "element_id")
    private Element element;


    @Column(name = "data_inizio_prestito")
    private LocalDate dataInizioPrestito;


    @Column(name = "data_restituzione_prevista")
    private LocalDate dataRestituzionePrevista;


    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;

}
