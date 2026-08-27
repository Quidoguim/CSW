package br.pucrs.microdemo.disciplina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.disciplina.domain.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    Optional<Disciplina> findByCodigo(String codigo);
}
