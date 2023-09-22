package dev.root101.trmi_eltoque;

import dev.root101.trmi_eltoque.model.Domain;
import dev.root101.trmi_eltoque.model.ElToque_Response;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UseCase {

    private final static String USD_KEY = "USD";
    private final static String EUR_KEY = "ECU";
    private final static String MLC_KEY = "MLC";

    @Autowired
    private Client client;

    public Domain updateTrmi() {
        try {
            ResponseEntity<ElToque_Response> response = client.trmi(
                    Instant.parse("2023-09-22T10:01:00.00Z"),
                    Instant.parse("2023-09-22T11:00:00.00Z")
            );
            Domain convert = convert(response.getBody());

            System.out.println("Valores actualizados OK." + convert);
            return convert;
        } catch (Exception e) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Error actualizando los valores.");
            System.out.println(Instant.now());
            System.out.println(e.getMessage());
        }

        return null;
    }

    private Domain convert(ElToque_Response response) {
        BigDecimal USD = response.getTasas().containsKey(USD_KEY)
                ? new BigDecimal(response.getTasas().get(USD_KEY))
                : null;

        BigDecimal EUR = response.getTasas().containsKey(EUR_KEY)
                ? new BigDecimal(response.getTasas().get(EUR_KEY))
                : null;

        BigDecimal MLC = response.getTasas().containsKey(MLC_KEY)
                ? new BigDecimal(response.getTasas().get(MLC_KEY))
                : null;

        return new Domain(USD, EUR, MLC);
    }
}
