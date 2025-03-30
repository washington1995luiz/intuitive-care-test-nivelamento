# INTUITIVE CARE - TESTE DE NIVELAMENTO

Este projeto contém tanto o backend quanto o frontend de uma aplicação. Abaixo estão as instruções para configurar, executar e testar o projeto utilizando Docker Compose e Postman.

## Estrutura do Projeto

- **Backend**: API desenvolvida para gerenciar os dados e fornecer endpoints para o frontend.
- **Frontend**: Interface do usuário para interação com os dados fornecidos pelo backend.

## Pré-requisitos

Certifique-se de ter os seguintes itens instalados:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Como subir o projeto

1. Clone o repositório:
    ```bash
    git clone https://github.com/washington1995luiz/intuitive-care-test.git
    cd intuitive-care-test
    ```

2. Suba os containers com Docker Compose:
    ```bash
    docker-compose up -d --build
    ```

3. Acesse o frontend no navegador:
    ```
    http://localhost:9000
    ```

4. O backend estará disponível em:
    ```
    http://localhost:8585
    ```

## Aviso
Ao iniciar o projeto, já vai baixar automaticamente os arquivos .zip do desafio 3, e serão inseridos no banco de dados.
Não será necessário fazer nova chamada para incluir no banco de dados os dados dos últimos dois anos pedido no teste de nivelamento.
Mas caso queira inserir mais dados, deixarei o endpoint e forma de inserir os dados abaixo na parte de exemplos de endpoint.


### Exemplos de Endpoints

- **GET /[swagger-ui/index.html](http://localhost:8585/swagger-ui/index.html) Retorna a documentação do Swagger
- **GET /[health-plan-operators](http://localhost:8585/api/v1/health-plan-operators) Retorna uma lista paginada de planos de saúde
- **GET /[health-plan-operators-id](http://localhost:8585/api/v1/health-plan-operators/{id}) Retorna um plano de saúde pelo ID
- **POST /[health-plan-operators-download](http://localhost:8585/api/v1/health-plan-operators/reports) Insere os dados no banco de dados os planos de saúde atualizados.
- **POST /[financial-statements-download](http;//localhost:8585/api/v1/financial-statements) Insere os dados das despesas de planos de saúde, os anos especificados via body, na base de dados.
```
{
    "years": ["2024", "2023"]
}
```
## Testando com Postman

1. Abra o Postman e crie uma nova coleção.
2. Adicione as requisições para os endpoints acima utilizando a Base URL.
3. Para endpoints que requerem um corpo (POST), insira o JSON no corpo da requisição.

## Observações

- Certifique-se de que as portas `9000` (frontend) e `8585` (backend) estejam livres antes de subir os containers.
- Para parar os containers, utilize:
  ```bash
  docker-compose down
  ```

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).