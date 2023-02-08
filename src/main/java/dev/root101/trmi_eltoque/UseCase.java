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

    private final Domain currentValues = new Domain(
            null,
            null,
            null/*,
            Instant.now()*/
    );

    public Domain getTrmi() {
        return currentValues;
    }

    public Domain updateTrmi() {
        try {
            ResponseEntity<ElToque_Response> response = client.trmi();
            Domain convert = convert(response.getBody());

            //update USD
            if (convert.getUSD() != null) {
                currentValues.setUSD(convert.getUSD());
            } else {
                System.out.println("USD NULL");
            }

            //update EUR
            if (convert.getEUR() != null) {
                currentValues.setEUR(convert.getEUR());
            } else {
                System.out.println("EUR NULL");
            }

            //update MLC
            if (convert.getMLC() != null) {
                currentValues.setMLC(convert.getMLC());
            } else {
                System.out.println("MLC NULL");
            }

            //currentValues.setLastUpdatedAt(convert.getLastUpdatedAt());
            System.out.println("Valores actualizados OK OK." + currentValues);
        } catch (Exception e) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Error actualizando los valores.");
            System.out.println(Instant.now());
            System.out.println(e.getMessage());
        }

        return currentValues;
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

        return new Domain(USD, EUR, MLC/*, Instant.now()*/);
    }
}
