package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class ClienteRepository implements Repositorio<Integer, Cliente> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_cliente.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Cliente adicionar(Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cliente.setIdCliente(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO CLIENTE (id_cliente, id_usuario, id_convenio) values (?, ?, ?)");

//
//            if (cliente.getIdConvenio()!= null) {
//                sql.append(" id_convenio,");
//            }
//            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
//            sql.append(") values(?, ?,");
//
//            if (cliente.getIdConvenio() != null) {
//                sql.append(" ?,");
//            }
//            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
//            sql.append(")");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setInt(2, cliente.getIdUsuario());
            stmt.setInt(3, cliente.getIdConvenio());

            stmt.executeUpdate();

            return cliente;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Cliente editar(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

//            StringBuilder sql = new StringBuilder();
//            sql.append("UPDATE CLIENTE SET id_convenio = ?, id_convenio = ? WHERE id_cliente = ?");
            String sql = "UPDATE CLIENTE SET id_usuario = ?, id_convenio = ? WHERE id_cliente = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

//            if (cliente.getIdUsuario() != null) {
//                sql.append(" id_usuario = ?,");
//            }
//            if (cliente.getIdConvenio() != null) {
//                sql.append(" id_convenio = ?,");
//            }

//            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
//            sql.append(" where id_cliente = ?");
//            PreparedStatement stmt = con.prepareStatement(sql.toString());

//            int index = 1;
//            if (cliente.getIdUsuario() != null) {
//                stmt.setInt(index++, cliente.getIdUsuario());
//            }
//            if (cliente.getIdConvenio() != null) {
//                stmt.setInt(index++, cliente.getIdConvenio());
//            }

            int index = 1;
            stmt.setInt(index++, cliente.getIdUsuario());
            stmt.setInt(index++, cliente.getIdConvenio());
            stmt.setInt(index, id);

            cliente.setIdCliente(id);

            stmt.executeUpdate();

            return cliente;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Cliente> listar() throws BancoDeDadosException {
        List<Cliente> clietes = new ArrayList<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM  CLIENTE C ";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = getClienteFromResultSet(res);
                clietes.add(cliente);
            }
            return clietes;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Cliente getClienteFromResultSet(ResultSet res) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("id_cliente"));
        cliente.setIdUsuario(res.getInt("id_usuario"));
        cliente.setIdConvenio(res.getInt("id_convenio"));
        return cliente;
    }

}
