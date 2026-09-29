package br.pucrs.microdemo.cliente.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.cliente.domain.Cliente;
import br.pucrs.microdemo.cliente.repository.ClienteRepository;

@RestController
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/clientes")
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    @GetMapping("/clientes/cpf/{cpf}")
    public Cliente buscarPorCpf(@PathVariable String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/clientes")
    public List<Cliente> listar(@RequestParam(value = "nome", required = false) String trechoNome) {
        if (trechoNome == null || trechoNome.isBlank()) {
            return clienteRepository.findAll();
        }
        return clienteRepository.findByNomeContainingIgnoreCase(trechoNome);
    }
}
