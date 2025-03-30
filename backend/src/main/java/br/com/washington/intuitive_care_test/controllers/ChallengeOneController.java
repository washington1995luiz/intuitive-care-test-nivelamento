package br.com.washington.intuitive_care_test.controllers;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;
import br.com.washington.intuitive_care_test.services.ChallengeOneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TESTE DE WEB SCRAPING")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/challenge-one")
public class ChallengeOneController {

    private final ChallengeOneService challengeOneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String download() {
        RequestFilesToScraping filesToScraping = new RequestFilesToScraping(
                List.of("Anexo I", "Anexo II"),
                ".pdf",
                "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos",
                "internal-link",
                "anexos"
        );
        String pathToFile = challengeOneService.download(filesToScraping);
        return "Path to find file: ".concat(pathToFile);
    }
}
