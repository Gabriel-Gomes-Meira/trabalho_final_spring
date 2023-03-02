package com.dbc.repository;

import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import com.dbc.exceptions.BancoDeDadosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConvenioRepository implements com.dbc.repository.Repositorio<Integer, Convenio> {


    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_convenio.nextval mysequence from DUAL";
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
    public Convenio adicionar(Convenio convenio) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            convenio.setIdConvenio(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO CONVENIO (id_convenio, cadastro_orgao_regulador, taxa_abatimento) " +
                    "values (?, ?, ?)");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, convenio.getIdConvenio());
            stmt.setString(2, convenio.getCadastroOragaoRegulador());
            stmt.setDouble(3, convenio.getTaxaAbatimento());

            int res = stmt.executeUpdate();
            System.out.println("adicionarUsuario.res=" + res);
            return convenio;
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

            String sql = "DELETE FROM CONVENIO WHERE ID_CONVENIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerConvenioPorId.res=" + res);

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
    public boolean editar(Integer id, Convenio convenio) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CONVENIO SET ");

            if (convenio.getTaxaAbatimento() != null) {
                sql.append(" taxa_abatimento = ?,");
            }
            if (convenio.getCadastroOragaoRegulador()!= null) {
                sql.append(" cadastro_orgao_regulador = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" where id_convenio = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;

            if (convenio.getCadastroOragaoRegulador() != null) {
                stmt.setString(index++, convenio.getCadastroOragaoRegulador());
            }
            if (convenio.getTaxaAbatimento() != null) {
                stmt.setDouble(index++, convenio.getTaxaAbatimento());
            }

            stmt.setInt(index, id);

            int res = stmt.executeUpdate();
            System.out.println("editarConvenio.res=" + res);
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
    public List<Convenio> listar() throws BancoDeDadosException {
        List<Convenio> convenios = new ArrayList<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM CONVENIO " ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Convenio convenio = getConvenioFromResultSet(res);
                convenios.add(convenio);
            }
            return convenios;
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

    private Convenio getConvenioFromResultSet(ResultSet res) throws SQLException {
        Convenio convenio = new Convenio();
        convenio.setIdConvenio(res.getInt("id_convenio"));
        convenio.setTaxaAbatimento(res.getDouble("taxa_abatimento"));
        convenio.setCadastroOragaoRegulador(res.getString("cadastro_orgao_regulador"));
        return convenio;
    }
}
