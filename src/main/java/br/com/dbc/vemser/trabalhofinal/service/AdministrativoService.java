package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.repository.AdministrativoRepository;
import com.dbc.exceptions.BancoDeDadosException;
import com.dbc.exceptions.ValorDeEntradaException;

public class AdministrativoService implements Service<Integer, Administrativo> {

    private AdministrativoRepository administrativoRepository;

    public AdministrativoService() {
        administrativoRepository = new AdministrativoRepository();
    }

    @Override
    public void adicionar(Administrativo administrativo) {
        try {
            Administrativo administrativoAdicionado = administrativoRepository.adicionar(administrativo);
            System.out.println("Administrativo adicionado com sucesso!" + administrativoAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Integer id) {

        try {
            boolean conseguiuRemover = administrativoRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editar(Integer id, Administrativo administrativo) {
        try {
            boolean conseguiuEditar = administrativoRepository.editar(id, administrativo);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException | ValorDeEntradaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() {
        try {
            administrativoRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }


}
