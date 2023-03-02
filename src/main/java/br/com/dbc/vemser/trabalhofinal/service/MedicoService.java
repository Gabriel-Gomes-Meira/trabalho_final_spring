package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.entity.Medico;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.service.Service;
import com.dbc.exceptions.BancoDeDadosException;

import com.dbc.repository.MedicoRepository;

import java.util.HashMap;
import java.util.Map;

public class MedicoService implements Service<Integer, Medico> {
    private MedicoRepository medicoRepository;

    public MedicoService() {
        medicoRepository = new MedicoRepository();
    }

    @Override
    public void adicionar(Medico medico) {
        try {
            Medico medicoAdicionado = medicoRepository.adicionar(medico);
            System.out.println("Cliente adicinado com sucesso! " + medicoAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = medicoRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Integer id, Medico medico) {
        try {
            boolean conseguiuEditar = medicoRepository.editar(id, medico);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() {
        try {
            medicoRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void mostrarInformacoesMedicoUsuario(Usuario usuarioAtivo){
        try {
            HashMap<String,String> informacoes = medicoRepository.mostrarInformacoesMedicoUsuario(usuarioAtivo);
            for (Map.Entry<String, String> set : informacoes.entrySet()) {
                System.out.println(set.getKey() + " "
                        + set.getValue());
            }
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
