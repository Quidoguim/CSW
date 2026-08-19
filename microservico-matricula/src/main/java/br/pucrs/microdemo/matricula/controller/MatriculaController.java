package br.pucrs.microdemo.matricula.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.matricula.client.DisciplinaClient;
import br.pucrs.microdemo.matricula.client.DisciplinaClient.DisciplinaDTO;
import br.pucrs.microdemo.matricula.client.EstudanteClient;
import br.pucrs.microdemo.matricula.domain.Matricula;
import br.pucrs.microdemo.matricula.repository.MatriculaRepository;

@RestController
public class MatriculaController {

    private final MatriculaRepository matriculaRepository;
    private final EstudanteClient estudanteClient;
    private final DisciplinaClient disciplinaClient;

    public MatriculaController(MatriculaRepository matriculaRepository, EstudanteClient estudanteClient,
            DisciplinaClient disciplinaClient) {
        this.matriculaRepository = matriculaRepository;
        this.estudanteClient = estudanteClient;
        this.disciplinaClient = disciplinaClient;
    }

    record MatriculaRequest(String numeroMatricula, String codigoDisciplina, String codigoHorario) {
    }

    @PostMapping("/matriculas")
    public Matricula matricular(@RequestBody MatriculaRequest request) {
        estudanteClient.buscarPorMatricula(request.numeroMatricula())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "estudante nao encontrado"));

        DisciplinaDTO disciplina = disciplinaClient.buscarPorCodigo(request.codigoDisciplina())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "disciplina nao encontrada"));

        boolean horarioValido = disciplina.horarios().stream()
                .anyMatch(h -> h.codigo().equalsIgnoreCase(request.codigoHorario()));
        if (!horarioValido) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "horario nao disponivel");
        }

        return matriculaRepository.save(new Matricula(request.numeroMatricula(), request.codigoDisciplina(),
                request.codigoHorario().toUpperCase()));
    }

    @GetMapping("/matriculas/estudante/{numeroMatricula}")
    public List<Matricula> listarPorEstudante(@PathVariable String numeroMatricula) {
        return matriculaRepository.findByNumeroMatricula(numeroMatricula);
    }
}
