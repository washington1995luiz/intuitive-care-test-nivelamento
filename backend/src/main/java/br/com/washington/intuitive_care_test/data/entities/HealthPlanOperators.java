package br.com.washington.intuitive_care_test.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_operadoras_plano_saude_ativas")
public class HealthPlanOperators {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "registro_ans") //ans registration
    private String ansRegistration;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "razao_social") //company name
    private String companyName;

    @Column(name = "nome_fantasia") //trade name
    private String fantasyName;

    @Column(name = "modalidade") //modality
    private String modality;

    @Column(name = "logradouro") //street
    private String street;

    @Column(name = "numero") //number
    private String number;

    @Column(name = "complemento") //complement
    private String complement;

    @Column(name = "bairro") //neighborhood
    private String neighborhood;

    @Column(name = "cidade") //city
    private String city;

    @Column(name = "uf") //state
    private String state;

    @Column(name = "cep")
    private String cep;

    @Column(name = "ddd") //area code
    private String dddCode;

    @Column(name = "telefone") //phone
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "endereco_eletronico") //electronic address
    private String electronicAddress;

    @Column(name = "representante") //representative
    private String representative;

    @Column(name = "cargo_representante") //representative role
    private String representativeRole;

    @Column(name = "regiao_de_comercializacao") //marketing region
    private String marketingRegion;

    @Column(name = "data_registro_ans") //ans registration date
    private LocalDate ansRegistrationDate;
}
