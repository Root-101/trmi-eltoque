package dev.root101.trmi_eltoque;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
        /*Instant now = Instant.now();
        System.out.println(now);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("GMT-5"));
        System.out.println(formatters.format(now));*/
                    /*String body = """
                      {
                          "tasas": {
                              "BTC": 160.0,
                              "USD": 163.0,
                              "TRX": 161.9,
                              "ECU": 165.0,
                              "USDT_TRC20": 153.8,
                              "MLC": 156.0
                          },
                          "date": "2023-02-07",
                          "hour": 9,
                          "minutes": 18,
                          "seconds": 17
                      }
                      """;

        ElToque_Response response = new ObjectMapper().readValue(body, ElToque_Response.class);
        System.out.println(response);
        System.out.println(LocalDate.parse(response.getDate()));*/
    }
}
