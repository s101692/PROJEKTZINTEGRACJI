package org.example.integracjaprojekt.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HarmonogramService {

    private final ZapisDanychService zapisDanychService;
    private final IntegracjaDanychService integracjaDanychService;

    public HarmonogramService(ZapisDanychService zapisDanychService,
                              IntegracjaDanychService integracjaDanychService) {
        this.zapisDanychService = zapisDanychService;
        this.integracjaDanychService = integracjaDanychService;
    }

    // Metoda odpala się automatycznie co 60 sekund
    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void cykliczneOdswiezanieDanych() {
        System.out.println("=========================================================");
        System.out.println(">>> URUCHOMIENIE HARMONOGRAMU: Automatyczna aktualizacja bazy...");
        try {
            zapisDanychService.pobierzIZapiszWszystko();
            integracjaDanychService.budujFaktyZintegrowane();
            System.out.println(">>> HARMONOGRAM ZAKOŃCZONY SUKCESEM <<<");
        } catch (Exception e) {
            System.err.println(">>> BŁĄD HARMONOGRAMU: " + e.getMessage());
        }
        System.out.println("=========================================================");
    }
}