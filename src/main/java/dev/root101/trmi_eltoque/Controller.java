package dev.root101.trmi_eltoque;

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
    public Domain trmi() {
        return useCase.getTrmi();
    }
}
