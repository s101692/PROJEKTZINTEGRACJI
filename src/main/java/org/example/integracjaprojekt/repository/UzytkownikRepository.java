package org.example.integracjaprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.integracjaprojekt.model.Uzytkownik;
import java.util.Optional;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {
    Optional<Uzytkownik> findByNazwaUzytkownika(String nazwaUzytkownika);
}
