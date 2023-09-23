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
 * Es tipo 'protected' para que no la puedan llamar fuera del paquete, que se
 * trabaje por la interfaz.
 *
 * @author Yo
 */
@Service
class ElToqueClientImpl implements ElToqueClient {

    private final static String USD_KEY = "USD";
    private final static String EUR_KEY = "ECU";
    private final static String MLC_KEY = "MLC";

    /**
     * Toquen con el que se hacen las peticiones a la api.
     */
    @Value("${trmi.eltoque.auth_token}")
    private String elToqueToken;

    /**
     * Url de la api.
     *
     * Como tiene un solo endpoint se trabaja una unica url.
     */
    @Value("${trmi.eltoque.url}")
    private String url;

    /**
     * Key del header de la fecha inicial
     */
    @Value("${trmi.eltoque.url.header.date_from}")
    private String header_dateFrom;

    /**
     * Key del header de la fecha final
     */
    @Value("${trmi.eltoque.url.header.date_to}")
    private String header_dateTo;

    /**
     * Formatter para convertir un Instant al formato de fecha que pide la api.
     */
    @Autowired
    DateTimeFormatter DATE_FORMATTER;

    /**
     * Template para hacer las peticiones.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Hace la peticion de obtener la Tasa Representativa del Mercado Informal
     * en un rango de tiempo determinado.
     *
     * @param fromInstant
     * @param toInstant
     * @return
     */
    @Override
    public ElToqueDomain trmi(Instant fromInstant, Instant toInstant) {
        //inicializa los headers y la autenticacion
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(elToqueToken);

        //crea el entity de la peticion, como es un get no tiene body, solo headers
        HttpEntity entity = new HttpEntity(headers);

        //formateo las fechas de Instant a String
        String from = DATE_FORMATTER.format(fromInstant);
        String to = DATE_FORMATTER.format(toInstant);

        //Hago la peticion y capturo la data cruda
        ResponseEntity<ElToqueResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ElToqueResponse.class,
                Map.of(
                        header_dateFrom, from,
                        header_dateTo, to
                )
        );

        //Convierto la data cruda en un objeto responsable
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
