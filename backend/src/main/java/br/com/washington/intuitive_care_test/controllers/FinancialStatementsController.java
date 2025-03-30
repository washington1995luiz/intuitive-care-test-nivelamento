package br.com.washington.intuitive_care_test.controllers;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFinancialStatementsByYears;
import br.com.washington.intuitive_care_test.data.dto.filters.FinancialStatementsFilter;
import br.com.washington.intuitive_care_test.data.dto.response.FinancialStatementsResponse;
import br.com.washington.intuitive_care_test.exception.BadFormattedUUIDException;
import br.com.washington.intuitive_care_test.exception.RequestCannotBeEmptyException;
import br.com.washington.intuitive_care_test.services.FinancialStatementsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "TESTE DE BANCO DE DADOS")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/financial-statements")
public class FinancialStatementsController {

    private final FinancialStatementsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void download(@RequestBody RequestFinancialStatementsByYears years) {
        if (years.years().isEmpty()) {
            throw new RequestCannotBeEmptyException("Years cannot be empty");
        }
        service.downloadFinancialStatements(years);
    }


    @GetMapping
    public Page<FinancialStatementsResponse> findAll(FinancialStatementsFilter filter, Pageable pageable){
        return service.findAll(filter, pageable);
    }

    @GetMapping(value = "/{id}")
    public FinancialStatementsResponse findById(@PathVariable(value = "id") String id) {
        try{
            UUID uuid = UUID.fromString(id);
            return service.findById(uuid);
        } catch (IllegalArgumentException e){
            throw new BadFormattedUUIDException(id);
        }
    }
}
