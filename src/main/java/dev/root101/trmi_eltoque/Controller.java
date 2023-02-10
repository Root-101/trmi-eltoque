package dev.root101.trmi_eltoque;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.root101.trmi_eltoque.model.Domain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Test Controller", description = "Test")
@RestController
@RequestMapping("/trmi")
public class Controller {

    @Autowired
    private UseCase useCase;

    @GetMapping("/current")
    @Operation(
            summary = "Consulta la tasa actual.",
            description = "Decuelve la tasa actual."
    )
    public Response trmi() {
        return Response.build(
                useCase.getTrmi()
        );
    }

    public record Response(
            @JsonProperty("usd")
            String usd,
            @JsonProperty("eur")
            String eur,
            @JsonProperty("mlc")
            String mlc) {

        public static Response build(Domain domain) {
            return new Response(
                    domain.getUSD().toString(),
                    domain.getEUR().toString(),
                    domain.getMLC().toString()
            );
        }
    }
}
