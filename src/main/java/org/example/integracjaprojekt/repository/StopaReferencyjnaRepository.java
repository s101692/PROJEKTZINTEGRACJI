package org.example.integracjaprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.integracjaprojekt.model.StopaReferencyjna;
import java.time.LocalDate;
import java.util.List;

public interface StopaReferencyjnaRepository extends JpaRepository<StopaReferencyjna, Long> {
    List<StopaReferencyjna> findByDataObowiazywaniaBetween(LocalDate dataOd, LocalDate dataDo);
}
