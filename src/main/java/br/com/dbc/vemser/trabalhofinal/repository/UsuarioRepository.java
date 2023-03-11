package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UsuarioRepository implements Repositorio<Integer, Usuario> {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT seq_usuario.nextval mysequence from DUAL";
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
    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            usuario.setIdUsuario(proximoId);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO USUARIO (id_usuario, cpf, email, nome, senha, tipo, cep, numero, contatos) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

//            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
//            sql.append(")");
//            sql.deleteCharAt(sql.length() - 1); //remove o ultimo ','
//            sql.append(")");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

//            int index = 7;
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNome());
            stmt.setString(5, usuario.getSenha());
            stmt.setInt(6, usuario.getTipoUsuario().getValor());
            stmt.setString(7, usuario.getCep());
            stmt.setInt(8, usuario.getNumero());
            stmt.setString(9, String.join("\n",usuario.getContatos()));

            stmt.executeUpdate();
            return usuario;
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
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";

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

    @Override
    public Usuario editar(Integer id, Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE USUARIO SET  cpf = ?, email = ?, nome = ?, senha = ?, tipo = ?, cep = ?, numero = ?, contatos = ? where id_usuario = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            int index = 1;
            stmt.setString(index++, usuario.getCpf());
            stmt.setString(index++, usuario.getEmail());
            stmt.setString(index++, usuario.getNome());
            stmt.setString(index++, usuario.getSenha());
            stmt.setInt(index++, usuario.getTipoUsuario().getValor());
            stmt.setString(index++, usuario.getCep());
            stmt.setInt(index++, usuario.getNumero());
            stmt.setString(index++, String.join(String.join("\n", usuario.getContatos())));

            stmt.setInt(index, id);
//            int res = stmt.executeUpdate();
            usuario.setIdUsuario(id);
            return usuario;
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
    public List<Usuario> listar() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
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

    private Usuario getUsuarioFromResultSet(ResultSet res) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(res.getInt("id_usuario"));
            usuario.setNome(res.getString("nome"));
            usuario.setCpf(res.getString("cpf"));
            usuario.setEmail(res.getString("email"));
            usuario.setSenha(res.getString("senha"));
            usuario.setCep(res.getString("cep"));
            usuario.setNumero(res.getInt("numero"));
            usuario.setContatos(res.getString("contatos").split("\n"));
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

    public boolean verificarSeDisponivel(Integer idUsuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT count(*) AS resultado FROM USUARIO u WHERE" +
                    "(" +
                    "NOT EXISTS (SELECT * FROM CLIENTE c  WHERE c.ID_USUARIO  = ?) AND " +
                    "NOT EXISTS (SELECT * FROM MEDICO m WHERE m.ID_USUARIO  = ?)" +
                    ") AND u.ID_USUARIO = ?";

            // Preparar a consulta
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idUsuario);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();
            res.next();
            return res.getInt("resultado") > 0;
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

    public Usuario getUsuario(Integer id) throws BancoDeDadosException {
        Usuario usuario = null;
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * " +
                        " FROM USUARIO WHERE id_usuario = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                usuario = getUsuarioFromResultSet(res);
            }

            return usuario;
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
}
