package br.pucrs.microdemo.matricula.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATRICULAS")
public class Matricula {

    @Id
    @GeneratedValue
    private Long id;

    private String numeroMatricula;

    private String codigoDisciplina;

    private String codigoHorario;

    private LocalDateTime dataHora;

    public Matricula() {
    }

    public Matricula(String numeroMatricula, String codigoDisciplina, String codigoHorario) {
        this.numeroMatricula = numeroMatricula;
        this.codigoDisciplina = codigoDisciplina;
        this.codigoHorario = codigoHorario;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getCodigoHorario() {
        return codigoHorario;
    }

    public void setCodigoHorario(String codigoHorario) {
        this.codigoHorario = codigoHorario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
