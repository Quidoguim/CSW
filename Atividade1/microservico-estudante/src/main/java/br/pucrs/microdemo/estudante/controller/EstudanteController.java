package br.pucrs.microdemo.estudante.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.pucrs.microdemo.estudante.domain.Estudante;
import br.pucrs.microdemo.estudante.service.EstudanteService;

@RestController
public class EstudanteController {

    private final EstudanteService estudanteService;

    public EstudanteController(EstudanteService estudanteService) {
        this.estudanteService = estudanteService;
    }

    @PostMapping("/estudantes")
    public Estudante cadastrar(@RequestBody Estudante estudante) {
        return estudanteService.cadastrar(estudante);
    }

    @GetMapping("/estudantes/matricula/{numeroMatricula}")
    public Estudante buscarPorMatricula(@PathVariable String numeroMatricula) {
        return estudanteService.buscarPorMatricula(numeroMatricula);
    }

    @GetMapping("/estudantes")
    public List<Estudante> buscarPorNome(@RequestParam("nome") String trechoNome) {
        return estudanteService.buscarPorNome(trechoNome);
    }
}
