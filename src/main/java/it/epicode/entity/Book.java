package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@Entity
@NamedQuery(name = "Trova_tutto_Book", query = "SELECT a FROM Book a")
public class Book extends Element {

    @Column(name = "autore")
    private String autore;

    @Column(name = "genere")
    private String genere;

}
