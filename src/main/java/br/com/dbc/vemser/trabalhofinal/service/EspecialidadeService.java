package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Especialidade;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;

import java.util.List;


public class EspecialidadeService {
    private EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService() {
        especialidadeRepository = new EspecialidadeRepository();
    }

    // criação de um objeto
    public void adicionar(Especialidade especialidade) {
        try {
            Especialidade especialidadeAdicionado = especialidadeRepository.adicionar(especialidade);
            System.out.println("contato adicinado com sucesso! " + especialidadeAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // remoção
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = especialidadeRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }


    // atualização de um objeto
    public void editar(Integer id, Especialidade especialidade) {
        try {
//            boolean conseguiuEditar =
            especialidadeRepository.editar(id, especialidade);
//            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    // leitura
    public List<Usuario> listar() {
        try {
            especialidadeRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }

}
