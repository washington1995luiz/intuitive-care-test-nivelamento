package br.com.washington.intuitive_care_test.repositories.spec;

import br.com.washington.intuitive_care_test.data.dto.filters.HealthPlanOperatorsFilter;
import br.com.washington.intuitive_care_test.data.entities.HealthPlanOperators;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;
import org.springframework.util.ObjectUtils;

public class HealthPlanOperatorsSpecification {

    public static Specification<HealthPlanOperators> withFilter(HealthPlanOperatorsFilter filter) {
        return (Root<HealthPlanOperators> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if(!ObjectUtils.isEmpty(filter.ansRegistration())){
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("ansRegistration")), "%" + filter.ansRegistration().toLowerCase() + "%"));
            }
            if (!ObjectUtils.isEmpty(filter.city())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("city")), "%" + filter.city().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.modality())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("modality")), "%" + filter.modality().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.state())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("state")), "%" + filter.state().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.telephone())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("phone")), "%" + filter.telephone().toLowerCase() + "%"));
            }
            if (!ObjectUtils.isEmpty(filter.ddd())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("dddCode")), "%" + filter.ddd().toLowerCase() + "%"));
            }
            return predicate;
        };
    }
}
