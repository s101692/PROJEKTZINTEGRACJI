package org.example.integracjaprojekt.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.example.integracjaprojekt.service.IntegracjaDanychService;
import org.example.integracjaprojekt.service.ZapisDanychService;

@Component
public class DataLoader implements CommandLineRunner {

    private final ZapisDanychService zapisDanychService;
    private final IntegracjaDanychService integracjaDanychService;

    public DataLoader(ZapisDanychService zapisDanychService, IntegracjaDanychService integracjaDanychService) {
        this.zapisDanychService = zapisDanychService;
        this.integracjaDanychService = integracjaDanychService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- 1. Uruchamianie procedury startowej zasilania bazy danych ---");
        zapisDanychService.pobierzIZapiszWszystko();

        System.out.println("--- 2. Rozpoczynanie agregacji i integracji ---");
        integracjaDanychService.budujFaktyZintegrowane();

        System.out.println("--- START ZAKOŃCZONY POMYŚLNIE ---");
    }
}