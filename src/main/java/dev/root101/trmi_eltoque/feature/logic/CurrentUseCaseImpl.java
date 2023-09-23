package dev.root101.trmi_eltoque.feature.logic;

import static dev.root101.trmi_eltoque.App.DATE_FORMATTER;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueDomain;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueClient;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * De tipo 'protected' para que solo se utilice la interfaz.
 *
 * @author Yo
 */
@Service
class CurrentUseCaseImpl implements CurrentUseCase {

    @Autowired
    private ElToqueClient elToque;

    private ElToqueDomain current;

    @Override
    public ElToqueDomain current() {
        return current;
    }

    /**
     * Cuando inicial servicio ejecuta y actualiza la tasa por primera vez.
     *
     * De ahi en adelante se actualiza cada X tiempo.
     */
    //docs del crono: https://reflectoring.io/spring-scheduler/
    //@Scheduled(initialDelay = 0, fixedDelay = 720, timeUnit = TimeUnit.MINUTES)//720 min = 12h
    public void onSchedule() {
        System.out.println("onSchedule %s".formatted(Instant.now()));
        update();
    }

    /**
     * Actualiza el valor actual con el rango [now, now - 1h]
     *
     * @param from
     * @param to
     * @return
     */
    public ElToqueDomain update() {
        try {
            ZonedDateTime to = ZonedDateTime.now();

            //ni idea porque, pero hay que hacerlo para que lo ajuste bien
            to = to.plus(1, ChronoUnit.HOURS);

            //cuanto tiempo antes es que va a actualizar
            ZonedDateTime from = to.minus(24, ChronoUnit.HOURS);

            //ajusto el from a +1 seg para que este dentro del rango de las 24h
            from = from.plusSeconds(1);

            System.out.println("Actualizando actual: fecha %s a %s".formatted(DATE_FORMATTER.format(from), DATE_FORMATTER.format(to)));

            ElToqueDomain updated = elToque.trmi(from, to);

            if (updated.getEUR() == null) {
                System.out.println("WARNING: EUR es null");
            }
            if (updated.getUSD() == null) {
                System.out.println("WARNING: USD es null");
            }
            if (updated.getMLC() == null) {
                System.out.println("WARNING: MLC es null");
            }

            this.current = updated;

            return this.current;
        } catch (Exception e) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Error actualizando los valores.");
            System.out.println(Instant.now());
            System.out.println(e.getMessage());
        }

        return null;
    }

}
