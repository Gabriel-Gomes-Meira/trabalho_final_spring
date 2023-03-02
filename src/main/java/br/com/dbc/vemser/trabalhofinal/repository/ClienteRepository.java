package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import com.dbc.exceptions.BancoDeDadosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClienteRepository implements com.dbc.repository.Repositorio<Integer, Cliente> {

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
            sql.append("INSERT INTO CLIENTE\n" +
                    "(id_cliente, id_usuario,");


            if (cliente.getIdConvenio()!= null) {
                sql.append(" id_convenio,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(") values(?, ?,");
            if (cliente.getIdConvenio() != null) {
                sql.append(" ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(")");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setInt(2, cliente.getIdUsuario());

            if (cliente.getIdConvenio() != null) {
                stmt.setInt(3, cliente.getIdConvenio());
            }

            int res = stmt.executeUpdate();
            System.out.println("adicionarCliente.res=" + res);
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
            System.out.println("removerClientePorId.res=" + res);

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
    public boolean editar(Integer id, Cliente cliente) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CLIENTE SET");

            if (cliente.getIdUsuario()!= null) {
                sql.append(" id_usuario = ?,");
            }
            if (cliente.getIdConvenio() != null) {
                sql.append(" id_convenio = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" where id_cliente = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (cliente.getIdUsuario()!= null) {
                stmt.setInt(index++, cliente.getIdUsuario());
            }
            if (cliente.getIdConvenio()!= null) {
                stmt.setInt(index++, cliente.getIdConvenio());
            }

            stmt.setInt(index, id);
            int res = stmt.executeUpdate();
            System.out.println("editarCliente.res=" + res);
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
    public List<Cliente> listar() throws BancoDeDadosException {
        List<Cliente> clietes = new ArrayList<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM  CLIENTE C " ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = getClienteFromResultSet(res);
                clietes.add(cliente);
            }
            return clietes;
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

    private Cliente getClienteFromResultSet(ResultSet res) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("id_cliente"));
        cliente.setIdUsuario(res.getInt("id_usuario"));
        cliente.setIdConvenio(res.getInt("id_convenio"));
        return cliente;
    }

    public HashMap<String,String> mostrarInformacoesClienteUsuario(Usuario usuarioAtivo) throws BancoDeDadosException {

        HashMap<String,String> dados = new HashMap<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT u.email, u.cpf, u.nome, " +
                    "c.telefone1, e.logradouro, e.numero, e.bairro, e.cidade, e.cep, e.estado, " +
                    "con.cadastro_orgao_regulador, con.taxa_abatimento " +
                    "FROM Cliente ci " +
                    "INNER JOIN USUARIO u ON (u.id_usuario = ci.id_usuario) " +
                    "INNER JOIN ENDERECO e ON (e.id_endereco = u.id_endereco) " +
                    "INNER JOIN CONTATO c ON (c.id_contato = u.id_contato) " +
                    "INNER JOIN CONVENIO CON ON (con.id_convenio = ci.id_convenio) " +
                    "WHERE ci.id_usuario = " + usuarioAtivo.getIdUsuario() ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);
            if(res.next()){
                dados.put("E-mail: ", res.getString("email"));
                dados.put("CPF: ", res.getString("cpf"));
                dados.put("Nome: ", res.getString("nome"));
                dados.put("Telefone: ", res.getString("telefone1"));
                dados.put("Logradouro: ", res.getString("logradouro"));
                dados.put("Número: ", String.valueOf(res.getInt("numero")));
                dados.put("Bairro: ", res.getString("bairro"));
                dados.put("Cidade: ", res.getString("cidade"));
                dados.put("CEP: ", res.getString("cep"));
                dados.put("Estado: ", res.getString("estado"));
                if (res.getString("cadastro_orgao_regulador") != null){
                    dados.put("Convênio: ", String.format("[ Orgão Regulador: %s, Taxa de Abatimento: %.2f ]",
                            res.getString("cadastro_orgao_regulador"), Double.valueOf(res.getDouble("taxa_abatimento"))));
                }
            }


            return dados;
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
}
