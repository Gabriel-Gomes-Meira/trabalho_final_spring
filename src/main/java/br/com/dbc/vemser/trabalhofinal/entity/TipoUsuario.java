package br.com.dbc.vemser.trabalhofinal.entity;



public enum TipoUsuario {

    ADMINISTRATIVO(1),
    MEDICO(2),
    CLIENTE(3);

    private Integer valor;

    TipoUsuario(Integer valor){
        this.valor = valor;
    }

    public Integer getValor(){
        return valor;
    }

}
