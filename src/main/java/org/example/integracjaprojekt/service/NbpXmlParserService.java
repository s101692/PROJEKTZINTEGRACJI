package org.example.integracjaprojekt.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.example.integracjaprojekt.model.StopaReferencyjna;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NbpXmlParserService {

    /**
     * Wymóg nr 1: Parsowanie przy użyciu DOM oraz wybór wartości za pomocą XPath
     */
    public List<StopaReferencyjna> pobierzStopyDomXPath() {
        List<StopaReferencyjna> stopy = new ArrayList<>();

        // Wczytanie pliku z folderu resources
        try (InputStream is = getClass().getResourceAsStream("/stopy_nbp.xml")) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            // Użycie XPath do wyciągnięcia konkretnych węzłów
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/stopy/pozycja";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                String dataStr = (String) xPath.compile("data").evaluate(nodeList.item(i), XPathConstants.STRING);
                String wartoscStr = (String) xPath.compile("wartosc").evaluate(nodeList.item(i), XPathConstants.STRING);

                StopaReferencyjna stopa = new StopaReferencyjna();
                stopa.setDataObowiazywania(LocalDate.parse(dataStr));
                stopa.setWartoscProcentowa(Double.parseDouble(wartoscStr));
                stopy.add(stopa);
            }
        } catch (Exception e) {
            System.err.println("Błąd parsowania DOM/XPath: " + e.getMessage());
        }
        return stopy;
    }

    /**
     * Wymóg nr 2: Parsowanie przy użyciu lekkiego, strumieniowego API (SAX)
     */
    public List<StopaReferencyjna> pobierzStopySax() {
        List<StopaReferencyjna> stopy = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream("/stopy_nbp.xml")) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                StopaReferencyjna aktualnaStopa = null;
                String aktualnyElement = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    aktualnyElement = qName;
                    if (qName.equalsIgnoreCase("pozycja")) {
                        aktualnaStopa = new StopaReferencyjna();
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    String zawartosc = new String(ch, start, length).trim();
                    if (zawartosc.isEmpty() || aktualnaStopa == null) return;

                    if (aktualnyElement.equalsIgnoreCase("data")) {
                        aktualnaStopa.setDataObowiazywania(LocalDate.parse(zawartosc));
                    } else if (aktualnyElement.equalsIgnoreCase("wartosc")) {
                        aktualnaStopa.setWartoscProcentowa(Double.parseDouble(zawartosc));
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase("pozycja") && aktualnaStopa != null) {
                        stopy.add(aktualnaStopa);
                        aktualnaStopa = null; // Reset obiektu po zakończeniu czytania węzła
                    }
                    aktualnyElement = "";
                }
            };

            saxParser.parse(is, handler);
        } catch (Exception e) {
            System.err.println("Błąd parsowania SAX: " + e.getMessage());
        }
        return stopy;
    }
}