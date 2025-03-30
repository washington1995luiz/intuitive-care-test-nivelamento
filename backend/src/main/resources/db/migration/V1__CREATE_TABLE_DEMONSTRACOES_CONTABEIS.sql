CREATE TABLE tb_demonstracoes_contabeis(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reg_ans VARCHAR(255),
    cd_conta_contabil VARCHAR(255),
    descricao VARCHAR(255),
    vl_saldo_inicial NUMERIC(15, 2),
    vl_saldo_final NUMERIC(15, 2),
    data DATE
);