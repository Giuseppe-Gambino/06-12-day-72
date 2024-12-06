package it.epicode.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@NamedQuery(name="Trova_tutto_Element", query="SELECT a FROM Element a")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "anno_di_publicazione")
    private Integer annoDiPublicazione;

    @Column(name = "numero_di_pagine")
    private Integer numeroDiPagine;



}
