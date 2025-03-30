package br.com.washington.intuitive_care_test.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
@Entity
@Table(name = "tb_demonstracoes_contabeis")
public class FinancialStatements {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reg_ans")
    private String ansRegistration;

    @Column(name = "cd_conta_contabil")
    private String codeAccountingAccount;

    @Column(name = "descricao")
    private String description;

    @Column(name = "vl_saldo_inicial")
    private BigDecimal initialBalanceValue;

    @Column(name = "vl_saldo_final")
    private BigDecimal finalBalanceValue;

    @Column(name = "data")
    private LocalDate date;
}
