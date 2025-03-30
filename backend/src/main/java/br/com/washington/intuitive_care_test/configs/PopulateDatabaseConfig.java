package br.com.washington.intuitive_care_test.configs;

import br.com.washington.intuitive_care_test.data.dto.request.RequestFinancialStatementsByYears;
import br.com.washington.intuitive_care_test.repositories.FinancialStatementsRepository;
import br.com.washington.intuitive_care_test.repositories.HealthPlanOperatorsRepository;
import br.com.washington.intuitive_care_test.services.FinancialStatementsService;
import br.com.washington.intuitive_care_test.services.HealthPlanOperatorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class PopulateDatabaseConfig implements CommandLineRunner {

    private final FinancialStatementsRepository financialStatementsRepository;
    private final HealthPlanOperatorsRepository healthPlanOperatorsRepository;
    private final FinancialStatementsService financialStatementsService;
    private final HealthPlanOperatorsService healthPlanOperatorsService;

    @Override
    public void run(String... args) throws Exception {
        downloadIfIsEmptyHealthPlanOperators();
        downloadIfIsEmptyFinancialStatements();
    }

    private void downloadIfIsEmptyHealthPlanOperators(){
        if(healthPlanOperatorsRepository.count() == 0){
            log.info("Populating database health plan operators!");
            log.info("Please, wait!");
            healthPlanOperatorsService.downloadReport();
            return;
        }
        log.info(" - Database health plan operators already populated!");
    }

    private void downloadIfIsEmptyFinancialStatements(){
        if(financialStatementsRepository.count() == 0){
           log.info("Populating database financial statements!\nWith datas of 2024 & 2023.");
           log.info("Please, wait!");
           financialStatementsService.downloadFinancialStatements(
                   new RequestFinancialStatementsByYears(List.of("2024","2023")));
           return;
        }
        log.info(" - Database financial statements already populated!");
    }
}
