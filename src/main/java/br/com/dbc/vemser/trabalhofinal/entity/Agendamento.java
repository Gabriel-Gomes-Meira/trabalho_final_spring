package br.com.dbc.vemser.trabalhofinal.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {

    private Integer idAgendamento, idCliente,idMedico;
    private String tratamento, exame;
    private LocalDateTime dataHorario;



    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getExame() {
        return exame;
    }

    public void setExame(String exame) {
        this.exame = exame;
    }

    public LocalDateTime getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(LocalDateTime dataHorario) {
        this.dataHorario = dataHorario;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "idAgendamento=" + idAgendamento +
                ", idCliente=" + idCliente +
                ", idMedico=" + idMedico +
                ", tratamento='" + tratamento + '\'' +
                ", exame='" + exame + '\'' +
                ", dataHorario=" + getDataHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                '}';
    }

}
