package br.com.dbc.vemser.trabalhofinal.entity;

public class Usuario {
    private Integer idUsuario;
    private String cpf, email, nome, senha;

    private TipoUsuario tipoUsuario;

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return  "Usuario {id_usuario = '" + getIdUsuario() +
                "', nome = '" +
                getNome()+"', " +
                "cpf = '" +
                getCpf()+"', " +
                "email = '" +
                getEmail()+ "', " +
                "idEndereco = '" +
                ", tipoUsuario = '" +
                getTipoUsuario() +
                "'}";
    }
}
