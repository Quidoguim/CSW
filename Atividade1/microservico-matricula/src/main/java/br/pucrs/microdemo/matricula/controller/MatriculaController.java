package br.pucrs.microdemo.matricula.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.pucrs.microdemo.matricula.domain.Matricula;
import br.pucrs.microdemo.matricula.service.MatriculaService;

@RestController
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    record MatriculaRequest(String numeroMatricula, String codigoDisciplina, String codigoHorario) {
    }

    @PostMapping("/matriculas")
    public Matricula matricular(@RequestBody MatriculaRequest request) {
        return matriculaService.matricular(request.numeroMatricula(), request.codigoDisciplina(),
                request.codigoHorario());
    }

    @GetMapping("/matriculas/estudante/{numeroMatricula}")
    public List<Matricula> listarPorEstudante(@PathVariable String numeroMatricula) {
        return matriculaService.listarPorEstudante(numeroMatricula);
    }
}
