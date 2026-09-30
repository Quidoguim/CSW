package br.pucrs.microdemo.estudante.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.pucrs.microdemo.estudante.domain.Estudante;

@DataJpaTest
class EstudanteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Test
    @DisplayName("findByNumeroMatricula deve encontrar o estudante apos persisti-lo")
    void findByNumeroMatricula_aposPersistir_retornaEstudante() {
        // Arrange
        Estudante novo = new Estudante("Juliana", "2021002");
        entityManager.persistAndFlush(novo);

        // Act
        Optional<Estudante> encontrado = estudanteRepository.findByNumeroMatricula("2021002");

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Juliana");
        assertThat(encontrado.get().getId()).isNotNull();
    }

    @Test
    @DisplayName("findByNumeroMatricula deve retornar vazio quando nao existe")
    void findByNumeroMatricula_quandoNaoExiste_retornaVazio() {
        // Act
        Optional<Estudante> encontrado = estudanteRepository.findByNumeroMatricula("0000000");

        // Assert
        assertThat(encontrado).isEmpty();
    }

    @Test
    @DisplayName("findByNomeContainingIgnoreCase deve encontrar por trecho, ignorando caixa")
    void findByNomeContainingIgnoreCase_encontraPorTrechoIgnorandoCaixa() {
        // Arrange
        entityManager.persistAndFlush(new Estudante("Ana Paula", "2021003"));
        entityManager.persistAndFlush(new Estudante("Pedro", "2021004"));

        // Act
        List<Estudante> encontrados = estudanteRepository.findByNomeContainingIgnoreCase("ANA");

        // Assert
        assertThat(encontrados).extracting(Estudante::getNumeroMatricula).containsExactly("2021003");
    }
}
