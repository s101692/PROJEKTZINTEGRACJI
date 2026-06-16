package org.example.integracjaprojekt.service;

import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.repository.FaktZintegrowanyRepository;
import org.example.integracjaprojekt.model.FaktZintegrowany;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataService {

    private final FaktZintegrowanyRepository repository;

    public List<FaktZintegrowany> getFiltered(String region, Integer yearFrom, Integer yearTo) {
        if (region != null && yearFrom != null && yearTo != null) {
            return repository
                    .findByWojewodztwoAndRokBetweenOrderByRokAscKwartalAsc(
                            region, yearFrom, yearTo
                    );
        }
        if (yearFrom != null && yearTo != null) {
            return repository.findByRokBetweenOrderByRokAscKwartalAsc(yearFrom, yearTo);
        }
        if (region != null) {
            return repository.findByWojewodztwoOrderByRokAscKwartalAsc(region);
        }
        return repository.findAll();
    }
}