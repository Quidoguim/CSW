package br.pucrs.microdemo.disciplina.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.disciplina.domain.Disciplina;
import br.pucrs.microdemo.disciplina.domain.Horario;
import br.pucrs.microdemo.disciplina.repository.DisciplinaRepository;

@RestController
public class DisciplinaController {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaController(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    record CadastroRequest(String codigo, String nome, String horario) {
    }

    @PostMapping("/disciplinas")
    public Disciplina cadastrar(@RequestBody CadastroRequest request) {
        Disciplina disciplina = disciplinaRepository.findByCodigo(request.codigo())
                .orElseGet(() -> new Disciplina(request.codigo(), request.nome()));

        boolean horarioJaExiste = disciplina.getHorarios().stream()
                .anyMatch(h -> h.getCodigo().equalsIgnoreCase(request.horario()));
        if (!horarioJaExiste) {
            disciplina.getHorarios().add(new Horario(request.horario().toUpperCase(), disciplina));
        }

        return disciplinaRepository.save(disciplina);
    }

    @GetMapping("/disciplinas")
    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    @GetMapping("/disciplinas/{codigo}")
    public Disciplina buscarPorCodigo(@PathVariable String codigo) {
        return disciplinaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
