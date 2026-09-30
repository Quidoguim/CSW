package br.pucrs.microdemo.disciplina.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.disciplina.domain.Disciplina;
import br.pucrs.microdemo.disciplina.domain.Horario;
import br.pucrs.microdemo.disciplina.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina cadastrar(String codigo, String nome, String horario) {
        Disciplina disciplina = disciplinaRepository.findByCodigo(codigo)
                .orElseGet(() -> new Disciplina(codigo, nome));

        boolean horarioJaExiste = disciplina.getHorarios().stream()
                .anyMatch(h -> h.getCodigo().equalsIgnoreCase(horario));
        if (!horarioJaExiste) {
            disciplina.getHorarios().add(new Horario(horario.toUpperCase(), disciplina));
        }

        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listar() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
