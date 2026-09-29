# Enunciado — Atividade 2: Microsserviços com Observabilidade e Testes

Retomamos o exercício de experimentação para configurar/ajustar o projeto inserindo aspectos de observabilidade: reportar métricas através do Prometheus, visualizadas pelo Grafana, com um container para o Prometheus e um para o Grafana dando suporte à aplicação. Ver o material de apoio (`aula13-testes-unitarios-integracao-junit5-mockito.pdf`, disponível localmente nesta pasta — não versionado no repositório por passar do limite de 100 MiB por arquivo do GitHub) como orientação.

## Parte 1 — Sistema de peças, clientes e representantes

O sistema deve implementar as seguintes funcionalidades:

1. Cadastrar peças
   - Número de identificação
   - Nome
   - Descrição
2. Consultar peças pelo nome e pelo id
3. Listar todas as peças
4. Cadastrar clientes
   - CPF
   - Nome
5. Consultar clientes por nome e por CPF
6. Listar todos os clientes
7. Cadastrar representantes comerciais
   - CPF
   - Nome
8. Consultar representantes por nome e por CPF
9. Listar todos os representantes

Ao implementar:

- Organizar o projeto em pelo menos 3 microsserviços (peças, clientes e representantes)
- Implementar os padrões Gateway, Service Discovery e Configuração Centralizada usando o Spring Cloud
- Testar as APIs com um front-end ou com o Postman (ou similar), acessando apenas o Gateway
- Configurar containers para o Prometheus e para o Grafana dando suporte à aplicação, reportando e visualizando métricas do sistema

## Parte 2 — Testes no exercício anterior (Atividade1)

Inserir código de testes unitários e de integração no exercício anterior ([Atividade1](../Atividade1/) — estudantes, disciplinas e matrícula):

- Dos serviços
- Dos controllers, isolando o framework web
- Do código de persistência, isolando o framework de persistência e o banco de dados

Implementar testes de mutação.
