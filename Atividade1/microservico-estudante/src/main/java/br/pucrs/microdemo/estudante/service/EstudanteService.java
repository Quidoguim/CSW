package br.pucrs.microdemo.estudante.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.estudante.domain.Estudante;
import br.pucrs.microdemo.estudante.repository.EstudanteRepository;

@Service
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;

    public EstudanteService(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    public Estudante cadastrar(Estudante estudante) {
        estudante.setId(null);
        return estudanteRepository.save(estudante);
    }

    public Estudante buscarPorMatricula(String numeroMatricula) {
        return estudanteRepository.findByNumeroMatricula(numeroMatricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Estudante> buscarPorNome(String trechoNome) {
        return estudanteRepository.findByNomeContainingIgnoreCase(trechoNome);
    }
}
