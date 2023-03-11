package br.com.dbc.vemser.trabalhofinal.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConexaoBancoDeDados {
//    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
//    private static final String PORT = "25000"; // Porta TCP padrão do Oracle
//    private static final String DATABASE = "xe";
//
//    // Configuração dos parâmetros de autenticação
//    private static final String USER = "SAUDE";
//    private static final String PASS = "ZKcUQpyWvUto";
//    private static final String SCHEMA = "SAUDE";

    @Value("${db.oracle.server}")
    private String server;
    @Value("${db.oracle.port}")
    private String port;
    @Value("${db.oracle.database}")
    private String databse;
    @Value("${db.oracle.user}")
    private String user;
    @Value("${db.oracle.password}")
    private String password;
    @Value("${db.oracle.scheme}")
    private String scheme;


    public Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + databse;

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, user, password);

        // sempre usar o schema vem_ser
        con.createStatement().execute("alter session set current_schema=" + scheme);

        return con;
    }
}
