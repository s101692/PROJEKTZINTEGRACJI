package org.example.integracjaprojekt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GusResponseDto {
    private List<GusResultDto> results;
}