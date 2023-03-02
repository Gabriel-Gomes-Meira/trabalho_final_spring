package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import com.dbc.exceptions.BancoDeDadosException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements com.dbc.repository.Repositorio<Integer, Usuario> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT seq_usuario.nextval mysequence from DUAL";
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
    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            usuario.setIdUsuario(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO USUARIO\n" +
                    "(id_usuario, cpf, email, nome, senha, tipo,");



            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(") values(?, ?, ?, ?, ?, ?,");
            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(")");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 7;
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getSenha());
            stmt.setInt(6, usuario.getTipoUsuario().getValor());


            int res = stmt.executeUpdate();
            System.out.println("adicionarUsuario.res=" + res);
            return usuario;
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

            String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("removerUsuarioPorId.res=" + res);

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
    public boolean editar(Integer id, Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE USUARIO SET ");

            if (usuario.getCpf()!= null) {
                sql.append(" cpf = ?,");
            }
            if (usuario.getEmail()!= null) {
                sql.append(" email = ?,");
            }
            if (usuario.getNome() != null) {
                sql.append(" nome = ?,");
            }
            if (usuario.getSenha()!= null) {
                sql.append(" senha = ?,");
            }

            if (usuario.getTipoUsuario() != null) {
                sql.append(" tipo = ?,");
            }

            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
            sql.append(" where id_usuario = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if (usuario.getCpf()!= null) {
                stmt.setString(index++, usuario.getCpf());
            }
            if (usuario.getEmail()!= null) {
                stmt.setString(index++, usuario.getEmail());
            }
            if (usuario.getNome() != null) {
                stmt.setString(index++, usuario.getNome());
            }
            if (usuario.getSenha()!= null) {
                stmt.setString(index++, usuario.getSenha());
            }
            if (usuario.getTipoUsuario() != null) {
                stmt.setInt(index++, usuario.getTipoUsuario().getValor());
            }

            stmt.setInt(index, id);
            int res = stmt.executeUpdate();
            System.out.println("editarUsuario.res=" + res);
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
    public List<Usuario> listar() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = com.dbc.repository.ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "       FROM USUARIO " ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario usuario = getUsuarioFromResultSet(res);
                usuarios.add(usuario);
            }
            return usuarios;
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

    private Usuario getUsuarioFromResultSet(ResultSet res) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(res.getInt("id_usuario"));
        usuario.setNome(res.getString("nome"));
        usuario.setCpf(res.getString("cpf"));
        usuario.setEmail(res.getString("email"));
        usuario.setSenha(res.getString("senha"));
        int tipo = res.getInt("tipo");
        if(tipo == 1){
            usuario.setTipoUsuario(TipoUsuario.ADMINISTRATIVO);
        }else if(tipo == 2){
            usuario.setTipoUsuario(TipoUsuario.MEDICO);
        }else {
            usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        }

        return usuario;
    }
}
