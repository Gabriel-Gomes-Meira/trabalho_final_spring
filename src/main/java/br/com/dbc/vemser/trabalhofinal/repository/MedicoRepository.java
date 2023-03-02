package com.dbc.repository;

import br.com.dbc.vemser.trabalhofinal.entity.Medico;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import com.dbc.exceptions.BancoDeDadosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicoRepository implements com.dbc.repository.Repositorio<Integer, Medico> {
    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT seq_endereco.nextval mysequence from DUAL";
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
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

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
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

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
    public boolean editar(Integer id, Medico medico) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

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
    public List<Medico> listar() throws BancoDeDadosException {
        List<Medico> listaMedico = new ArrayList<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
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

    public HashMap<String,String> mostrarInformacoesMedicoUsuario(Usuario usuarioAtivo) throws BancoDeDadosException {

        HashMap<String,String> dados = new HashMap<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT u.email, u.cpf, u.nome, c.telefone1, e.logradouro, e.numero, e.bairro, e.cidade, e.cep, e.estado, m.crm, es.nome AS especialidade " +
                    "FROM MEDICO m " +
                    "INNER JOIN USUARIO u ON (u.id_usuario = m.id_usuario) " +
                    "INNER JOIN ENDERECO e ON (e.id_endereco = u.id_endereco) " +
                    "INNER JOIN CONTATO c ON (c.id_contato = u.id_contato) " +
                    "INNER JOIN ESPECIALIDADE es ON (es.id_especialidade = m.id_especialidade) " +
                    "WHERE m.id_usuario = " + usuarioAtivo.getIdUsuario() ;

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
                dados.put("CRM: ", res.getString("crm"));
                dados.put("Especialidade:: ", res.getString("especialidade"));
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
