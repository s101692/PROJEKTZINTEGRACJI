package org.example.integracjaprojekt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.integracjaprojekt.model.FaktZintegrowany;
import org.example.integracjaprojekt.repository.FaktZintegrowanyRepository;

import java.util.List;

@RestController
@RequestMapping("/api/mieszkania")
public class MieszkaniaController {

    private final FaktZintegrowanyRepository faktRepo;

    public MieszkaniaController(FaktZintegrowanyRepository faktRepo) {
        this.faktRepo = faktRepo;
    }

    // Ten endpoint połączy się z bazą i zwróci wszystkie połączone dane
    @GetMapping("/fakty")
    public List<FaktZintegrowany> pobierzWszystkieFakty() {
        return faktRepo.findAll();
    }
}