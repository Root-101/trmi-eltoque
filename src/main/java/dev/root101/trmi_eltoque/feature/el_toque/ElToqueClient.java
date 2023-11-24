package dev.root101.trmi_eltoque.feature.el_toque;

import java.time.ZonedDateTime;

/**
 * Interfaz para el trabajo con la api de El Toque
 *
 * @author Yo
 */
public interface ElToqueClient {

    public ElToqueDomain trmi(ZonedDateTime fromInstant, ZonedDateTime toInstant);
}
