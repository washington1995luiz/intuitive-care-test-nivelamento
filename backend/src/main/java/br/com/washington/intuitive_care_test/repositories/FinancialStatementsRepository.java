package br.com.washington.intuitive_care_test.repositories;

import br.com.washington.intuitive_care_test.data.entities.FinancialStatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FinancialStatementsRepository extends JpaRepository<FinancialStatements, UUID>, JpaSpecificationExecutor<FinancialStatements> {
}
