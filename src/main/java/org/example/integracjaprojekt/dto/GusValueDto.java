package org.example.integracjaprojekt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GusValueDto {
    private Integer year;
    private Double val;
}