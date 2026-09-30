package br.pucrs.microdemo.matricula.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import br.pucrs.microdemo.matricula.client.DisciplinaClient;
import br.pucrs.microdemo.matricula.client.DisciplinaClient.DisciplinaDTO;
import br.pucrs.microdemo.matricula.client.DisciplinaClient.HorarioDTO;
import br.pucrs.microdemo.matricula.client.EstudanteClient;
import br.pucrs.microdemo.matricula.client.EstudanteClient.EstudanteDTO;
import br.pucrs.microdemo.matricula.domain.Matricula;
import br.pucrs.microdemo.matricula.repository.MatriculaRepository;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private EstudanteClient estudanteClient;

    @Mock
    private DisciplinaClient disciplinaClient;

    @InjectMocks
    private MatriculaService matriculaService;

    @Test
    @DisplayName("matricular deve lancar 404 quando o estudante nao existe, sem consultar a disciplina")
    void matricular_quandoEstudanteNaoExiste_lancaNotFoundSemConsultarDisciplina() {
        // Arrange
        when(estudanteClient.buscarPorMatricula("9999999")).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> matriculaService.matricular("9999999", "CSW01", "A"));
        assertThat(exception.getStatusCode().value()).isEqualTo(404);
        verifyNoInteractions(disciplinaClient);
        verify(matriculaRepository, never()).save(any());
    }

    @Test
    @DisplayName("matricular deve lancar 404 quando a disciplina nao existe")
    void matricular_quandoDisciplinaNaoExiste_lancaNotFound() {
        // Arrange
        when(estudanteClient.buscarPorMatricula("2021001"))
                .thenReturn(Optional.of(new EstudanteDTO(1L, "Carlos", "2021001")));
        when(disciplinaClient.buscarPorCodigo("INEXISTENTE")).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> matriculaService.matricular("2021001", "INEXISTENTE", "A"));
        assertThat(exception.getStatusCode().value()).isEqualTo(404);
        verify(matriculaRepository, never()).save(any());
    }

    @Test
    @DisplayName("matricular deve lancar 400 quando o horario pedido nao existe na disciplina")
    void matricular_quandoHorarioNaoDisponivel_lancaBadRequest() {
        // Arrange
        when(estudanteClient.buscarPorMatricula("2021001"))
                .thenReturn(Optional.of(new EstudanteDTO(1L, "Carlos", "2021001")));
        DisciplinaDTO disciplina = new DisciplinaDTO(1L, "CSW01", "Construcao de Software",
                List.of(new HorarioDTO(1L, "A")));
        when(disciplinaClient.buscarPorCodigo("CSW01")).thenReturn(Optional.of(disciplina));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> matriculaService.matricular("2021001", "CSW01", "Z"));
        assertThat(exception.getStatusCode().value()).isEqualTo(400);
        verify(matriculaRepository, never()).save(any());
    }

    @Test
    @DisplayName("matricular deve salvar a matricula com o horario em maiusculas quando tudo e valido")
    void matricular_quandoValido_salvaComHorarioEmMaiusculas() {
        // Arrange
        when(estudanteClient.buscarPorMatricula("2021001"))
                .thenReturn(Optional.of(new EstudanteDTO(1L, "Carlos", "2021001")));
        DisciplinaDTO disciplina = new DisciplinaDTO(1L, "CSW01", "Construcao de Software",
                List.of(new HorarioDTO(1L, "A")));
        when(disciplinaClient.buscarPorCodigo("CSW01")).thenReturn(Optional.of(disciplina));
        when(matriculaRepository.save(any(Matricula.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Matricula resultado = matriculaService.matricular("2021001", "CSW01", "a");

        // Assert
        assertThat(resultado.getCodigoHorario()).isEqualTo("A");
        assertThat(resultado.getNumeroMatricula()).isEqualTo("2021001");
    }

    @Test
    @DisplayName("listarPorEstudante deve delegar ao repositorio")
    void listarPorEstudante_delegaAoRepositorio() {
        // Arrange
        Matricula matricula = new Matricula("2021001", "CSW01", "A");
        when(matriculaRepository.findByNumeroMatricula("2021001")).thenReturn(List.of(matricula));

        // Act
        List<Matricula> resultado = matriculaService.listarPorEstudante("2021001");

        // Assert
        assertThat(resultado).containsExactly(matricula);
    }
}
