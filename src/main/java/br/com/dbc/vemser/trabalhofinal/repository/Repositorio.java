package br.com.dbc.vemser.trabalhofinal.repository;


import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<CHAVE, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO adicionar(OBJETO object) throws BancoDeDadosException, SQLException;

    boolean remover(CHAVE id) throws BancoDeDadosException, SQLException;

    OBJETO editar(CHAVE id, OBJETO objeto) throws BancoDeDadosException, SQLException;

    List<OBJETO> listar() throws BancoDeDadosException, SQLException;
}
