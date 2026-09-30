package br.pucrs.microdemo.estudante.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.pucrs.microdemo.estudante.domain.Estudante;
import br.pucrs.microdemo.estudante.service.EstudanteService;

@WebMvcTest(EstudanteController.class)
class EstudanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstudanteService estudanteService;

    @Test
    @DisplayName("POST /estudantes deve retornar 200 e o estudante cadastrado")
    void cadastrar_retornaOkComEstudante() throws Exception {
        // Arrange
        Estudante requisicao = new Estudante("Carlos", "2021001");
        Estudante salvo = new Estudante("Carlos", "2021001");
        salvo.setId(1L);
        when(estudanteService.cadastrar(any(Estudante.class))).thenReturn(salvo);

        // Act & Assert
        mockMvc.perform(post("/estudantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Carlos")));
    }

    @Test
    @DisplayName("GET /estudantes/matricula/{n} deve retornar 200 quando encontrado")
    void buscarPorMatricula_quandoEncontrado_retornaOk() throws Exception {
        // Arrange
        Estudante estudante = new Estudante("Carlos", "2021001");
        estudante.setId(1L);
        when(estudanteService.buscarPorMatricula("2021001")).thenReturn(estudante);

        // Act & Assert
        mockMvc.perform(get("/estudantes/matricula/2021001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroMatricula", is("2021001")));
    }

    @Test
    @DisplayName("GET /estudantes/matricula/{n} deve retornar 404 quando o servico lanca NOT_FOUND")
    void buscarPorMatricula_quandoNaoEncontrado_retorna404() throws Exception {
        // Arrange
        when(estudanteService.buscarPorMatricula("9999999"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/estudantes/matricula/9999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /estudantes?nome= deve retornar a lista filtrada")
    void buscarPorNome_retornaListaFiltrada() throws Exception {
        // Arrange
        Estudante estudante = new Estudante("Carlos", "2021001");
        estudante.setId(1L);
        when(estudanteService.buscarPorNome(eq("carl"))).thenReturn(List.of(estudante));

        // Act & Assert
        mockMvc.perform(get("/estudantes").param("nome", "carl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is("Carlos")));
    }
}
