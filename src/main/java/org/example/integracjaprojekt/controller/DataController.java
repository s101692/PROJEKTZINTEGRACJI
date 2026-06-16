package org.example.integracjaprojekt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.example.integracjaprojekt.dto.FactsXmlResponse;
import org.example.integracjaprojekt.service.DataService;
import org.example.integracjaprojekt.model.FaktZintegrowany;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
public class DataController {

    private final DataService service;

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FaktZintegrowany> json(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo
    ) {
        return service.getFiltered(region, yearFrom, yearTo);
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public List<FaktZintegrowany> xml(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo
    ) {
        return service.getFiltered(region, yearFrom, yearTo);
    }

    @GetMapping(
            value = "/json/download")
    public ResponseEntity<byte[]> downloadJson()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        byte[] content =
                mapper.writeValueAsBytes(
                        service.getFiltered(null, null, null));

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fakty.json")
                .contentType(
                        MediaType.APPLICATION_JSON)
                .body(content);
    }

    @GetMapping("/xml/download")
    public ResponseEntity<byte[]> downloadXml()
            throws Exception {

        XmlMapper mapper = new XmlMapper();

        FactsXmlResponse response =
                new FactsXmlResponse(
                        service.getFiltered(null, null, null));

        byte[] content =
                mapper.writeValueAsBytes(response);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fakty.xml")
                .contentType(
                        MediaType.APPLICATION_XML)
                .body(content);
    }
}