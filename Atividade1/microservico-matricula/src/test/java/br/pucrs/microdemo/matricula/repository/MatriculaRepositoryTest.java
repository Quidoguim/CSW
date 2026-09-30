package br.pucrs.microdemo.matricula.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.pucrs.microdemo.matricula.domain.Matricula;

@DataJpaTest
class MatriculaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Test
    @DisplayName("findByNumeroMatricula deve retornar apenas as matriculas do estudante pedido")
    void findByNumeroMatricula_retornaApenasMatriculasDoEstudante() {
        // Arrange
        entityManager.persistAndFlush(new Matricula("2021001", "CSW01", "A"));
        entityManager.persistAndFlush(new Matricula("2021001", "CSW02", "B"));
        entityManager.persistAndFlush(new Matricula("2021002", "CSW01", "A"));

        // Act
        List<Matricula> resultado = matriculaRepository.findByNumeroMatricula("2021001");

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(Matricula::getCodigoDisciplina).containsExactlyInAnyOrder("CSW01", "CSW02");
    }

    @Test
    @DisplayName("findByNumeroMatricula deve retornar lista vazia quando o estudante nao tem matriculas")
    void findByNumeroMatricula_quandoSemMatriculas_retornaListaVazia() {
        // Act
        List<Matricula> resultado = matriculaRepository.findByNumeroMatricula("0000000");

        // Assert
        assertThat(resultado).isEmpty();
    }
}
