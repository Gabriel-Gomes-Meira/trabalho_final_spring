package br.com.dbc.vemser.trabalhofinal.entity;

public class Administrativo{

    private Integer idAdministrativo;
    private Integer idUsuario;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdAdministrativo() {
        return idAdministrativo;
    }

    public void setIdAdministrativo(Integer idAdministrativo) {
        this.idAdministrativo = idAdministrativo;
    }

    @Override
    public String toString(){
        return "Administrativo{idAdministrativo='" +
                getIdAdministrativo() + "'" +
                ", idUsuario='" +
                getIdUsuario() + "'}";
    }

}
