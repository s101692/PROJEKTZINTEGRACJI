package org.example.integracjaprojekt.service;

import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.dto.CorrelationDto;
import org.example.integracjaprojekt.model.FaktZintegrowany;
import org.example.integracjaprojekt.repository.FaktZintegrowanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final FaktZintegrowanyRepository repository;
    private double pearsonCorrelation(
            List<Double> x,
            List<Double> y) {

        int n = x.size();

        double meanX =
                x.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0);

        double meanY =
                y.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0);

        double numerator = 0;
        double sumX = 0;
        double sumY = 0;

        for (int i = 0; i < n; i++) {

            double dx = x.get(i) - meanX;
            double dy = y.get(i) - meanY;

            numerator += dx * dy;
            sumX += dx * dx;
            sumY += dy * dy;
        }

        if (sumX == 0 || sumY == 0) {
            return 0.0;
        }

        return numerator /
                Math.sqrt(sumX * sumY);
    }
    public CorrelationDto calculateRegionCorrelation(
            String region) {

        List<FaktZintegrowany> data;
        if (region == null || region.isBlank()) {
            data = repository.findAll();
        } else {
            data = repository.findByWojewodztwoOrderByRokAscKwartalAsc(region);
        }
        List<Double> prices =
                data.stream()
                        .map(FaktZintegrowany::getSredniaCenaMieszkania)
                        .toList();

        List<Double> rates =
                data.stream()
                        .map(FaktZintegrowany::getSredniaStopaWKwarcie)
                        .toList();

        double correlation =
                pearsonCorrelation(
                        prices,
                        rates);

        return new CorrelationDto(
                "REGION",
                region,
                correlation,
                "Pearson correlation"
        );
    }
}