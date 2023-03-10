package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDadosDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Agendamento;
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

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO AGENDAMENTO\n" +
                    "(id_agendamento, id_medico, id_cliente, data_horario,");

            if (agendamento.getTratamento()!= null) {
                sql.append(" tratamento,");
            }
            if (agendamento.getExame()!= null) {
                sql.append(" exame,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(") values(?, ?, ?, TO_DATE( ?, 'yyyy/mm/dd hh24:mi'),");

            if (agendamento.getTratamento() != null) {
                sql.append(" ?,");
            }
            if (agendamento.getExame() != null) {
                sql.append(" ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(")");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 5;
            stmt.setInt(1, agendamento.getIdAgendamento());
            stmt.setInt(2, agendamento.getIdMedico());
            stmt.setInt(3, agendamento.getIdCliente());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            stmt.setString(4, agendamento.getDataHorario().format(formatter));


            if (agendamento.getTratamento() != null) {
                stmt.setString(index++, agendamento.getTratamento());
            }
            if (agendamento.getExame() != null) {
                stmt.setString(index, agendamento.getExame());
            }

            int res = stmt.executeUpdate();
            System.out.println("adicionarAgendamento.res=" + res);
            return agendamento;
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
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM AGENDAMENTO WHERE ID_AGENDAMENTO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerAgendamentoPorId.res=" + res);

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
    public Agendamento editar(Integer id, Agendamento agendamento) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE AGENDAMENTO SET");

            if (agendamento.getIdCliente()!= null) {
                sql.append(" id_cliente = ?,");
            }
            if (agendamento.getIdMedico() != null) {
                sql.append(" id_medico = ?,");
            }
            if (agendamento.getTratamento() != null) {
                sql.append(" tratamento = ?,");
            }
            if (agendamento.getExame() != null) {
                sql.append(" exame = ?,");
            }
            if (agendamento.getDataHorario() != null) {
                sql.append(" data_horario = TO_DATE( ?, 'yyyy/mm/dd hh24:mi'),");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" where id_agendamento = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if (agendamento.getIdCliente()!= null) {
                stmt.setInt(index++, agendamento.getIdCliente());
            }
            if (agendamento.getIdMedico()!= null) {
                stmt.setInt(index++, agendamento.getIdMedico());
            }
            if (agendamento.getTratamento()!= null) {
                stmt.setString(index++, agendamento.getTratamento());
            }
            if (agendamento.getExame()!= null) {
                stmt.setString(index++, agendamento.getExame());
            }
            if (agendamento.getDataHorario()!= null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                String valor = agendamento.getDataHorario().format(formatter);
                stmt.setString(index++, valor);
            }

            stmt.setInt(index, id);
            int res = stmt.executeUpdate();
            System.out.println("editarAgendamento.res=" + res);
            return agendamento;
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
                Agendamento agendamento = getAgendamentoFromResultSet(res);
                agendamentos.add(agendamento);
            }
            return agendamentos;
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
        agendamento.setCliente(res.getString("nome_medico"));
        
        return agendamento;
    }

    public List<AgendamentoDadosDTO> mostrarAgendamentosUsuario(Usuario usuarioAtivo) throws BancoDeDadosException {

        List<AgendamentoDadosDTO> agendamentosDoUsuario = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT a.data_horario, uc.nome AS nome_cliente, um.nome AS nome_medico, a.tratamento, a.exame " +
                    "FROM AGENDAMENTO a " +
                    "INNER JOIN MEDICO m ON (m.id_medico = a.id_medico) " +
                    "INNER JOIN CLIENTE c ON (c.id_cliente = a.id_cliente) " +
                    "WHERE" + (usuarioAtivo.getTipoUsuario().getValor() == 3 ? " c.id_usuario = " : " m.id_usuario = ") + usuarioAtivo.getIdUsuario() +
                    " ORDER BY a.data_horario";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while(res.next()){
                agendamentosDoUsuario.add(getAgendamentoDTOFromResultSet(res));
            }
            return agendamentosDoUsuario;
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
