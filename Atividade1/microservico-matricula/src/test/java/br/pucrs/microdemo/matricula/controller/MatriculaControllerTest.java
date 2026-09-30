package br.pucrs.microdemo.matricula.controller;

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

import br.pucrs.microdemo.matricula.domain.Matricula;
import br.pucrs.microdemo.matricula.service.MatriculaService;

@WebMvcTest(MatriculaController.class)
class MatriculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatriculaService matriculaService;

    @Test
    @DisplayName("POST /matriculas deve retornar 200 quando a matricula e efetuada")
    void matricular_quandoValido_retornaOk() throws Exception {
        // Arrange
        Matricula salva = new Matricula("2021001", "CSW01", "A");
        when(matriculaService.matricular(eq("2021001"), eq("CSW01"), eq("a"))).thenReturn(salva);

        // Act & Assert
        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroMatricula\":\"2021001\",\"codigoDisciplina\":\"CSW01\",\"codigoHorario\":\"a\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoHorario", is("A")));
    }

    @Test
    @DisplayName("POST /matriculas deve retornar 404 quando o servico lanca NOT_FOUND")
    void matricular_quandoEstudanteOuDisciplinaNaoEncontrada_retorna404() throws Exception {
        // Arrange
        when(matriculaService.matricular(eq("9999999"), eq("CSW01"), eq("A")))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "estudante nao encontrado"));

        // Act & Assert
        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroMatricula\":\"9999999\",\"codigoDisciplina\":\"CSW01\",\"codigoHorario\":\"A\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /matriculas deve retornar 400 quando o servico lanca BAD_REQUEST")
    void matricular_quandoHorarioInvalido_retorna400() throws Exception {
        // Arrange
        when(matriculaService.matricular(eq("2021001"), eq("CSW01"), eq("Z")))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "horario nao disponivel"));

        // Act & Assert
        mockMvc.perform(post("/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroMatricula\":\"2021001\",\"codigoDisciplina\":\"CSW01\",\"codigoHorario\":\"Z\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /matriculas/estudante/{n} deve retornar a lista de matriculas do estudante")
    void listarPorEstudante_retornaLista() throws Exception {
        // Arrange
        Matricula matricula = new Matricula("2021001", "CSW01", "A");
        when(matriculaService.listarPorEstudante("2021001")).thenReturn(List.of(matricula));

        // Act & Assert
        mockMvc.perform(get("/matriculas/estudante/2021001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoDisciplina", is("CSW01")));
    }
}
