package dev.root101.trmi_eltoque.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domain {

    @JsonProperty("usd")
    private BigDecimal USD;

    @JsonProperty("eur")
    private BigDecimal EUR;

    @JsonProperty("mlc")
    private BigDecimal MLC;

    //@JsonProperty("last_updated_at")
    //private Instant lastUpdatedAt;
}
