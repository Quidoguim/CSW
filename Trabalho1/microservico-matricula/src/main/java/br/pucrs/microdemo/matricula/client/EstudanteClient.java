package br.pucrs.microdemo.matricula.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EstudanteClient {

    public record EstudanteDTO(Long id, String nome, String numeroMatricula) {
    }

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public EstudanteClient(RestTemplate restTemplate, @Value("${services.estudante.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<EstudanteDTO> buscarPorMatricula(String numeroMatricula) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(
                    baseUrl + "/estudantes/matricula/{numeroMatricula}", EstudanteDTO.class, numeroMatricula));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
