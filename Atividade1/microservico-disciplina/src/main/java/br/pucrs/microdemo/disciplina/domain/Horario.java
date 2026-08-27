package br.pucrs.microdemo.disciplina.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "HORARIOS")
public class Horario {

    @Id
    @GeneratedValue
    private Long id;

    private String codigo;

    @ManyToOne
    @JsonBackReference
    private Disciplina disciplina;

    public Horario() {
    }

    public Horario(String codigo, Disciplina disciplina) {
        this.codigo = codigo;
        this.disciplina = disciplina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
