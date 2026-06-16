package org.example.integracjaprojekt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fakty_zintegrowane")
public class FaktZintegrowany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rok;

    private Integer kwartal;
    private String wojewodztwo;

    private Double sredniaCenaMieszkania;
    private Double sredniaStopaWKwarcie;
}