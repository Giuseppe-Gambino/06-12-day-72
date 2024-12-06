package it.epicode.entity;

import it.epicode.classi_enum.Periodicita;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@NamedQuery(name = "Trova_tutto_Megazine", query = "SELECT a FROM Megazine a")
public class Megazine extends Element {

    @Enumerated
    @Column(name = "periodicita")
    private Periodicita periodicita;


}
