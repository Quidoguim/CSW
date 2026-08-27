package br.pucrs.microdemo.estudante.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.estudante.domain.Estudante;
import br.pucrs.microdemo.estudante.repository.EstudanteRepository;

@RestController
public class EstudanteController {

    private final EstudanteRepository estudanteRepository;

    public EstudanteController(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    @PostMapping("/estudantes")
    public Estudante cadastrar(@RequestBody Estudante estudante) {
        estudante.setId(null);
        return estudanteRepository.save(estudante);
    }

    @GetMapping("/estudantes/matricula/{numeroMatricula}")
    public Estudante buscarPorMatricula(@PathVariable String numeroMatricula) {
        return estudanteRepository.findByNumeroMatricula(numeroMatricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/estudantes")
    public List<Estudante> buscarPorNome(@RequestParam("nome") String trechoNome) {
        return estudanteRepository.findByNomeContainingIgnoreCase(trechoNome);
    }
}
