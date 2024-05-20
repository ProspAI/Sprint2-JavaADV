# ProspAI

## Integrantes do Grupo
- AGATHA PIRES – RM552247 – (2TDSPH) - MOBILE APPLICATION DEVELOPMENT / MASTERING RELATIONAL AND NON-RELATIONAL DATABASE
- DAVID BRYAN VIANA – RM551236 – (2TDSPM) - ADVANCED BUSINESS DEVELOPMENT WITH .NET / MOBILE APPLICATION DEVELOPMENT
- GABRIEL LIMA – RM99743 – (2TDSPM) - COMPLIANCE, QUALITY ASSURANCE & TESTS / DISRUPTIVE ARCHITECTURES: IOT, IOB & GENERATIVE IA
- GIOVANNA ALVAREZ – RM98892 – (2TDSPM) - COMPLIANCE, QUALITY ASSURANCE & TESTS / DEVOPS TOOLS & CLOUD COMPUTING
- MURILO MATOS – RM552525 – (2TDSPM) - JAVA ADVANCED / DEVOPS TOOLS & CLOUD COMPUTING

## Instruções para Executar a Aplicação
Para executar o projeto ProspAI, siga estas etapas:
1. Certifique-se de ter o Java Development Kit (JDK) instalado em sua máquina.
2. Clone o repositório do projeto para sua máquina local.
3. Abra o projeto em sua IDE preferida.
4. Execute o aplicativo como um aplicativo Spring Boot.

## Imagem dos Diagramas e videos 
Você pode encontrar a documentação e os testes na pasta `DOCUMENTAÇÃO` dentro do arquivo da documentação.


## Descrição

ProspAI é uma aplicação desenvolvida em Java utilizando Spring Boot que visa gerenciar Clientes, Compras, Feedbacks, Predições, Relatórios e Estratégias de Vendas. Esta aplicação oferece uma API RESTful que permite operações CRUD (Create, Read, Update, Delete) em cada uma das entidades gerenciadas.

## Arquitetura

A aplicação segue os princípios de uma arquitetura RESTful e está estruturada para garantir coesão e desacoplamento, respeitando os padrões de DTO (Data Transfer Object) e Bean Validation. 


### Principais Componentes

- **Controladores (Controllers)**: Responsáveis por receber as requisições HTTP e delegar a lógica de negócios para os serviços correspondentes.
- **Serviços (Services)**: Contêm a lógica de negócios da aplicação.
- **Repositorios (Repositories)**: Interagem com o banco de dados para realizar operações CRUD.
- **DTOs (Data Transfer Objects)**: Utilizados para transferência de dados entre a camada de apresentação e a lógica de negócios.
- **Entidades (Entities)**: Representam as tabelas no banco de dados.



## Requisitos

### Funcionais

- CRUD para Clientes, Feedbacks, Predições, Relatórios e Estratégias de Vendas.
- API RESTful seguindo o modelo de maturidade de Leonard Richardson Nível 2.

### Não-Funcionais

- Utilização de Spring Boot para o backend.
- Banco de dados relacional (Oracle).
- Documentação da API utilizando HATEOAS.
- Padrões de Projeto: Singleton, Repository com Genéricos.

## Melhorias Implementadas

- **Modularização e Desacoplamento**: Separação clara entre controladores, serviços e repositórios.
- **Uso de HATEOAS**: Adição de links HATEOAS nas respostas da API.
- **Validação com Bean Validation**: Uso de anotações de validação nas entidades e DTOs.
- **DTOs para Transferência de Dados**: Implementação de DTOs para isolar a lógica de negócios da camada de apresentação.
- **Padrões RESTful**: Melhor conformidade com os padrões RESTful, incluindo status HTTP apropriados e links de navegação.

## Configuração

### Pré-requisitos

- Java 17
- Maven
- Banco de dados Oracle


Endpoints:

Clientes
GET /clientes: Obtém todos os clientes.
GET /clientes/{id}: Obtém um cliente pelo ID.
POST /clientes: Cria um novo cliente.
PUT /clientes/{id}: Atualiza um cliente existente.
DELETE /clientes/{id}: Deleta um cliente.

Feedbacks
GET /feedbacks: Obtém todos os feedbacks.
GET /feedbacks/{id}: Obtém um feedback pelo ID.
POST /feedbacks: Cria um novo feedback.
PUT /feedbacks/{id}: Atualiza um feedback existente.
DELETE /feedbacks/{id}: Deleta um feedback.

Predições
GET /predictions: Obtém todas as predições.
GET /predictions/{id}: Obtém uma predição pelo ID.
POST /predictions: Cria uma nova predição.
PUT /predictions/{id}: Atualiza uma predição existente.
DELETE /predictions/{id}: Deleta uma predição.

Relatórios
GET /reports: Obtém todos os relatórios.
GET /reports/{id}: Obtém um relatório pelo ID.
POST /reports: Cria um novo relatório.
PUT /reports/{id}: Atualiza um relatório existente.
DELETE /reports/{id}: Deleta um relatório.

Estratégias de Vendas
GET /sales-strategies: Obtém todas as estratégias de vendas.
GET /sales-strategies/{id}: Obtém uma estratégia de vendas pelo ID.
POST /sales-strategies: Cria uma nova estratégia de vendas.
PUT /sales-strategies/{id}: Atualiza uma estratégia de vendas existente.
DELETE /sales-strategies/{id}: Deleta uma estratégia de vendas.

Usuários
GET /usuarios: Obtém todos os usuários.
GET /usuarios/{id}: Obtém um usuário pelo ID.
POST /usuarios: Cria um novo usuário.
PUT /usuarios/{id}: Atualiza um usuário existente.
DELETE /usuarios/{id}: Deleta um usuário.
