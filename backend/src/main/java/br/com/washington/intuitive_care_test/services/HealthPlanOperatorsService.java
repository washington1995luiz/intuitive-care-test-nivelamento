package br.com.washington.intuitive_care_test.services;

import br.com.washington.intuitive_care_test.data.dto.filters.HealthPlanOperatorsFilter;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsExpensesResponse;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsFullResponse;
import br.com.washington.intuitive_care_test.data.dto.response.HealthPlanOperatorsShortResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface HealthPlanOperatorsService {

    Page<HealthPlanOperatorsShortResponse> findAll(HealthPlanOperatorsFilter filter, Pageable pageable);

    HealthPlanOperatorsFullResponse findById(UUID uuid);

    void downloadReport();

    List<HealthPlanOperatorsExpensesResponse> findTop10OperatorsWithTheHighestExpensesLatestYear();

    List<HealthPlanOperatorsExpensesResponse> findTop10OperatorsWithTheHighestExpensesLatestQuarter();
}
