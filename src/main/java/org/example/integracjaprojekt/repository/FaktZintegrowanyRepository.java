package org.example.integracjaprojekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.integracjaprojekt.model.FaktZintegrowany;
import java.util.List;

public interface FaktZintegrowanyRepository extends JpaRepository<FaktZintegrowany, Long> {
    List<FaktZintegrowany> findByWojewodztwoOrderByRokAscKwartalAsc(String wojewodztwo);
    List<FaktZintegrowany> findByRokBetweenOrderByRokAscKwartalAsc(Integer rokOd, Integer rokDo);
    List<FaktZintegrowany> findByWojewodztwoAndRokBetweenOrderByRokAscKwartalAsc(String wojewodztwo, Integer rokOd, Integer rokDo);
}
