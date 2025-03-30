package br.com.washington.intuitive_care_test.data.dto.response;

import br.com.washington.intuitive_care_test.data.entities.HealthPlanOperators;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record HealthPlanOperatorsShortResponse(
        UUID id,
        @JsonProperty("registro_ans") String ansRegistration,
        @JsonProperty("cnpj") String companyRegistrationNumber,
        @JsonProperty("razao_social") String companyName,
        @JsonProperty("nome_fantasia") String fantasyName,
        @JsonProperty("modalidade") String modality,
        @JsonProperty("cidade") String city,
        @JsonProperty("uf") String uf,
        @JsonProperty("ddd") String dddCode,
        @JsonProperty("telefone") String phone,
        @JsonProperty("endereco_eletronico") String electronicAddress,
        @JsonProperty("data_registro_ans") LocalDate ansRegistrationDate
) {
        
        public static HealthPlanOperatorsShortResponse fromEntity(HealthPlanOperators entity){
                return new HealthPlanOperatorsShortResponse(
                        entity.getId(),
                        entity.getAnsRegistration(), //ansRegistration
                        entity.getCnpj(),
                        entity.getCompanyName(),
                        entity.getFantasyName(),
                        entity.getModality(),
                        entity.getCity(),
                        entity.getState(),
                        entity.getDddCode(),
                        entity.getPhone(),
                        entity.getElectronicAddress(),
                        entity.getAnsRegistrationDate()
                );
        }
}
