package br.com.washington.intuitive_care_test.repositories.spec;

import br.com.washington.intuitive_care_test.data.dto.filters.FinancialStatementsFilter;
import br.com.washington.intuitive_care_test.data.entities.FinancialStatements;
import br.com.washington.intuitive_care_test.util.ConvertDate;
import br.com.washington.intuitive_care_test.util.FormatMonetaryNumber;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialStatementsSpecification {

    public static Specification<FinancialStatements> withFilter(FinancialStatementsFilter filter) {
        return (Root<FinancialStatements> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (!ObjectUtils.isEmpty(filter.date())) {
                LocalDate date = ConvertDate.convert(filter.date());
                predicate = cb.and(predicate, cb.equal(root.get("date"), date));
            }

            if (!ObjectUtils.isEmpty(filter.ansRegistration())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("ansRegistration")), "%" + filter.ansRegistration().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.cdAccountingAccount())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("cdAccountingAccount")), "%" + filter.cdAccountingAccount().toLowerCase() + "%"));
            }

            if (!ObjectUtils.isEmpty(filter.description())) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%"));
            }
            if (!ObjectUtils.isEmpty(filter.initialBalanceValue())) {
                BigDecimal initialBalance = FormatMonetaryNumber.format(filter.initialBalanceValue());
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("initialBalanceValue")), "%" + initialBalance + "%"));
            }
            if (!ObjectUtils.isEmpty(filter.finalBalanceValue())) {
                BigDecimal finalBalance = FormatMonetaryNumber.format(filter.finalBalanceValue());
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("finalBalanceValue")), "%" + finalBalance + "%"));
            }
            return predicate;
        };
    }
}
