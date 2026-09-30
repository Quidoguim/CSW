package br.pucrs.microdemo.matricula.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.matricula.client.DisciplinaClient;
import br.pucrs.microdemo.matricula.client.DisciplinaClient.DisciplinaDTO;
import br.pucrs.microdemo.matricula.client.EstudanteClient;
import br.pucrs.microdemo.matricula.domain.Matricula;
import br.pucrs.microdemo.matricula.repository.MatriculaRepository;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final EstudanteClient estudanteClient;
    private final DisciplinaClient disciplinaClient;

    public MatriculaService(MatriculaRepository matriculaRepository, EstudanteClient estudanteClient,
            DisciplinaClient disciplinaClient) {
        this.matriculaRepository = matriculaRepository;
        this.estudanteClient = estudanteClient;
        this.disciplinaClient = disciplinaClient;
    }

    public Matricula matricular(String numeroMatricula, String codigoDisciplina, String codigoHorario) {
        estudanteClient.buscarPorMatricula(numeroMatricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "estudante nao encontrado"));

        DisciplinaDTO disciplina = disciplinaClient.buscarPorCodigo(codigoDisciplina)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "disciplina nao encontrada"));

        boolean horarioValido = disciplina.horarios().stream()
                .anyMatch(h -> h.codigo().equalsIgnoreCase(codigoHorario));
        if (!horarioValido) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "horario nao disponivel");
        }

        return matriculaRepository.save(new Matricula(numeroMatricula, codigoDisciplina,
                codigoHorario.toUpperCase()));
    }

    public List<Matricula> listarPorEstudante(String numeroMatricula) {
        return matriculaRepository.findByNumeroMatricula(numeroMatricula);
    }
}
