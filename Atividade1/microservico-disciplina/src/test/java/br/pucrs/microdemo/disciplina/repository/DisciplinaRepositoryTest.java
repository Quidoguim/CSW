package br.pucrs.microdemo.disciplina.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.pucrs.microdemo.disciplina.domain.Disciplina;
import br.pucrs.microdemo.disciplina.domain.Horario;

@DataJpaTest
class DisciplinaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Test
    @DisplayName("findByCodigo deve encontrar a disciplina apos persisti-la")
    void findByCodigo_aposPersistir_retornaDisciplina() {
        // Arrange
        Disciplina nova = new Disciplina("CSW01", "Construcao de Software");
        entityManager.persistAndFlush(nova);

        // Act
        Optional<Disciplina> encontrada = disciplinaRepository.findByCodigo("CSW01");

        // Assert
        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNome()).isEqualTo("Construcao de Software");
    }

    @Test
    @DisplayName("salvar a disciplina deve persistir os horarios em cascata, sem salva-los a parte")
    void salvarDisciplina_persisteHorariosEmCascata() {
        // Arrange
        Disciplina disciplina = new Disciplina("CSW01", "Construcao de Software");
        disciplina.getHorarios().add(new Horario("A", disciplina));

        // Act
        Disciplina salva = entityManager.persistFlushFind(disciplina);

        // Assert
        assertThat(salva.getHorarios()).hasSize(1);
        assertThat(salva.getHorarios().get(0).getId()).isNotNull();
    }

    @Test
    @DisplayName("remover um horario da lista e salvar deve apagar o registro orfao (orphanRemoval)")
    void removerHorarioDaLista_aposSalvar_apagaRegistroOrfao() {
        // Arrange
        Disciplina disciplina = new Disciplina("CSW01", "Construcao de Software");
        disciplina.getHorarios().add(new Horario("A", disciplina));
        disciplina.getHorarios().add(new Horario("B", disciplina));
        Disciplina persistida = entityManager.persistFlushFind(disciplina);
        Long idHorarioRemovido = persistida.getHorarios().get(0).getId();

        // Act
        persistida.getHorarios().remove(0);
        disciplinaRepository.save(persistida);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Disciplina recarregada = disciplinaRepository.findByCodigo("CSW01").orElseThrow();
        assertThat(recarregada.getHorarios()).hasSize(1);
        assertThat(recarregada.getHorarios())
                .extracting(Horario::getId)
                .doesNotContain(idHorarioRemovido);
    }
}
