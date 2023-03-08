package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dtos.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
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
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cliente.setIdCliente(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO CLIENTE (id_cliente, id_usuario, id_convenio) values (?, ?, ?)");

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

    @Override // ERRO:  integrity constraint (SAUDE.FK_AGENDAMENTO_CLIENTE) violated
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

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
            con = ConexaoBancoDeDados.getConnection();

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
        List<Cliente> clientes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "Select * from Cliente ";


            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cliente cliente = getClienteFromResultSet(res);
                clientes.add(cliente);
            }
            return clientes;
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

    public List<ClienteDTO> listarClienteDTOs() throws BancoDeDadosException {
        List<ClienteDTO> clietes = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT u.id_usuario, u.tipo, u.email, u.email, u.cpf, u.nome, u.contatos, u.endereco, ci.id_cliente, " +
                    "con.cadastro_orgao_regulador, con.taxa_abatimento, con.id_convenio " +
                    "FROM Cliente ci " +
                    "INNER JOIN USUARIO u ON (u.id_usuario = ci.id_usuario) " +
                    "LEFT JOIN CONVENIO CON ON (con.id_convenio = ci.id_convenio) ";


            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                ClienteDTO cliente = getClienteDTOFromResultSet(res);
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

    public ClienteDTO listarClienteDTOId(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            ClienteDTO cliente = null;
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT u.id_usuario, u.tipo,u.email, u.cpf, u.nome, u.contatos, u.endereco, ci.id_cliente, " +
                    "con.cadastro_orgao_regulador, con.taxa_abatimento, con.id_convenio " +
                    "FROM Cliente ci " +
                    "INNER JOIN USUARIO u ON (u.id_usuario = ci.id_usuario) " +
                    "LEFT JOIN CONVENIO CON ON (con.id_convenio = ci.id_convenio) " +
                    "WHERE ci.id_cliente = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                cliente = getClienteDTOFromResultSet(res);
            }
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

    private Cliente getClienteFromResultSet(ResultSet res) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(res.getInt("id_cliente"));
        cliente.setIdUsuario(res.getInt("id_usuario"));
        cliente.setIdConvenio(res.getInt("id_convenio"));
        return cliente;
    }

    private ClienteDTO getClienteDTOFromResultSet(ResultSet res) throws SQLException {
        ConvenioDTO convenioDTO = new ConvenioDTO();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario((res.getInt("id_usuario")));
        usuarioDTO.setTipoUsuario(TipoUsuario.recuperarPeloCodigo(res.getInt("tipo")));
        usuarioDTO.setEmail(res.getString("email"));
        usuarioDTO.setCpf(res.getString("cpf"));
        usuarioDTO.setNome(res.getString("nome"));
        usuarioDTO.setContatos(res.getString("contatos").split("\n"));
        usuarioDTO.setEndereco(res.getString("endereco"));
        if (res.getString("cadastro_orgao_regulador") != null) {
            convenioDTO.setIdConvenio((res.getInt("id_convenio")));
            convenioDTO.setTaxaAbatimento(Double.valueOf(res.getDouble("taxa_abatimento")));
            convenioDTO.setCadastroOragaoRegulador(res.getString("cadastro_orgao_regulador"));
        }
        return new ClienteDTO(res.getInt(("id_cliente")), usuarioDTO, convenioDTO);
    }

}
