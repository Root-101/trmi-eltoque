package dev.root101.trmi_eltoque.feature.el_toque;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Para consumir la api de 'el toque'.
 *
 * @author Yo
 */
@Service
public class ElToqueClient {

    private final static String USD_KEY = "USD";
    private final static String EUR_KEY = "ECU";
    private final static String MLC_KEY = "MLC";

    @Value("${trmi.eltoque.auth_token}")
    private String elToqueToken;

    @Value("${trmi.eltoque.url}")
    private String url;

    @Value("${trmi.eltoque.url.header.date_from}")
    private String hedaer_dateFrom;

    @Value("${trmi.eltoque.url.header.date_to}")
    private String hedaer_dateTo;

    @Autowired
    DateTimeFormatter DATE_FORMATTER;

    @Autowired
    private RestTemplate restTemplate;

    public ElToqueDomain trmi(Instant fromInstant, Instant toInstant) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(elToqueToken);

        HttpEntity entity = new HttpEntity(headers);

        String from = DATE_FORMATTER.format(fromInstant);
        String to = DATE_FORMATTER.format(toInstant);

        System.out.println("from: %s  -  to: %s".formatted(from, to));
        ResponseEntity<ElToqueResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ElToqueResponse.class,
                Map.of(
                        hedaer_dateFrom, from,
                        hedaer_dateTo, to
                )
        );
        return convert(response.getBody(), fromInstant, toInstant);
    }

    /**
     * Convierte la data cruda que viene de la api a un objeto mas manejable,
     * con tipos de datos en lugar de strings
     *
     * @param response
     * @param from
     * @param to
     * @return
     */
    private ElToqueDomain convert(ElToqueResponse response, Instant from, Instant to) {
        BigDecimal USD = response.getTasas().containsKey(USD_KEY)
                ? new BigDecimal(response.getTasas().get(USD_KEY))
                : null;

        BigDecimal EUR = response.getTasas().containsKey(EUR_KEY)
                ? new BigDecimal(response.getTasas().get(EUR_KEY))
                : null;

        BigDecimal MLC = response.getTasas().containsKey(MLC_KEY)
                ? new BigDecimal(response.getTasas().get(MLC_KEY))
                : null;

        return new ElToqueDomain(
                USD,
                EUR,
                MLC,
                from,
                to,
                Instant.from(
                        DATE_FORMATTER.parse(
                                "%s %s:%s:%s".formatted(
                                        response.getDate(),
                                        response.getHour(),
                                        response.getMinutes(),
                                        response.getSeconds()
                                )
                        )
                )
        );
    }
}
