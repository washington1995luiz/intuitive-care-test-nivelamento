package br.com.washington.intuitive_care_test.data.dto.request;

import java.util.List;

public record RequestFinancialStatementsByYears(
        List<String> years
) {
}
