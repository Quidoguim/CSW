package br.pucrs.microdemo.disciplina.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import br.pucrs.microdemo.disciplina.domain.Disciplina;
import br.pucrs.microdemo.disciplina.domain.Horario;
import br.pucrs.microdemo.disciplina.repository.DisciplinaRepository;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    @Test
    @DisplayName("cadastrar deve criar disciplina nova quando o codigo nao existe")
    void cadastrar_quandoCodigoNaoExiste_criaDisciplinaNova() {
        // Arrange
        when(disciplinaRepository.findByCodigo("CSW01")).thenReturn(Optional.empty());
        when(disciplinaRepository.save(any(Disciplina.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Disciplina resultado = disciplinaService.cadastrar("CSW01", "Construcao de Software", "a");

        // Assert
        assertThat(resultado.getCodigo()).isEqualTo("CSW01");
        assertThat(resultado.getHorarios()).hasSize(1);
        assertThat(resultado.getHorarios().get(0).getCodigo()).isEqualTo("A");
    }

    @Test
    @DisplayName("cadastrar deve adicionar horario novo a disciplina existente que ja tem outro horario diferente")
    void cadastrar_quandoDisciplinaExisteEHorarioNovo_adicionaHorario() {
        // Arrange
        Disciplina existente = new Disciplina("CSW01", "Construcao de Software");
        existente.getHorarios().add(new Horario("A", existente));
        when(disciplinaRepository.findByCodigo("CSW01")).thenReturn(Optional.of(existente));
        when(disciplinaRepository.save(any(Disciplina.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Disciplina resultado = disciplinaService.cadastrar("CSW01", "Construcao de Software", "B");

        // Assert
        assertThat(resultado.getHorarios()).extracting(h -> h.getCodigo()).containsExactlyInAnyOrder("A", "B");
    }

    @Test
    @DisplayName("cadastrar nao deve duplicar horario ja existente, mesmo com caixa diferente")
    void cadastrar_quandoHorarioJaExisteComCaixaDiferente_naoDuplica() {
        // Arrange
        Disciplina existente = new Disciplina("CSW01", "Construcao de Software");
        existente.getHorarios().add(new Horario("A", existente));
        when(disciplinaRepository.findByCodigo("CSW01")).thenReturn(Optional.of(existente));
        when(disciplinaRepository.save(any(Disciplina.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Disciplina resultado = disciplinaService.cadastrar("CSW01", "Construcao de Software", "a");

        // Assert
        assertThat(resultado.getHorarios()).hasSize(1);
    }

    @Test
    @DisplayName("buscarPorCodigo deve retornar a disciplina quando encontrada")
    void buscarPorCodigo_quandoEncontrada_retornaDisciplina() {
        // Arrange
        Disciplina disciplina = new Disciplina("CSW01", "Construcao de Software");
        when(disciplinaRepository.findByCodigo("CSW01")).thenReturn(Optional.of(disciplina));

        // Act
        Disciplina resultado = disciplinaService.buscarPorCodigo("CSW01");

        // Assert
        assertThat(resultado).isSameAs(disciplina);
    }

    @Test
    @DisplayName("buscarPorCodigo deve lancar 404 quando a disciplina nao existe")
    void buscarPorCodigo_quandoNaoExiste_lancaNotFound() {
        // Arrange
        when(disciplinaRepository.findByCodigo("INEXISTENTE")).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> disciplinaService.buscarPorCodigo("INEXISTENTE"));
        assertThat(exception.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    @DisplayName("listar deve delegar ao repositorio")
    void listar_delegaAoRepositorio() {
        // Arrange
        Disciplina disciplina = new Disciplina("CSW01", "Construcao de Software");
        when(disciplinaRepository.findAll()).thenReturn(List.of(disciplina));

        // Act
        List<Disciplina> resultado = disciplinaService.listar();

        // Assert
        assertThat(resultado).containsExactly(disciplina);
    }
}
