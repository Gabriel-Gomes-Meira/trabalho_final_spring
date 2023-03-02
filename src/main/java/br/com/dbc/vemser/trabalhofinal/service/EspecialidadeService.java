package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.entity.Especialidade;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;
import com.dbc.exceptions.BancoDeDadosException;


public class EspecialidadeService implements Service<Integer, Especialidade> {
    private EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService() {
        especialidadeRepository = new EspecialidadeRepository();
    }

    @Override
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

    @Override
    // remoção
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = especialidadeRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }


    @Override
    // atualização de um objeto
    public void editar(Integer id, Especialidade especialidade) {
        try {
            boolean conseguiuEditar = especialidadeRepository.editar(id, especialidade);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    // leitura
    public void listar() {
        try {
            especialidadeRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
