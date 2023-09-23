package dev.root101.trmi_eltoque.feature.el_toque;

import java.util.Map;
import lombok.*;

/**
 * Respuesta cruda que da la api de El Toque.
 *
 * Ejemplo de respuesta:
 *
 * <pre>
 * {
 *      "tasas": {
 *          "BTC": 160.0,
 *          "USD": 163.0,
 *          "TRX": 161.9,
 *          "ECU": 165.0,
 *          "USDT_TRC20": 153.8,
 *          "MLC": 156.0
 *      },
 *      "date": "2023-02-07",
 *      "hour": 9,
 *      "minutes": 18,
 *      "seconds": 17
 * }
 * </pre>
 *
 * @author Yo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElToqueResponse {

    /**
     * Array con las tasas:
     */
    private Map<String, String> tasas;

    /**
     * Fecha en que se realizo la consulta a la API
     */
    private String date;

    /**
     * Hora en que se realizo la consulta a la API
     */
    private int hour;

    /**
     * Minuto en que se realizo la consulta a la API
     */
    private int minutes;

    /**
     * Segundo en que se realizo la consulta a la API
     */
    private int seconds;

}
