package dev.root101.trmi_eltoque.model;

import java.util.Map;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElToque_Response {

    private Map<String, String> tasas;

    private String date;

    private int hour;
    
    private int minutes;
    
    private int seconds;

}
