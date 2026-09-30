package br.pucrs.microdemo.estudante.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

import br.pucrs.microdemo.estudante.domain.Estudante;
import br.pucrs.microdemo.estudante.repository.EstudanteRepository;

@ExtendWith(MockitoExtension.class)
class EstudanteServiceTest {

    @Mock
    private EstudanteRepository estudanteRepository;

    @InjectMocks
    private EstudanteService estudanteService;

    @Test
    @DisplayName("cadastrar deve zerar o id antes de salvar")
    void cadastrar_comIdPreenchido_ignoraIdEChamaSave() {
        // Arrange
        Estudante estudante = new Estudante("Carlos", "2021001");
        estudante.setId(99L);
        when(estudanteRepository.save(any(Estudante.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Estudante resultado = estudanteService.cadastrar(estudante);

        // Assert
        assertThat(resultado.getId()).isNull();
        verify(estudanteRepository).save(estudante);
    }

    @Test
    @DisplayName("buscarPorMatricula deve retornar o estudante quando encontrado")
    void buscarPorMatricula_quandoEncontrado_retornaEstudante() {
        // Arrange
        Estudante estudante = new Estudante("Carlos", "2021001");
        when(estudanteRepository.findByNumeroMatricula("2021001")).thenReturn(Optional.of(estudante));

        // Act
        Estudante resultado = estudanteService.buscarPorMatricula("2021001");

        // Assert
        assertThat(resultado).isSameAs(estudante);
    }

    @Test
    @DisplayName("buscarPorMatricula deve lancar 404 quando nao encontrado")
    void buscarPorMatricula_quandoNaoEncontrado_lancaNotFound() {
        // Arrange
        when(estudanteRepository.findByNumeroMatricula("9999999")).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> estudanteService.buscarPorMatricula("9999999"));
        assertThat(exception.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    @DisplayName("buscarPorNome deve delegar ao repositorio e retornar a lista")
    void buscarPorNome_delegaAoRepositorioERetornaLista() {
        // Arrange
        Estudante estudante = new Estudante("Carlos", "2021001");
        when(estudanteRepository.findByNomeContainingIgnoreCase("carl")).thenReturn(List.of(estudante));

        // Act
        List<Estudante> resultado = estudanteService.buscarPorNome("carl");

        // Assert
        assertThat(resultado).containsExactly(estudante);
        verify(estudanteRepository, never()).findAll();
    }
}
