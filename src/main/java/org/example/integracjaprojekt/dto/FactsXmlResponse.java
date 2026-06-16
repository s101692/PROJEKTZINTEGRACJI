package org.example.integracjaprojekt.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.example.integracjaprojekt.model.FaktZintegrowany;

import java.util.List;

public class FactsXmlResponse {

    @JacksonXmlProperty(localName = "fakt")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<FaktZintegrowany> fakty;

    public FactsXmlResponse() {
    }

    public FactsXmlResponse(List<FaktZintegrowany> fakty) {
        this.fakty = fakty;
    }

    public List<FaktZintegrowany> getFakty() {
        return fakty;
    }

    public void setFakty(List<FaktZintegrowany> fakty) {
        this.fakty = fakty;
    }
}