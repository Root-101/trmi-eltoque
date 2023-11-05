package dev.root101.trmi_eltoque.feature;

import static dev.root101.trmi_eltoque.App.DATE_FORMATTER;
import dev.root101.trmi_eltoque.feature.data.TrmiEntity;
import dev.root101.trmi_eltoque.feature.data.TrmiRepo;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueClient;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueDomain;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

    private final String start = "2021-01-01 00:00:00";

    private final List<LocalDate> daysWithHourChange = List.of(
            ZonedDateTime.from(DATE_FORMATTER.parse("2021-11-07 00:00:00")).toLocalDate(),
            ZonedDateTime.from(DATE_FORMATTER.parse("2022-11-07 00:00:00")).toLocalDate(),
            ZonedDateTime.from(DATE_FORMATTER.parse("2023-11-05 00:00:00")).toLocalDate()
    );

    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateRegistery() {
        try {
            TrmiEntity latest = repo.findFirstByOrderByRegisterDateDesc();

            ZonedDateTime from;

            //si no lo encontre en la tabla es porque esta vacio, pongo el inicial inicial
            if (latest == null) {
                from = ZonedDateTime.from(DATE_FORMATTER.parse(start));
            } else {
                //si esta en la tabla cojo el inicial por este, y el from es 24h antes
                from = latest.getRegisterDate().minus(24, ChronoUnit.HOURS);

                //le agrego una hora para que sea el next item
                from = from.plus(1, ChronoUnit.HOURS);
            }

            //creo el 'to' como 24h despues
            ZonedDateTime to = from.plus(24, ChronoUnit.HOURS);

            if (daysWithHourChange.contains(to.toLocalDate())) {
                //si es un dia de cambio de horario, para hora normal, agrego una hora porque si no se va del rango de las 24h
                from = from.plusHours(1);
                from = from.plusSeconds(1);
            } else {
                //ajusto el from a +61 seg (un min y un seg) para que este dentro del rango de las 24h
                from = from.plusSeconds(1);
            }

            System.out.println("Buscando registro: fecha %s a %s".formatted(DATE_FORMATTER.format(from), DATE_FORMATTER.format(to)));

            ElToqueDomain response = elToque.trmi(from, to);

            System.out.println(response);

            latest = repo.save(
                    new TrmiEntity(
                            null,
                            response.getEUR(),
                            response.getUSD(),
                            response.getMLC(),
                            to,
                            ZonedDateTime.now()
                    )
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
