package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ConvenioRepository implements Repositorio<Integer, Convenio> {


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
            log.error(e.getMessage());
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Convenio adicionar(Convenio convenio) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            convenio.setIdConvenio(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO CONVENIO (id_convenio, cadastro_orgao_regulador, taxa_abatimento) " +
                    "values (?, ?, ?)");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, convenio.getIdConvenio());
            stmt.setString(2, convenio.getCadastroOragaoRegulador());
            stmt.setDouble(3, convenio.getTaxaAbatimento());

            stmt.executeUpdate();

            return convenio;
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
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CONVENIO WHERE ID_CONVENIO = ?";

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
            }
        }
    }

    public void remover2(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CONVENIO WHERE ID_CONVENIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            stmt.executeUpdate();

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
            }
        }
    }

    @Override
    public Convenio editar(Integer id, Convenio convenio) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE CONVENIO SET cadastro_orgao_regulador = ?, taxa_abatimento = ? where id_convenio = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

//            StringBuilder sql = new StringBuilder();
//            sql.append("UPDATE CONVENIO SET ");
//
//            if (convenio.getTaxaAbatimento() != null) {
//                sql.append(" taxa_abatimento = ?,");
//            }
//            if (convenio.getCadastroOragaoRegulador()!= null) {
//                sql.append(" cadastro_orgao_regulador = ?,");
//            }
//
//            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
//            sql.append(" where id_convenio = ?");
//            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            stmt.setString(index++, convenio.getCadastroOragaoRegulador());
            stmt.setDouble(index++, convenio.getTaxaAbatimento());
            stmt.setInt(index, id);

//            if (convenio.getCadastroOragaoRegulador() != null) {
//                stmt.setString(index++, convenio.getCadastroOragaoRegulador());
//            }
//            if (convenio.getTaxaAbatimento() != null) {
//                stmt.setDouble(index++, convenio.getTaxaAbatimento());
//            }

//            int res = stmt.executeUpdate();
            convenio.setIdConvenio(id);

            stmt.executeUpdate();

            return convenio;
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
    public List<Convenio> listar() throws BancoDeDadosException {
        List<Convenio> convenios = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM CONVENIO ";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Convenio convenio = getConvenioFromResultSet(res);
                convenios.add(convenio);
            }
            return convenios;
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

    private Convenio getConvenioFromResultSet(ResultSet res) throws SQLException {
        Convenio convenio = new Convenio();
        convenio.setIdConvenio(res.getInt("id_convenio"));
        convenio.setTaxaAbatimento(res.getDouble("taxa_abatimento"));
        convenio.setCadastroOragaoRegulador(res.getString("cadastro_orgao_regulador"));
        return convenio;
    }

    public Convenio getUmId(Integer id) throws BancoDeDadosException {
        Convenio convenio = null;
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * " +
                    " FROM CONVENIO WHERE id_convenio = "+ id ;

            PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                convenio = getConvenioFromResultSet(res);
            }

            return convenio;
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
