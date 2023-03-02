package br.com.dbc.vemser.trabalhofinal.entity;

public class Medico {
    private Integer idMedico;
    private String crm;
    private Integer idEspecialidade;
    private Integer idUsuario;


    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Medico{" +
                "idMedico=" + idMedico +
                ", crm='" + crm + '\'' +
                ", idEspecialidade=" + idEspecialidade +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
