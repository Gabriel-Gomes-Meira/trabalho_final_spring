package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.entity.Medico;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.repository.AdministrativoRepository;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.dbc.exceptions.BancoDeDadosException;

import com.dbc.repository.MedicoRepository;

import java.util.List;

public class UsuarioService implements Service<Integer, Usuario> {

    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
    }

    @Override
    public void adicionar(Usuario usuario) {
        try {
            Usuario usuarioAdicionado = usuarioRepository.adicionar(usuario);
            System.out.println("Usuário adicinado com sucesso! " + usuarioAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = usuarioRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Integer id, Usuario usuario) {
        try {
            boolean conseguiuEditar = usuarioRepository.editar(id, usuario);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() {
        try {
            usuarioRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public Usuario findUser(String email, String password) {
        try {
            List<Usuario> tempList = usuarioRepository.listar().stream().filter(usuario -> usuario.getEmail().equals(email)
                    && usuario.getSenha().equals(password)).toList();
            if(tempList.size() > 0){
                return tempList.get(0);
            }else{
                return null;
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verificarIdUsuario(Integer id) {

        AdministrativoRepository administrativoRepository = new AdministrativoRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ClienteRepository clienteRepository = new ClienteRepository();

        try {
            List<Administrativo> tempListAdministrativos = administrativoRepository.listar().stream().filter(administrativo -> administrativo.getIdUsuario() == id).toList();
            List<Medico> tempListMedicos = medicoRepository.listar().stream().filter(medico -> medico.getIdUsuario() == id).toList();
            List<Cliente> tempListClientes = clienteRepository.listar().stream().filter(cliente -> cliente.getIdUsuario().equals(id)).toList();


            return (tempListAdministrativos.size() + tempListMedicos.size() + tempListClientes.size()) > 0;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            return false;
        }
    }

}
