package br.pucrs.microdemo.disciplina.controller;

import static org.hamcrest.Matchers.is;
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

import br.pucrs.microdemo.disciplina.domain.Disciplina;
import br.pucrs.microdemo.disciplina.domain.Horario;
import br.pucrs.microdemo.disciplina.service.DisciplinaService;

@WebMvcTest(DisciplinaController.class)
class DisciplinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DisciplinaService disciplinaService;

    @Test
    @DisplayName("POST /disciplinas deve retornar 200 com a disciplina cadastrada")
    void cadastrar_retornaOkComDisciplina() throws Exception {
        // Arrange
        Disciplina salva = new Disciplina("CSW01", "Construcao de Software");
        salva.getHorarios().add(new Horario("A", salva));
        when(disciplinaService.cadastrar(eq("CSW01"), eq("Construcao de Software"), eq("a"))).thenReturn(salva);

        // Act & Assert
        mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\":\"CSW01\",\"nome\":\"Construcao de Software\",\"horario\":\"a\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is("CSW01")))
                .andExpect(jsonPath("$.horarios[0].codigo", is("A")));
    }

    @Test
    @DisplayName("GET /disciplinas deve retornar a lista completa")
    void listar_retornaListaCompleta() throws Exception {
        // Arrange
        Disciplina disciplina = new Disciplina("CSW01", "Construcao de Software");
        when(disciplinaService.listar()).thenReturn(List.of(disciplina));

        // Act & Assert
        mockMvc.perform(get("/disciplinas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo", is("CSW01")));
    }

    @Test
    @DisplayName("GET /disciplinas/{codigo} deve retornar 200 quando encontrada")
    void buscarPorCodigo_quandoEncontrada_retornaOk() throws Exception {
        // Arrange
        Disciplina disciplina = new Disciplina("CSW01", "Construcao de Software");
        when(disciplinaService.buscarPorCodigo("CSW01")).thenReturn(disciplina);

        // Act & Assert
        mockMvc.perform(get("/disciplinas/CSW01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Construcao de Software")));
    }

    @Test
    @DisplayName("GET /disciplinas/{codigo} deve retornar 404 quando nao encontrada")
    void buscarPorCodigo_quandoNaoEncontrada_retorna404() throws Exception {
        // Arrange
        when(disciplinaService.buscarPorCodigo("INEXISTENTE"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/disciplinas/INEXISTENTE"))
                .andExpect(status().isNotFound());
    }
}
