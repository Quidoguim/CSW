package br.pucrs.microdemo.peca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PECAS")
public class Peca {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "numero_identificacao", unique = true, nullable = false)
    private String numeroIdentificacao;

    private String nome;

    private String descricao;

    public Peca() {
    }

    public Peca(String numeroIdentificacao, String nome, String descricao) {
        this.numeroIdentificacao = numeroIdentificacao;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
