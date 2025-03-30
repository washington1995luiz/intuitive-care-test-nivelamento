package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.filters.HealthPlanOperatorsFilter;
import br.com.washington.intuitive_care_test.data.dto.request.FileToDownload;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsExpensesResponse;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsFullResponse;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsShortResponse;
import br.com.washington.intuitive_care_test.data.entities.HealthPlanOperators;
import br.com.washington.intuitive_care_test.exception.HealthPlanOperatorNotFoundException;
import br.com.washington.intuitive_care_test.exception.PageableException;
import br.com.washington.intuitive_care_test.exception.Top10OperatorsNotFoundException;
import br.com.washington.intuitive_care_test.repositories.HealthPlanOperatorsRepository;
import br.com.washington.intuitive_care_test.repositories.spec.HealthPlanOperatorsSpecification;
import br.com.washington.intuitive_care_test.services.HealthPlanOperatorsService;
import br.com.washington.intuitive_care_test.services.WebScrapingService;
import br.com.washington.intuitive_care_test.services.ZipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@RequiredArgsConstructor
@Service
public class HealthPlanOperatorsServiceImpl implements HealthPlanOperatorsService {

    private final HealthPlanOperatorsRepository repository;
    private final Path path;
    private final WebScrapingService webScrapingService;
    private final ZipService zipService;

    private static final String REPORT = "report";

    @Value("${test-three.sql.tb-health-plan-operators}")
    private String sqlTbHealthPlanOperators;

    @Cacheable(value = "health-plan-operators-all")
    @Override
    public Page<HealthPlanOperatorsShortResponse> findAll(HealthPlanOperatorsFilter filter, Pageable pageable) {
        try{
            Specification<HealthPlanOperators> spec = HealthPlanOperatorsSpecification.withFilter(filter);
            Page<HealthPlanOperators> healthPlanOperators = repository.findAll(spec, pageable);
            return healthPlanOperators.map(HealthPlanOperatorsShortResponse::fromEntity);
        } catch (RuntimeException e) {
            throw new PageableException(e.getMessage());
        }
    }

    @Cacheable(value = "health-plan-operators-id")
    @Override
    public HealthPlanOperatorsFullResponse findById(UUID uuid) {
        Optional<HealthPlanOperatorsFullResponse> response = repository.findById(uuid).map(HealthPlanOperatorsFullResponse::fromEntity);
        response.orElseThrow(() -> new HealthPlanOperatorNotFoundException(uuid.toString()));
        return response.get();
    }

    @Cacheable(value = "health-plan-operators-top10Operators-latest-year")
    @Override
    public List<HealthPlanOperatorsExpensesResponse> findTop10OperatorsWithTheHighestExpensesLatestYear() {
        List<Object[]> top10 = repository.findTop10OperatorsWithTheHighestExpensesLatestYear().orElseThrow(Top10OperatorsNotFoundException::new);
        return top10.stream().map(HealthPlanOperatorsExpensesResponse::fromObjects).toList();
    }

    @Cacheable(value = "health-plan-operators-top10Operators-latest-quarter")
    @Override
    public List<HealthPlanOperatorsExpensesResponse> findTop10OperatorsWithTheHighestExpensesLatestQuarter() {
        List<Object[]> top10 = repository.find10OperatorsWithTheHighestExpensesLatestQuarter().orElseThrow(Top10OperatorsNotFoundException::new);
        return top10.stream().map(HealthPlanOperatorsExpensesResponse::fromObjects).toList();
    }

    @Override
    public void downloadReport() {
        try {
            log.info("Downloading report");
            Path newDirectory = Path.of(path.toAbsolutePath().normalize() + "/relatorio-cadop-" + UUID.randomUUID());
            Path currentDirectory = Files.createDirectories(newDirectory);
            CompletableFuture<Path> downloadFile = webScrapingService.downloadFiles(
                    new FileToDownload(
                            "relatorio-cadop",
                            "https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/Relatorio_cadop.csv"),
                    currentDirectory);
            downloadFile.join();
            File file = Objects.requireNonNull(currentDirectory.toFile().listFiles())[0];

            try (InputStream is = Files.newInputStream(currentDirectory.resolve(file.getAbsolutePath()));
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                log.info("Processing CSV file and importing data to database");
                zipService.processCsv(br, REPORT, sqlTbHealthPlanOperators);
                log.info("Completed");
                log.info("Deleting files and directories");
                file.delete();
                currentDirectory.toFile().delete();
                log.info("Completed");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
