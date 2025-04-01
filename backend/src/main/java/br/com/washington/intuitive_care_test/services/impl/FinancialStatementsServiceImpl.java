package br.com.washington.intuitive_care_test.services.impl;

import br.com.washington.intuitive_care_test.data.dto.filters.FinancialStatementsFilter;
import br.com.washington.intuitive_care_test.data.dto.request.RequestFinancialStatementsByYears;
import br.com.washington.intuitive_care_test.data.dto.response.FinancialStatementsResponse;
import br.com.washington.intuitive_care_test.data.entities.FinancialStatements;
import br.com.washington.intuitive_care_test.exception.FinancialStatementsNotFoundException;
import br.com.washington.intuitive_care_test.exception.PageableException;
import br.com.washington.intuitive_care_test.repositories.FinancialStatementsRepository;
import br.com.washington.intuitive_care_test.repositories.spec.FinancialStatementsSpecification;
import br.com.washington.intuitive_care_test.services.FinancialStatementsService;
import br.com.washington.intuitive_care_test.services.WebScrapingService;
import br.com.washington.intuitive_care_test.services.ZipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


@Log4j2
@RequiredArgsConstructor
@Service
public class FinancialStatementsServiceImpl implements FinancialStatementsService {

    private final FinancialStatementsRepository repository;
    private final WebScrapingService webScrapingService;
    private final ZipService zipService;


    @Override
    public void downloadFinancialStatements(RequestFinancialStatementsByYears statementsByYears) {
        statementsByYears.years().stream()
                .map(webScrapingService::financialStatementsWebScraping)
                .toList()
                .forEach(result -> {
                    File file = result.join().toFile();
                    File[] files = file.listFiles();
                    assert files != null;
                    zipService.processZipFiles(files);
                    Stream.of(files).forEach(File::delete);
                    file.getAbsoluteFile().delete();
                });
        log.info("Completed");
    }
    
    
    @Cacheable(value = "financial-statements-all")
    @Override
    public Page<FinancialStatementsResponse> findAll(FinancialStatementsFilter filter, Pageable pageable) {
        try{
            Specification<FinancialStatements> spec = FinancialStatementsSpecification.withFilter(filter);
            Page<FinancialStatements> findAll = repository.findAll(spec, pageable);
            return findAll.map(FinancialStatementsResponse::fromEntity);
        } catch (RuntimeException e) {
            throw new PageableException(e.getMessage());
        }
    }

    @Cacheable(value = "financial-statements-id")
    @Override
    public FinancialStatementsResponse findById(UUID uuid) {
        Optional<FinancialStatementsResponse> response = repository.findById(uuid).map(FinancialStatementsResponse::fromEntity);
        response.orElseThrow(() -> new FinancialStatementsNotFoundException(uuid.toString()));
        return response.get();
    }

}
