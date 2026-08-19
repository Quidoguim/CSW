package br.pucrs.microdemo.matricula.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.matricula.domain.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByNumeroMatricula(String numeroMatricula);
}
