package br.pucrs.microdemo.peca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.peca.domain.Peca;

public interface PecaRepository extends JpaRepository<Peca, Long> {

    Optional<Peca> findByNumeroIdentificacao(String numeroIdentificacao);

    List<Peca> findByNomeContainingIgnoreCase(String trechoNome);
}
