package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.repository.AdministrativoRepository;

import java.util.List;

public class AdministrativoService {

    private AdministrativoRepository administrativoRepository;

    public AdministrativoService() {
        administrativoRepository = new AdministrativoRepository();
    }

    public void adicionar(Administrativo administrativo) {
        try {
            Administrativo administrativoAdicionado = administrativoRepository.adicionar(administrativo);
            System.out.println("Administrativo adicionado com sucesso!" + administrativoAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void remover(Integer id) {

        try {
            boolean conseguiuRemover = administrativoRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }

    }

    public void editar(Integer id, Administrativo administrativo) {
        try {
//            boolean conseguiuEditar =
            administrativoRepository.editar(id, administrativo);
//            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        try {
            administrativoRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }


}
