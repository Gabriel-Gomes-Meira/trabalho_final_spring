package br.com.dbc.vemser.trabalhofinal.entity;

public class Especialidade {
    private Integer idEspecialidade;
    private double valor;
    private String nome;

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Especialidade{" +
                "idEspecialidade=" + idEspecialidade +
                ", valor=" + valor +
                ", nome='" + nome + '\'' +
                '}';
    }
}
