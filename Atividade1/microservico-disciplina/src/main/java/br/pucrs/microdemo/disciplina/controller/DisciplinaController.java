package br.pucrs.microdemo.disciplina.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.pucrs.microdemo.disciplina.domain.Disciplina;
import br.pucrs.microdemo.disciplina.service.DisciplinaService;

@RestController
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    record CadastroRequest(String codigo, String nome, String horario) {
    }

    @PostMapping("/disciplinas")
    public Disciplina cadastrar(@RequestBody CadastroRequest request) {
        return disciplinaService.cadastrar(request.codigo(), request.nome(), request.horario());
    }

    @GetMapping("/disciplinas")
    public List<Disciplina> listar() {
        return disciplinaService.listar();
    }

    @GetMapping("/disciplinas/{codigo}")
    public Disciplina buscarPorCodigo(@PathVariable String codigo) {
        return disciplinaService.buscarPorCodigo(codigo);
    }
}
