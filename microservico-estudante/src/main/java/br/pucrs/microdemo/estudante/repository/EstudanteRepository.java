package br.pucrs.microdemo.estudante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.estudante.domain.Estudante;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    Optional<Estudante> findByNumeroMatricula(String numeroMatricula);

    List<Estudante> findByNomeContainingIgnoreCase(String trechoNome);
}
