package org.example.integracjaprojekt.dto;

public class CorrelationDto {

    private String analysisType;

    private String parameter;

    private Double correlationValue;

    private String description;

    public CorrelationDto() {
    }

    public CorrelationDto(
            String analysisType,
            String parameter,
            Double correlationValue,
            String description) {

        this.analysisType = analysisType;
        this.parameter = parameter;
        this.correlationValue = correlationValue;
        this.description = description;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Double getCorrelationValue() {
        return correlationValue;
    }

    public void setCorrelationValue(Double correlationValue) {
        this.correlationValue = correlationValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}