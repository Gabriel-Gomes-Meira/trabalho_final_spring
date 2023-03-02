package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import com.dbc.exceptions.BancoDeDadosException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministrativoRepository implements com.dbc.repository.Repositorio<Integer, Administrativo> {


    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_administrativo.nextval mysequence from DUAL";
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
    public Administrativo adicionar(Administrativo administrativo) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            administrativo.setIdAdministrativo(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ADMINISTRATIVO\n" +
                    "(id_administrativo, id_usuario) values(?, ?)" );

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, administrativo.getIdAdministrativo());
            stmt.setInt(2, administrativo.getIdUsuario());


            int res = stmt.executeUpdate();
            System.out.println("adicionarEndereco.res=" + res);
            return administrativo;
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

            String sql = "DELETE FROM ADMINISTRATIVO WHERE id_administrativo = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerAdministrativoPorId.res=" + res);

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
    public boolean editar(Integer id, Administrativo administrativo) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE administrativo SET id_usuario = ? WHERE id_administrativo = ? \n");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, administrativo.getIdUsuario());
            stmt.setInt(2, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarAdministrativo.res=" + res);

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
    public List<Administrativo> listar() throws BancoDeDadosException {
        List<Administrativo> administrativos = new ArrayList<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM ADMINISTRATIVO A " ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Administrativo administrativo = getAdministrativoFromResultSet(res);
                administrativos.add(administrativo);
            }
            return administrativos;
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

    private Administrativo getAdministrativoFromResultSet(ResultSet res) throws SQLException {
        Administrativo administrativo = new Administrativo();
        administrativo.setIdAdministrativo(res.getInt("id_administrativo"));
        administrativo.setIdUsuario(res.getInt("id_usuario"));
        return administrativo;
    }

}
