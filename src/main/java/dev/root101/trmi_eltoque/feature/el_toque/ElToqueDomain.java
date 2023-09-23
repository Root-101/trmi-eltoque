package dev.root101.trmi_eltoque.feature.el_toque;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

/**
 * Data completamente formateada de la peticion del toque.
 *
 * Nota: Alguno de los valores de USD, EUR o MLC puede ser null si no se
 * encuentra el valor cuando se hace la peticion.
 *
 * @author Yo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElToqueDomain {

    private BigDecimal USD;

    private BigDecimal EUR;

    private BigDecimal MLC;

    private Instant from;
    private Instant to;
    private Instant requestedAt;
}
