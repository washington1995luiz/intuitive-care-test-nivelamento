package br.com.washington.intuitive_care_test.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HealthPlanOperatorsExpensesResponse(
      @JsonProperty("registro_ans")   String ansRegistration,
      @JsonProperty("nome_fantasia")   String fantasyName,
      @JsonProperty("razao-social")   String companyName,
      @JsonProperty("descricao")   String description,
      @JsonProperty("despesas_totais")   BigDecimal totalExpenses,
      @JsonProperty("data")   LocalDate date
) {

    public static HealthPlanOperatorsExpensesResponse fromObjects(Object[] objects) {
        return new HealthPlanOperatorsExpensesResponse(
                (String) objects[0],
                (String) objects[1],
                (String) objects[2],
                (String) objects[3],
                (BigDecimal) objects[4],
                LocalDate.parse(objects[5].toString())
        );
    }
}
