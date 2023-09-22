/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.root101.trmi_eltoque;

import dev.root101.trmi_eltoque.model.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Yo
 */
@Service
public class Client {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("GMT-0"));

    @Value("${trmi.eltoque.auth_token}")
    private String elToqueToken;

    @Value("${trmi.eltoque.url}")
    private String url;

    @Value("${trmi.eltoque.url.header.date_from}")
    private String hedaer_dateFrom;

    @Value("${trmi.eltoque.url.header.date_to}")
    private String hedaer_dateTo;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<ElToque_Response> trmi(Instant fromInstant, Instant toInstant) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(elToqueToken);

        HttpEntity entity = new HttpEntity(headers);

        String from = DATE_FORMATTER.format(fromInstant);
        String to = DATE_FORMATTER.format(toInstant);

        System.out.println("from: %s  -  to: %s".formatted(from, to));
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ElToque_Response.class,
                Map.of(
                        hedaer_dateFrom, from,
                        hedaer_dateTo, to
                )
        );
    }
}
