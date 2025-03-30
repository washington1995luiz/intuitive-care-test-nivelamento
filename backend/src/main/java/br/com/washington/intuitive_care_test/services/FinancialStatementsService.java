package br.com.washington.intuitive_care_test.services;

import br.com.washington.intuitive_care_test.data.dto.filters.FinancialStatementsFilter;
import br.com.washington.intuitive_care_test.data.dto.request.RequestFinancialStatementsByYears;
import br.com.washington.intuitive_care_test.data.dto.response.FinancialStatementsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FinancialStatementsService {
    void downloadFinancialStatements(RequestFinancialStatementsByYears years);

    Page<FinancialStatementsResponse> findAll(FinancialStatementsFilter filter, Pageable pageable);

    FinancialStatementsResponse findById(UUID uuid);
}
