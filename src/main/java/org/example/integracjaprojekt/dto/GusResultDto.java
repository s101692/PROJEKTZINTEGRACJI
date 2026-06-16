package org.example.integracjaprojekt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GusResultDto {
    private String name; // Pobiera nazwę województwa
    private List<GusValueDto> values;
}