package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDadosDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Agendamento;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class AgendamentoRepository implements Repositorio<Integer, Agendamento> {


    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_agendamento.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Agendamento adicionar(Agendamento agendamento) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            agendamento.setIdAgendamento(proximoId);

            String sql ="INSERT INTO AGENDAMENTO (id_agendamento, id_medico, id_cliente, data_horario, tratamento, " +
                    "exame) values(?, ?, ?, TO_DATE( ?, 'yyyy/mm/dd hh24:mi'), ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, agendamento.getIdAgendamento());
            stmt.setInt(2, agendamento.getIdMedico());
            stmt.setInt(3, agendamento.getIdCliente());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            stmt.setString(4, agendamento.getDataHorario().format(formatter));

            stmt.setString(5, agendamento.getTratamento());
            stmt.setString(6, agendamento.getExame());

            return agendamento;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM AGENDAMENTO WHERE ID_AGENDAMENTO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Agendamento editar(Integer id, Agendamento agendamento) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE AGENDAMENTO SET id_cliente = ?, id_medico = ?,  tratamento = ?, exame = ?, " +
                    "data_horario = TO_DATE( ?, 'yyyy/mm/dd hh24:mi')  where id_agendamento = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, agendamento.getIdCliente());
            stmt.setInt(2, agendamento.getIdMedico());
            stmt.setString(3, agendamento.getTratamento());
            stmt.setString(4, agendamento.getExame());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            String valor = agendamento.getDataHorario().format(formatter);
            stmt.setString(5, valor);

            stmt.setInt(6, id);
            stmt.executeUpdate();

            agendamento.setIdAgendamento(id);
            return agendamento;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Agendamento> listar() throws BancoDeDadosException {
        List<Agendamento> agendamentos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM  AGENDAMENTO A " ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                agendamentos.add(getAgendamentoFromResultSet(res));
            }
            return agendamentos;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private Agendamento getAgendamentoFromResultSet(ResultSet res) throws SQLException {
        Agendamento agendamento = new Agendamento();
        agendamento.setIdAgendamento(res.getInt("id_agendamento"));
        agendamento.setIdCliente(res.getInt("id_cliente"));
        agendamento.setIdMedico(res.getInt("id_medico"));
        agendamento.setTratamento(res.getString("tratamento"));
        agendamento.setExame(res.getString("exame"));
        agendamento.setDataHorario(LocalDateTime.parse(res.getString("data_horario"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return agendamento;
    }
    private AgendamentoDadosDTO getAgendamentoDTOFromResultSet(ResultSet res) throws SQLException {
        AgendamentoDadosDTO agendamento = new AgendamentoDadosDTO();
        agendamento.setIdAgendamento(res.getInt("id_agendamento"));
        agendamento.setIdCliente(res.getInt("id_cliente"));
        agendamento.setIdMedico(res.getInt("id_medico"));
        agendamento.setTratamento(res.getString("tratamento"));
        agendamento.setExame(res.getString("exame"));
        agendamento.setDataHorario(LocalDateTime.parse(res.getString("data_horario"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        agendamento.setCliente(res.getString("nome_cliente"));
        agendamento.setMedico(res.getString("nome_medico"));
        
        return agendamento;
    }

    public List<AgendamentoDadosDTO> mostrarAgendamentosUsuario(Integer id, TipoUsuario tipoUsuario) throws BancoDeDadosException {

        List<AgendamentoDadosDTO> agendamentosDoUsuario = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT a.data_horario, cliente_usuario.nome AS nome_cliente, medico_usuario.nome AS nome_medico, a.tratamento, a.exame, " +
                    "m.id_medico as id_medico, c.id_cliente as id_cliente, a.id_agendamento " +
                    "FROM AGENDAMENTO a " +
                    "INNER JOIN MEDICO m ON (m.id_medico = a.id_medico) " +
                    "INNER JOIN CLIENTE c ON (c.id_cliente = a.id_cliente) " +
                    "INNER JOIN USUARIO medico_usuario on (m.id_usuario = medico_usuario.id_usuario)" +
                    "INNER JOIN USUARIO cliente_usuario on (c.id_usuario = cliente_usuario.id_usuario)" +
                    "WHERE" + (tipoUsuario.getValor() == 3 ? " c.id_cliente = ? " : " m.id_medico = ? ") +
                    "ORDER BY a.data_horario";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            while(res.next()){
                agendamentosDoUsuario.add(getAgendamentoDTOFromResultSet(res));
            }
            return agendamentosDoUsuario;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public AgendamentoDadosDTO getAgendamentoDados(Integer id) throws BancoDeDadosException {

        AgendamentoDadosDTO dadosAgendamento = null;
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT a.data_horario, uc.nome AS nome_cliente, um.nome AS nome_medico, a.tratamento, a.exame " +
                    "FROM AGENDAMENTO a " +
                    "INNER JOIN MEDICO m ON (m.id_medico = a.id_medico) " +
                    "INNER JOIN CLIENTE c ON (c.id_cliente = a.id_cliente) " +
                    "WHERE a.id_agendamento = ?" ;

            // Executa-se a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery(sql);

            if(res.next()){
                dadosAgendamento = getAgendamentoDTOFromResultSet(res);
            }
            return dadosAgendamento;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Agendamento getAgendamento(Integer id) throws BancoDeDadosException {
        Connection con = null;
        Agendamento agendamento = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * " +
                    "FROM AGENDAMENTO " +
                    "WHERE id_agendamento = ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                agendamento = getAgendamentoFromResultSet(res);
            }
            return agendamento;
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
