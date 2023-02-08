package dev.root101.trmi_eltoque;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Schedule {

    @Autowired
    private UseCase useCase;

    @Scheduled(fixedDelay = 1, initialDelay = 0, timeUnit = TimeUnit.MINUTES)
    public void mailToInactiveUsers() {
        try {
            System.out.println("Actualizando valores a las %s".formatted(Instant.now()));
            useCase.updateTrmi();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
