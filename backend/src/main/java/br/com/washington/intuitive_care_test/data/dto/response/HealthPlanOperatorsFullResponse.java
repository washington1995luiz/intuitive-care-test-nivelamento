package br.com.washington.intuitive_care_test.data.dto.response;

import br.com.washington.intuitive_care_test.data.entities.HealthPlanOperators;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record HealthPlanOperatorsFullResponse(
        UUID id,
        @JsonProperty("registro_ans")
        String ansRegistration,
        @JsonProperty("cnpj")
        String companyRegistrationNumber,
        @JsonProperty("razao_social")
        String companyName,
        @JsonProperty("nome_fantasia")
        String fantasyName,
        @JsonProperty("modalidade")
        String modality,

        @JsonProperty("logradouro")
        String street,

        @JsonProperty("numero")
        String number,

        @JsonProperty("complemento")
        String complement,

        @JsonProperty("bairro")
        String neighborhood,
        @JsonProperty("cidade")
        String city,
        @JsonProperty("uf")
        String uf,

        @JsonProperty("cep")
        String postalCode,
        @JsonProperty("ddd")
        String dddCode,
        @JsonProperty("telefone")
        String phone,

        @JsonProperty("fax")
        String faxNumber,
        @JsonProperty("endereco_eletronico")
        String electronicAddress,

        @JsonProperty("representante")
        String representative,

        @JsonProperty("cargo_representante")
        String representativeRole,

        @JsonProperty("regiao_de_comercializacao")
        String marketingRegion,
        @JsonProperty("data_registro_ans")
        LocalDate ansRegistrationDate
) {

    public static HealthPlanOperatorsFullResponse fromEntity(HealthPlanOperators entity) {
        return new HealthPlanOperatorsFullResponse(
                entity.getId(),
                entity.getAnsRegistration(), //ansRegistration
                entity.getCnpj(),
                entity.getCompanyName(),
                entity.getFantasyName(),
                entity.getModality(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getComplement(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getState(),
                entity.getCep(),
                entity.getDddCode(),
                entity.getPhone(),
                entity.getFax(),
                entity.getElectronicAddress(),
                entity.getRepresentative(),
                entity.getRepresentativeRole(),
                entity.getMarketingRegion(),
                entity.getAnsRegistrationDate()
        );
    }
}