package br.com.washington.intuitive_care_test.controllers;

import br.com.washington.intuitive_care_test.data.dto.filters.HealthPlanOperatorsFilter;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsExpensesResponse;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsFullResponse;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsShortResponse;
import br.com.washington.intuitive_care_test.exception.BadFormattedUUIDException;
import br.com.washington.intuitive_care_test.services.HealthPlanOperatorsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/health-plan-operators")
public class HealthPlanOperatorsController {

    private final HealthPlanOperatorsService healthPlanOperatorsService;

    @Tag(name = "TESTE DE API")
    @GetMapping
    public Page<HealthPlanOperatorsShortResponse> findAll(HealthPlanOperatorsFilter filter, Pageable pageable) {
        return healthPlanOperatorsService.findAll(filter, pageable);
    }

    @Tag(name = "TESTE DE API")
    @GetMapping(value = "/{id}")
    public HealthPlanOperatorsFullResponse findById(@PathVariable(value = "id") String id){
        try{
            UUID uuid = UUID.fromString(id);
            return healthPlanOperatorsService.findById(uuid);
        } catch (RuntimeException e) {
            throw new BadFormattedUUIDException(id);
        }
    }

    @Tag(name = "TESTE DE BANCO DE DADOS")
    @GetMapping(value = "/top-10-expenses-latest-year")
    public List<HealthPlanOperatorsExpensesResponse> findTop10OperatorsWithTheHighestExpensesLatestYear(){
        return healthPlanOperatorsService.findTop10OperatorsWithTheHighestExpensesLatestYear();
    }

    @Tag(name = "TESTE DE BANCO DE DADOS")
    @GetMapping(value = "/top-10-expenses-latest-quarter")
    public List<HealthPlanOperatorsExpensesResponse> findTop10OperatorsWithTheHighestExpensesLatestQuarter(){
        return healthPlanOperatorsService.findTop10OperatorsWithTheHighestExpensesLatestQuarter();
    }

    @Tag(name = "TESTE DE BANCO DE DADOS")
    @PostMapping(value = "/reports")
    @ResponseStatus(HttpStatus.CREATED)
    public void downloadReport(){
        healthPlanOperatorsService.downloadReport();
    }
}
