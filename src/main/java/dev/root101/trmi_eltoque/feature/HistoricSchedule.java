package dev.root101.trmi_eltoque.feature;

import dev.root101.trmi_eltoque.feature.data.TrmiEntity;
import dev.root101.trmi_eltoque.feature.data.TrmiRepo;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueClient;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueDomain;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HistoricSchedule {

    @Autowired
    private ElToqueClient elToque;

    @Autowired
    private TrmiRepo repo;

    @Autowired
    private DateTimeFormatter DATE_FORMATTER;

    private TrmiEntity latest = null;
    private final String start = "2020-12-31 19:00:00";

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateRegistery() {
        try {
            //si no tengo ninguno de ultimo, lo busco en la tabla
            if (latest == null) {
                latest = repo.findFirstByOrderByRegisterDateDesc();
            }

            Instant from;

            //si no lo encontre en la tabla es porque esta vacio, pongo el inicial inicial
            if (latest == null) {
                from = Instant.from(DATE_FORMATTER.parse(start));
            } else {
                //si esta en la tabla cojo el inicial por este, y el from es 24h antes
                from = latest.getRegisterDate().minus(24, ChronoUnit.HOURS);

                //le agrego una hora para que sea el next item
                from = from.plus(1, ChronoUnit.HOURS);
            }

            //creo el 'to' como 24h despues
            Instant to = from.plus(24, ChronoUnit.HOURS);
            //ajusto el from a +1 seg para que este dentro del rango de las 24h
            from = from.plusSeconds(1);

            System.out.println("Buscando registro: fecha %s a %s".formatted(from, to));

            ElToqueDomain response = elToque.trmi(from, to);

            System.out.println(response);

            latest = repo.save(
                    new TrmiEntity(
                            null,
                            response.getEUR(),
                            response.getUSD(),
                            response.getMLC(),
                            to,
                            Instant.now()
                    )
            );

        } catch (Exception e) {
            latest = null;//si da error quito el latest para que se vaya a buscar a la BD
            System.out.println(e.getMessage());
        }
    }

}
