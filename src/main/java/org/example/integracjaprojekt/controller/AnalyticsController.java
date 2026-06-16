package org.example.integracjaprojekt.controller;

import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.dto.CorrelationDto;
import org.example.integracjaprojekt.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/correlation/region")
    public CorrelationDto correlationRegion(
            @RequestParam String region) {

        return analyticsService
                .calculateRegionCorrelation(region);
    }
}