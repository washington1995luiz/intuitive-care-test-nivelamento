package br.com.washington.intuitive_care_test.controllers;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFilesToScraping;
import br.com.washington.intuitive_care_test.services.ChallengeTwoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TESTE DE TRANSFORMAÇÃO DE DADOS")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/challenge-two")
public class ChallengeTwoController {

    private final ChallengeTwoService challengeTwoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String transform(){
        RequestFilesToScraping filesToScraping = new RequestFilesToScraping(
                List.of("Anexo I"),
                ".pdf",
                "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos",
                "internal-link",
                "Teste_washington"
        );
        String pathToZip = challengeTwoService.transform(filesToScraping);
        return "Path to find file: ".concat(pathToZip);
    }
}
