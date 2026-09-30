# CSW — Construção de Software

Repositório dedicado à disciplina de Construção de Software (PUCRS - Escola Politécnica). Contém os trabalhos e atividades desenvolvidos ao longo do curso.

## Estrutura

Cada trabalho ou atividade fica em uma pasta própria, com seu enunciado e sua implementação.

```
Atividade1/
├── enunciado.md               # enunciado da atividade
├── docker-compose.yaml
├── commons/                   # projeto-base fornecido pelo professor
├── microservico1/              # projeto-base fornecido pelo professor
├── microservico2/              # projeto-base fornecido pelo professor
├── microservico-estudante/     # implementado na atividade
├── microservico-disciplina/    # implementado na atividade
└── microservico-matricula/     # implementado na atividade
```

## Atividades e trabalhos

| Tipo | Nome | Descrição | Status |
|---|---|---|---|
| Atividade | [Microsserviços](Atividade1/) | Sistema de matrícula (estudantes, disciplinas e matrícula) implementado como microsserviços em Spring Boot | Entregue |
| Atividade | [Microsserviços com Gateway/Discovery/Config e observabilidade](Atividade2/) | Sistema de peças, clientes e representantes comerciais, com Spring Cloud Gateway, Eureka, Config Server e observabilidade via Prometheus + Grafana; testes e teste de mutação inseridos na Atividade1 | Entregue |

## Licença

Este repositório está sob a licença MIT — veja [LICENSE](LICENSE).
