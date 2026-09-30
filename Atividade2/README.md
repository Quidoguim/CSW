# Atividade 2 — Microsserviços com Gateway, Service Discovery, Configuração Centralizada e Observabilidade

Enunciado em [enunciado.md](enunciado.md).

Sistema de cadastro e consulta de peças, clientes e representantes comerciais, implementado como microsserviços em Spring Boot 4 + Spring Cloud 2025.1.3, com um API Gateway como único ponto de entrada externo, Eureka para service discovery, Spring Cloud Config Server para configuração centralizada, e Prometheus + Grafana para observabilidade.

## Estrutura

```
Atividade2/
├── enunciado.md
├── docker-compose.yaml
├── config-repo/                    # config centralizada servida pelo Config Server
├── observability/
│   ├── prometheus/                 # config de scrape do Prometheus
│   └── grafana/provisioning/       # datasource + dashboard do Grafana, provisionados automaticamente
├── microservico-discovery/         # Eureka Server
├── microservico-config/            # Spring Cloud Config Server (backend nativo)
├── microservico-gateway/           # Spring Cloud Gateway (unico ponto de entrada externo)
├── microservico-peca/              # cadastro/consulta de pecas
├── microservico-cliente/           # cadastro/consulta de clientes
└── microservico-representante/     # cadastro/consulta de representantes comerciais
```

Os três serviços de negócio não publicam porta no host — só são alcançáveis através do Gateway (ou de dentro da rede Docker), conforme pedido no enunciado.

## Como rodar

```
docker compose up --build
```

Portas publicadas no host:
- `gateway-service`: 8080 (único ponto de entrada para a API — peças/clientes/representantes)
- `service-discovery` (Eureka): 8761
- `config-server`: 8888
- `banco` (Postgres): 5433
- `prometheus`: 9090
- `grafana`: 3000 (usuário/senha padrão: admin/admin — datasource do Prometheus já vem configurado)

## API (via Gateway, `http://localhost:8080`)

| Recurso | Cadastrar | Consultar por chave | Consultar por nome / listar |
|---|---|---|---|
| Peças | `POST /pecas` | `GET /pecas/identificacao/{numeroIdentificacao}` | `GET /pecas?nome=` / `GET /pecas` |
| Clientes | `POST /clientes` | `GET /clientes/cpf/{cpf}` | `GET /clientes?nome=` / `GET /clientes` |
| Representantes | `POST /representantes` | `GET /representantes/cpf/{cpf}` | `GET /representantes?nome=` / `GET /representantes` |

## Parte 2 — Testes e teste de mutação na Atividade1

Feito diretamente nos módulos da [Atividade1](../Atividade1/) (estudante/disciplina/matrícula), não aqui: extraída uma camada de serviço em cada um (antes os controllers falavam direto com o repositório/cliente HTTP), com testes de serviço (Mockito puro), de controller (`@WebMvcTest` isolando o framework web) e de persistência (`@DataJpaTest` com H2 em memória, isolando do Postgres real). Teste de mutação com PIT (`org.pitest:pitest-maven`), 100% de mutantes mortos nos três módulos.

Para rodar, dentro de cada `Atividade1/microservico-<estudante|disciplina|matricula>/`:
```
mvn test                                                  # testes
mvn test-compile org.pitest:pitest-maven:mutationCoverage # relatorio em target/pit-reports/index.html
```
