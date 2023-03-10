package br.com.dbc.vemser.trabalhofinal.service;

public enum TipoEmail {

    USUARIO_CADASTRO("Cadastro no sistema"),
    AGENDAMENTO_CRIADO_CLIENTE("Novo agendamento"),
    AGENDAMENTO_EDITADO_CLIENTE("Agendamento alterado"),
    AGENDAMENTO_CANCELADO_CLIENTE("Agendamento removido"),
    AGENDAMENTO_CRIADO_MEDICO("Novo agendamento"),
    AGENDAMENTO_EDITADO_MEDICO("Agendamento alterado"),
    AGENDAMENTO_CANCELADO_MEDICO("Agendamento removido");


    private String assunto;

    TipoEmail(String assunto){
        this.assunto = assunto;
    }

    public String getAssunto(){
        return assunto;
    }

}
