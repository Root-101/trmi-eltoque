package dev.root101.trmi_eltoque;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class App {

    @Bean
    public DateTimeFormatter DATE_FORMATTER(@Value("${trmi.eltoque.timezone}") String timezone) {
        return DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
                .withZone(ZoneId.of(timezone));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }
}
