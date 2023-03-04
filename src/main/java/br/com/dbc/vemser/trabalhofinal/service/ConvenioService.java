package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;

import java.util.List;


public class ConvenioService {
    private ConvenioRepository convenioRepository;

    public ConvenioService() {
        convenioRepository = new ConvenioRepository();
    }

    public void adicionar(Convenio convenio) {
        try {
            Convenio enderecoAdicionado = convenioRepository.adicionar(convenio);
            System.out.println("Convênio adicinado com sucesso! " + enderecoAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = convenioRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void editar(Integer id, Convenio convenio) {
        try {
//            boolean conseguiuEditar =
            convenioRepository.editar(id, convenio);
//            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        try {
            convenioRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }


}
