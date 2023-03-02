package br.com.dbc.vemser.trabalhofinal.entity;

public class Convenio {
    private Integer idConvenio;
    private String cadastroOragaoRegulador;

    private Double taxaAbatimento;

    public Integer getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(Integer idConvenio) {
        this.idConvenio = idConvenio;
    }

    public String getCadastroOragaoRegulador() {
        return cadastroOragaoRegulador;
    }

    public void setCadastroOragaoRegulador(String cadastroOragaoRegulador) {
        this.cadastroOragaoRegulador = cadastroOragaoRegulador;
    }

    public Double getTaxaAbatimento() {
        return taxaAbatimento;
    }

    public void setTaxaAbatimento(Double taxaAbatimento) {
        this.taxaAbatimento = taxaAbatimento;
    }

    @Override
    public String toString() {
        return String.format("Convenio {id_convenio = %d, cadastro_orgao_regulador = %s, taxa_abatimento = %.2f}"
                , getIdConvenio(), getCadastroOragaoRegulador(), getTaxaAbatimento());

    }
}
