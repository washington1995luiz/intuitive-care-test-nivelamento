package br.com.washington.intuitive_care_test.data.dto.filters;

public record HealthPlanOperatorsFilter(
        String city,
        String state,
        String telephone,
        String modality,
        String ddd,
        String ansRegistration
) {
}
