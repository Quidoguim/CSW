# spring-microservices-demo

Projeto de exemplo de microsserviços em Spring Boot, para a disciplina de Construção de Software (PUCRS).

O projeto original do professor tem dois serviços de demonstração (`microservico1` e `microservico2`). A partir dele, foram adicionados três serviços novos para implementar um sistema simples de matrícula de estudantes:

- `microservico-estudante`: cadastro de estudantes e consulta por número de matrícula ou por trecho do nome.
- `microservico-disciplina`: cadastro de disciplinas, cada uma podendo ter vários horários (códigos de A a G).
- `microservico-matricula`: matrícula de um estudante em uma disciplina e horário, validando os dados junto aos outros dois serviços.

Cada serviço é uma aplicação Spring Boot independente, com seu próprio Dockerfile, rodando em container separado. Todos usam o mesmo banco Postgres.

## Como rodar

```
docker compose up --build
```

Portas:
- `microservico1`: 443
- `microservico2`: 444
- `microservico-estudante`: 8081
- `microservico-disciplina`: 8082
- `microservico-matricula`: 8083
