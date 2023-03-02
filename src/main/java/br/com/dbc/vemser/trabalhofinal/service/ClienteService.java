package com.dbc.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.entity.Cliente;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.service.Service;
import com.dbc.exceptions.BancoDeDadosException;

import java.util.HashMap;
import java.util.Map;


public class ClienteService implements Service<Integer, Cliente> {
    private ClienteRepository clienteRepository;

    public ClienteService() {
        clienteRepository = new ClienteRepository();
    }

    @Override
    public void adicionar(Cliente cliente) {
        try {
            Cliente clienteAdicionado = clienteRepository.adicionar(cliente);
            System.out.println("Cliente adicinado com sucesso! " + clienteAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = clienteRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Integer id, Cliente cliente) {
        try {
            boolean conseguiuEditar = clienteRepository.editar(id, cliente);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() {
        try {
            clienteRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void mostrarInformacoesClienteUsuario(Usuario usuarioAtivo){
        try {
            HashMap<String,String> informacoes = clienteRepository.mostrarInformacoesClienteUsuario(usuarioAtivo);
            for (Map.Entry<String, String> set : informacoes.entrySet()) {
                System.out.println(set.getKey() + " "
                        + set.getValue());
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
