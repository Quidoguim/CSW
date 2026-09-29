package br.pucrs.microdemo.representante.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "REPRESENTANTES")
public class RepresentanteComercial {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String nome;

    public RepresentanteComercial() {
    }

    public RepresentanteComercial(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
