package dev.root101.trmi_eltoque.feature.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.root101.commons.rest.ApiResponse;
import dev.root101.trmi_eltoque.feature.el_toque.ElToqueDomain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.root101.trmi_eltoque.feature.logic.CurrentUseCase;

@Slf4j
@Tag(name = "Test Controller", description = "Test")
@RestController
@RequestMapping("/trmi")
public class CurrentController {

    @Autowired
    private CurrentUseCase useCase;

    @GetMapping("/current")
    @Operation(
            summary = "Consulta la tasa actual.",
            description = "Decuelve la tasa actual."
    )
    public ApiResponse<CurrentResponse> current() {
        return ApiResponse.success(
                CurrentResponse.build(
                        useCase.current()
                )
        );
    }

    public record CurrentResponse(
            @JsonProperty("usd")
            String usd,
            @JsonProperty("eur")
            String eur,
            @JsonProperty("mlc")
            String mlc,
            @JsonProperty("updated_at")
            String updatedAt) {

        public static CurrentResponse build(ElToqueDomain domain) {
            return new CurrentResponse(
                    domain.getUSD() == null ? null : domain.getUSD().toString(),
                    domain.getEUR() == null ? null : domain.getEUR().toString(),
                    domain.getMLC() == null ? null : domain.getMLC().toString(),
                    domain.getRequestedAt().toString()
            );
        }
    }
}
