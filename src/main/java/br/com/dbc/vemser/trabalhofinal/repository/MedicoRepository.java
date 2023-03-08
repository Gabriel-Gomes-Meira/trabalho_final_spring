package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Medico;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class
MedicoRepository implements Repositorio<Integer, Medico> {
    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT seq_medico.nextval mysequence from DUAL";
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
    public Medico adicionar(Medico medico) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            medico.setIdMedico(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO MEDICO\n" +
                    "(id_medico, crm, id_especialidade, id_usuario) VALUES (?, ?, ?, ?)");

            PreparedStatement stmt = con.prepareStatement(sql.toString());


            stmt.setInt(1, medico.getIdMedico());
            stmt.setString(2, medico.getCrm());
            stmt.setInt(3, medico.getIdEspecialidade());
            stmt.setInt(4, medico.getIdUsuario());

            int res = stmt.executeUpdate();
            System.out.println("adicionarMedico.res=" + res);
            return medico;
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

            String sql = "DELETE FROM MEDICO WHERE id_medico = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerMedicoPorId.res=" + res);

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
    public Medico editar(Integer id, Medico medico) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE MEDICO SET ");

            if(medico.getCrm() != null){
                sql.append(" crm = ?,");
            }
            if(medico.getIdEspecialidade() != null){
                sql.append(" id_especialidade = ?,");
            }
            if(medico.getIdUsuario() != null){
                sql.append(" id_usuario = ?,");
            }


            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" WHERE id_medico = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if(medico.getCrm() != null){
                stmt.setString(index++, medico.getCrm());
            }
            if(medico.getIdEspecialidade() != null){
                stmt.setInt(index++, medico.getIdEspecialidade());
            }
            if(medico.getIdUsuario() != null){
                stmt.setInt(index++, medico.getIdUsuario());
            }


            stmt.setInt(index, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarMedico.res=" + res);

            return medico;
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
    public List<Medico> listar() throws BancoDeDadosException {
        List<Medico> listaMedico = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM MEDICO M " ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Medico medico = getMedicoFromResultSet(res);
                listaMedico.add(medico);
            }
            return listaMedico;
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

    private Medico getMedicoFromResultSet(ResultSet res) throws SQLException {
        Medico medico = new Medico();
        medico.setIdMedico(res.getInt("id_medico"));
        medico.setCrm(res.getString("crm"));
        medico.setIdEspecialidade(res.getInt("id_especialidade"));
        medico.setIdUsuario(res.getInt("id_usuario"));

        return medico;
    }

    public List<MedicoDTO> listarMedicosUsuariosDTOs() throws BancoDeDadosException {
        List<MedicoDTO> medicos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT u.id_usuario, u.tipo, u.email, u.email, u.cpf, u.nome, u.contatos, u.endereco, m.id_medico, m.crm, " +
                    "es.id_especialidade, es.valor, es.nome AS especialidade " +
                    "FROM Medico m " +
                    "INNER JOIN USUARIO u ON (u.id_usuario = m.id_usuario) " +
                    "LEFT JOIN ESPECIALIDADE es ON (es.id_especialidade = m.id_especialidade) ";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                MedicoDTO medico = getMedicoDTOFromResultSet(res);
                medicos.add(medico);
            }
            return medicos;
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

    public MedicoDTO getMedicoDTO(Integer id) throws BancoDeDadosException {
        MedicoDTO medicoDTO = new MedicoDTO();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT u.id_usuario, u.tipo, u.email, u.email, u.cpf, u.nome, u.contatos, u.endereco, m.id_medico, m.crm, " +
                    "es.id_especialidade, es.valor, es.nome AS especialidade " +
                    "FROM Medico m " +
                    "INNER JOIN USUARIO u ON (u.id_usuario = m.id_usuario) " +
                    "LEFT JOIN ESPECIALIDADE es ON (es.id_especialidade = m.id_especialidade) " +
                    "WHERE m.id_medico = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                medicoDTO = getMedicoDTOFromResultSet(res);
            }
            return medicoDTO;
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

    private MedicoDTO getMedicoDTOFromResultSet(ResultSet res) throws SQLException {
        EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        MedicoDTO medicoDTO = new MedicoDTO();

        usuarioDTO.setIdUsuario((res.getInt("id_usuario")));
        usuarioDTO.setTipoUsuario(TipoUsuario.recuperarPeloCodigo(res.getInt("tipo")));
        usuarioDTO.setEmail(res.getString("email"));
        usuarioDTO.setCpf(res.getString("cpf"));
        usuarioDTO.setNome(res.getString("nome"));
        usuarioDTO.setContatos(res.getString("contatos").split("\n"));
        usuarioDTO.setEndereco(res.getString("endereco"));

        especialidadeDTO.setIdEspecialidade(res.getInt("id_Especialidade"));
        especialidadeDTO.setValor(res.getDouble("valor"));
        especialidadeDTO.setNome(res.getString("especialidade"));

        medicoDTO.setEspecialidadeDTO(especialidadeDTO);
        medicoDTO.setIdMedico(res.getInt("id_medico"));
        medicoDTO.setCrm(res.getString("crm").trim());
        medicoDTO.setUsuarioDTO(usuarioDTO);
        medicoDTO.setIdEspecialidade(res.getInt("id_especialidade"));
        medicoDTO.setIdUsuario(res.getInt("id_usuario"));

        return medicoDTO;
    }

}
