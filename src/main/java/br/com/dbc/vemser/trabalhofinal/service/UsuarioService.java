package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dtos.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;


@org.springframework.stereotype.Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ObjectMapper objectMapper) {
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        try {
            return objectMapper.convertValue(usuarioRepository.adicionar(validarUsuario(objectMapper.convertValue(usuario, Usuario.class))), UsuarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            verificarSeExiste(id);
            usuarioRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        try {
            Usuario usarioEditar = objectMapper.convertValue(usuario, Usuario.class);
            usarioEditar.setIdUsuario(id);
            return objectMapper.convertValue(usuarioRepository.editar(id, validarUsuario(usarioEditar)), UsuarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }


    public List<UsuarioDTO> listar() throws RegraDeNegocioException {
        try {
            return usuarioRepository.listar().stream().map(usuario -> { return objectMapper.convertValue(usuario, UsuarioDTO.class);}).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }

    // Pensando se passamos essa validação pro Banco, afim de ganhar mais performance
    private Usuario validarUsuario(Usuario usuario) throws RegraDeNegocioException {
        try {

            if (usuario.getIdUsuario() != null) {
                verificarSeExiste(usuario.getIdUsuario());
            }

            // Estou iterando com 'fori comum' pois não consigo levantar exceções dentro do forEach. Apesar de poder tratálos...
            List<Usuario> usuarios = usuarioRepository.listar();
            for (int i = 0; i < usuarios.size(); i++) {
                //Quando estivermos atualizando, devemos verifiar se email e cpf já existem em outro usuário ALÉM do que está sendo atualizado.
                if(usuarios.get(i).getCpf().equals(usuario.getCpf()) && (usuario.getIdUsuario()!=null &&
                                                                    !Objects.equals(usuarios.get(i).getIdUsuario(), usuario.getIdUsuario()))){
                    throw new RegraDeNegocioException("Já existe usuário com esse CPF!");
                }
                if (usuarios.get(i).getEmail().equals(usuario.getEmail()) && (usuario.getIdUsuario()!=null &&
                                                                    !Objects.equals(usuarios.get(i).getIdUsuario(), usuario.getIdUsuario()))) {
                    throw new RegraDeNegocioException("Já existe usuário com esse e-mail!");
                }
            }

            return  usuario;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }



    public UsuarioDTO verificarSeExiste(Integer id) throws RegraDeNegocioException{
        try {
            Usuario usuarioAchado = usuarioRepository.listar().stream()
                    .filter(pessoa -> pessoa.getIdUsuario().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
            return objectMapper.convertValue(usuarioAchado, UsuarioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    // Verifica a disponilidade do id_usuario

    public boolean verificarIdUsuario(Integer id) throws RegraDeNegocioException {
        try {
            return usuarioRepository.verificarSeDisponivel(id);
        } catch (BancoDeDadosException e){
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }

    public boolean authUser(String email, String password) throws RegraDeNegocioException {
        try {
            List<Usuario> tempList = usuarioRepository.listar().stream().filter(usuario -> usuario.getEmail().equals(email)
                    && usuario.getSenha().equals(password)).toList();
            if(tempList.size() > 0){
                return true;
            }else{
                return false;
            }
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }
}
