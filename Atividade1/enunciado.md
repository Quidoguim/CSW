# Enunciado — Atividade de Microsserviços

Implementar as seguintes funcionalidades:

1. Cadastrar estudante
   - Nome
   - Número de matrícula
2. Consultar um estudante pelo número de matrícula
3. Consultar um estudante por um trecho do seu nome
   - Se houver mais de um resultado, retornar uma lista
4. Cadastrar disciplinas
   - Código da disciplina
   - Nome da disciplina
   - Horário da disciplina (por códigos: A, B, C, D... G)
   - Uma mesma disciplina (mesmo código e nome) pode ocorrer em múltiplos horários
5. Matricular estudante em uma disciplina
   - Escolher a disciplina
   - Escolher o horário
   - Efetuar a matrícula

Ao implementar as funcionalidades:
- Escolher os serviços a serem implementados (como dividir, quais as rotas e parâmetros dos requests/responses)
- Adotar um estilo arquitetural baseado em microsserviços para definir essa organização
- Por ora, o banco de dados pode ser centralizado
