package br.pucrs.microdemo.representante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.pucrs.microdemo.representante.domain.RepresentanteComercial;

public interface RepresentanteRepository extends JpaRepository<RepresentanteComercial, Long> {

    Optional<RepresentanteComercial> findByCpf(String cpf);

    List<RepresentanteComercial> findByNomeContainingIgnoreCase(String trechoNome);
}
