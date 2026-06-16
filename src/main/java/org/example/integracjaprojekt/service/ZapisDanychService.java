package org.example.integracjaprojekt.service;

import org.springframework.stereotype.Service;
import org.example.integracjaprojekt.model.CenaMieszkania;
import org.example.integracjaprojekt.model.StopaReferencyjna;
import org.example.integracjaprojekt.repository.CenaMieszkaniaRepository;
import org.example.integracjaprojekt.repository.StopaReferencyjnaRepository;

import java.util.List;

@Service
public class ZapisDanychService {

    private final GusApiService gusApiService;
    private final NbpXmlParserService nbpXmlParserService;
    private final CenaMieszkaniaRepository cenaMieszkaniaRepository;
    private final StopaReferencyjnaRepository stopaReferencyjnaRepository;

    // Automatyczne wstrzykiwanie zależności przez Spring (Dependency Injection)
    public ZapisDanychService(GusApiService gusApiService,
                              NbpXmlParserService nbpXmlParserService,
                              CenaMieszkaniaRepository cenaMieszkaniaRepository,
                              StopaReferencyjnaRepository stopaReferencyjnaRepository) {
        this.gusApiService = gusApiService;
        this.nbpXmlParserService = nbpXmlParserService;
        this.cenaMieszkaniaRepository = cenaMieszkaniaRepository;
        this.stopaReferencyjnaRepository = stopaReferencyjnaRepository;
    }

    public void pobierzIZapiszWszystko() {
        // 1. Pobranie i zapis danych ustrukturyzowanych z GUS (JSON)
        System.out.println("Rozpoczynam procesowanie danych z GUS...");
        List<CenaMieszkania> ceny = gusApiService.pobierzIDeserializujDaneGus();

        if (ceny != null && !ceny.isEmpty()) {
            // saveAll to wbudowana metoda Spring Data JPA, która generuje masowe zapytania INSERT
            cenaMieszkaniaRepository.saveAll(ceny);
            System.out.println("SUKCES: Zapisano " + ceny.size() + " rekordów cen mieszkań do tabeli.");
        }

        // 2. Pobranie i zapis danych statycznych z NBP (XML)
        System.out.println("Rozpoczynam procesowanie pliku XML z NBP...");
        List<StopaReferencyjna> stopy = nbpXmlParserService.pobierzStopyDomXPath();

        if (stopy != null && !stopy.isEmpty()) {
            stopaReferencyjnaRepository.saveAll(stopy);
            System.out.println("SUKCES: Zapisano " + stopy.size() + " rekordów stóp referencyjnych do tabeli.");
        }
    }
}