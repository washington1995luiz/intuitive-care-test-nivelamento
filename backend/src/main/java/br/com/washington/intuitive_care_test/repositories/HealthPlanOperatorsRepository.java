package br.com.washington.intuitive_care_test.repositories;

import br.com.washington.intuitive_care_test.data.entities.HealthPlanOperators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HealthPlanOperatorsRepository extends JpaRepository<HealthPlanOperators, UUID>, JpaSpecificationExecutor<HealthPlanOperators> {

    @Query(value = """
            
              SELECT
                op.registro_ans,
                op.nome_fantasia,
                op.razao_social,
                demo.descricao,
                SUM(demo.vl_saldo_final - demo.vl_saldo_inicial) AS total_despesas,
                demo.data
            FROM tb_demonstracoes_contabeis demo
                     INNER JOIN tb_operadoras_plano_saude_ativas op ON op.registro_ans = demo.reg_ans
              AND demo.data >= CURRENT_DATE - INTERVAL '1 year'
              AND demo.data < CURRENT_DATE
            GROUP BY op.registro_ans, op.nome_fantasia, op.razao_social, demo.descricao, demo.data
            ORDER BY total_despesas DESC
            LIMIT 10;
            """, nativeQuery = true)
    Optional<List<Object[]>> findTop10OperatorsWithTheHighestExpensesLatestYear();

    @Query(value = """
               SELECT
                   op.registro_ans,
                   op.nome_fantasia,
                   op.razao_social,
                   demo.descricao,
                   SUM(demo.vl_saldo_final - demo.vl_saldo_inicial) AS total_despesas,
                   demo.data
               FROM tb_demonstracoes_contabeis demo
                        INNER JOIN tb_operadoras_plano_saude_ativas op ON op.registro_ans = demo.reg_ans
               WHERE demo.descricao = 'EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR'
                 AND demo.data >= date_trunc('quarter', CURRENT_DATE - INTERVAL '3 month')
                 AND demo.data < date_trunc('quarter', CURRENT_DATE + INTERVAL '0 month')
               GROUP BY op.registro_ans, op.nome_fantasia, op.razao_social, demo.descricao, demo.data
               ORDER BY total_despesas DESC
               LIMIT 10;
            """, nativeQuery = true)
    Optional<List<Object[]>> find10OperatorsWithTheHighestExpensesLatestQuarter();

}
