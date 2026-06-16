package org.example.integracjaprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.integracjaprojekt.model.CenaMieszkania;
import java.util.List;

public interface CenaMieszkaniaRepository extends JpaRepository<CenaMieszkania, Long> {
    List<CenaMieszkania> findByWojewodztwoAndRokAndKwartal(String wojewodztwo, Integer rok, Integer kwartal);
}
