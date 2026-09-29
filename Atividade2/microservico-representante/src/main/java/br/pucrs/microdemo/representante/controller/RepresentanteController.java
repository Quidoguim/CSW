package br.pucrs.microdemo.representante.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.representante.domain.RepresentanteComercial;
import br.pucrs.microdemo.representante.repository.RepresentanteRepository;

@RestController
public class RepresentanteController {

    private final RepresentanteRepository representanteRepository;

    public RepresentanteController(RepresentanteRepository representanteRepository) {
        this.representanteRepository = representanteRepository;
    }

    @PostMapping("/representantes")
    public RepresentanteComercial cadastrar(@RequestBody RepresentanteComercial representante) {
        representante.setId(null);
        return representanteRepository.save(representante);
    }

    @GetMapping("/representantes/cpf/{cpf}")
    public RepresentanteComercial buscarPorCpf(@PathVariable String cpf) {
        return representanteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/representantes")
    public List<RepresentanteComercial> listar(@RequestParam(value = "nome", required = false) String trechoNome) {
        if (trechoNome == null || trechoNome.isBlank()) {
            return representanteRepository.findAll();
        }
        return representanteRepository.findByNomeContainingIgnoreCase(trechoNome);
    }
}
