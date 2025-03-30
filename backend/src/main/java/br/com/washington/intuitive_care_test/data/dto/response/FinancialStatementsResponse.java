package br.com.washington.intuitive_care_test.data.dto.response;

import br.com.washington.intuitive_care_test.data.entities.FinancialStatements;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FinancialStatementsResponse(
        UUID id,
        @JsonProperty("registro_ans") String ansRegistration,
        @JsonProperty("codigo_conta_contabil") String cdAccountingAccount,
        @JsonProperty("descricao") String description,
        @JsonProperty("balanco_saldo_inicial") BigDecimal initialBalanceValue,
        @JsonProperty("balanco_saldo_final") BigDecimal finalBalanceValue,
        @JsonProperty("data") LocalDate date
) {
    public static FinancialStatementsResponse fromEntity(FinancialStatements entity){
        return new FinancialStatementsResponse(
                entity.getId(),
                entity.getAnsRegistration(),
                entity.getCodeAccountingAccount(),
                entity.getDescription(),
                entity.getInitialBalanceValue(),
                entity.getFinalBalanceValue(),
                entity.getDate()
        );
    }
}
