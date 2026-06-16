package org.example.integracjaprojekt.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "stopy_referencyjne")
public class StopaReferencyjna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataObowiazywania;

    @Column(nullable = false)
    private Double wartoscProcentowa;
}