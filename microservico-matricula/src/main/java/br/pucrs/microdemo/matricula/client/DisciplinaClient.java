package br.pucrs.microdemo.matricula.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class DisciplinaClient {

    public record HorarioDTO(Long id, String codigo) {
    }

    public record DisciplinaDTO(Long id, String codigo, String nome, List<HorarioDTO> horarios) {
    }

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public DisciplinaClient(RestTemplate restTemplate, @Value("${services.disciplina.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<DisciplinaDTO> buscarPorCodigo(String codigo) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(
                    baseUrl + "/disciplinas/{codigo}", DisciplinaDTO.class, codigo));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
