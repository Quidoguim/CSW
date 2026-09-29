package br.pucrs.microdemo.peca.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.peca.domain.Peca;
import br.pucrs.microdemo.peca.repository.PecaRepository;

@RestController
public class PecaController {

    private final PecaRepository pecaRepository;

    public PecaController(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    @PostMapping("/pecas")
    public Peca cadastrar(@RequestBody Peca peca) {
        peca.setId(null);
        return pecaRepository.save(peca);
    }

    @GetMapping("/pecas/identificacao/{numeroIdentificacao}")
    public Peca buscarPorIdentificacao(@PathVariable String numeroIdentificacao) {
        return pecaRepository.findByNumeroIdentificacao(numeroIdentificacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pecas")
    public List<Peca> listar(@RequestParam(value = "nome", required = false) String trechoNome) {
        if (trechoNome == null || trechoNome.isBlank()) {
            return pecaRepository.findAll();
        }
        return pecaRepository.findByNomeContainingIgnoreCase(trechoNome);
    }
}
