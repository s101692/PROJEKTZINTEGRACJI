package org.example.integracjaprojekt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ceny_mieszkan")
public class CenaMieszkania {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wojewodztwo;

    @Column(nullable = false)
    private Integer rok;

    @Column(nullable = false)
    private Integer kwartal;

    @Column(nullable = false)
    private Double cenaZaMetr;
}