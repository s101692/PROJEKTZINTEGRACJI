package org.example.integracjaprojekt.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.example.integracjaprojekt.dto.GusResponseDto;
import org.example.integracjaprojekt.dto.GusResultDto;
import org.example.integracjaprojekt.dto.GusValueDto;
import org.example.integracjaprojekt.model.CenaMieszkania;

import java.util.ArrayList;
import java.util.List;

@Service
public class GusApiService {

    private final RestTemplate restTemplate;

    public GusApiService() {
        this.restTemplate = new RestTemplate();
    }

    public List<CenaMieszkania> pobierzIDeserializujDaneGus() {
        // Przykładowy adres API
        String url = "https://bdl.stat.gov.pl/api/v1/data/by-variable/450283?unit-level=2&page-size=100";

        try {
            // Spring automatycznie parsuje JSON z URL na obiekt GusResponseDto
            GusResponseDto response = restTemplate.getForObject(url, GusResponseDto.class);

            List<CenaMieszkania> listaMieszkan = new ArrayList<>();

            if (response != null && response.getResults() != null) {
                // Mapowanie DTO na Encję bazodanową
                for (GusResultDto result : response.getResults()) {
                    for (GusValueDto value : result.getValues()) {
                        CenaMieszkania cena = new CenaMieszkania();
                        cena.setWojewodztwo(result.getName());
                        cena.setRok(value.getYear());
                        cena.setKwartal(1);
                        cena.setCenaZaMetr(value.getVal());

                        listaMieszkan.add(cena);
                    }
                }
            }
            System.out.println("Zdeserializowano poprawnie " + listaMieszkan.size() + " rekordów o cenach mieszkań.");
            return listaMieszkan;
        } catch (Exception e) {
            System.err.println("Błąd podczas deserializacji JSON z GUS: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}