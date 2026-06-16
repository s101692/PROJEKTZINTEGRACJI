package org.example.integracjaprojekt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.integracjaprojekt.model.CenaMieszkania;
import org.example.integracjaprojekt.model.FaktZintegrowany;
import org.example.integracjaprojekt.model.StopaReferencyjna;
import org.example.integracjaprojekt.repository.CenaMieszkaniaRepository;
import org.example.integracjaprojekt.repository.FaktZintegrowanyRepository;
import org.example.integracjaprojekt.repository.StopaReferencyjnaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntegracjaDanychService {

    private final CenaMieszkaniaRepository cenaRepo;
    private final StopaReferencyjnaRepository stopaRepo;
    private final FaktZintegrowanyRepository faktRepo;

    public IntegracjaDanychService(CenaMieszkaniaRepository cenaRepo,
                                   StopaReferencyjnaRepository stopaRepo,
                                   FaktZintegrowanyRepository faktRepo) {
        this.cenaRepo = cenaRepo;
        this.stopaRepo = stopaRepo;
        this.faktRepo = faktRepo;
    }

    // Adnotacja @Transactional realizuje wymóg z Kroku A11 (Zapis jako jedna transakcja ACID)
    @Transactional
    public void budujFaktyZintegrowane() {
        System.out.println("Rozpoczynam integrację danych i budowę hurtowni (Krok A9/A10)...");

        // 1. Pobranie wszystkich surowych danych z bazy
        List<CenaMieszkania> ceny = cenaRepo.findAll();
        List<StopaReferencyjna> stopy = stopaRepo.findAll();

        // 2. Normalizacja stóp NBP (Sprowadzenie dat dziennych do średniej kwartalnej)
        Map<String, List<Double>> stopyKwartalne = new HashMap<>();
        for (StopaReferencyjna stopa : stopy) {
            int rok = stopa.getDataObowiazywania().getYear();
            int kwartal = (stopa.getDataObowiazywania().getMonthValue() - 1) / 3 + 1;
            String klucz = rok + "-" + kwartal; // Np. "2023-3"

            boolean add = stopyKwartalne.computeIfAbsent(klucz, k -> new ArrayList<>()).add(stopa.getWartoscProcentowa());
        }

        // Obliczenie średniej dla każdego kwartału
        Map<String, Double> srednieStopy = new HashMap<>();
        for (Map.Entry<String, List<Double>> wpis : stopyKwartalne.entrySet()) {
            double suma = wpis.getValue().stream().mapToDouble(Double::doubleValue).sum();
            double srednia = suma / wpis.getValue().size();
            srednieStopy.put(wpis.getKey(), srednia);
        }

        // 3. Budowa Faktu Zintegrowanego (Operacja JOIN w pamięci)
        List<FaktZintegrowany> fakty = new ArrayList<>();
        for (CenaMieszkania cena : ceny) {
            String kluczSzukania = cena.getRok() + "-" + cena.getKwartal();

            // Pobranie dopasowanej stopy. Jeśli brak danych dla danego kwartału w XML, domyślnie wstawiamy 0.0
            Double dopasowanaStopa = srednieStopy.getOrDefault(kluczSzukania, 0.0);

            FaktZintegrowany fakt = new FaktZintegrowany();
            fakt.setRok(cena.getRok());
            fakt.setKwartal(cena.getKwartal());
            fakt.setWojewodztwo(cena.getWojewodztwo());
            fakt.setSredniaCenaMieszkania(cena.getCenaZaMetr());
            fakt.setSredniaStopaWKwarcie(dopasowanaStopa);

            fakty.add(fakt);
        }

        // 4. Trwały zapis wyników integracji do bazy
        faktRepo.deleteAll(); // Czyszczenie starych faktów przed nowym przeliczeniem
        faktRepo.saveAll(fakty);
        System.out.println("SUKCES: Utworzono i zapisano " + fakty.size() + " zintegrowanych faktów analitycznych.");
    }
}