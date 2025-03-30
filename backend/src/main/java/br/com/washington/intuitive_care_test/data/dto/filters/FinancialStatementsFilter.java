package br.com.washington.intuitive_care_test.data.dto.filters;

public record FinancialStatementsFilter(
        String ansRegistration,
        String cdAccountingAccount,
        String description,
        String initialBalanceValue,
        String finalBalanceValue,
        String date
) {
}
