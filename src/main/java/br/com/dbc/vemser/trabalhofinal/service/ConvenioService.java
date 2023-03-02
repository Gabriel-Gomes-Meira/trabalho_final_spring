package com.dbc.service;

import br.com.dbc.vemser.trabalhofinal.entity.Administrativo;
import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import br.com.dbc.vemser.trabalhofinal.service.Service;
import com.dbc.exceptions.BancoDeDadosException;
import com.dbc.repository.ConvenioRepository;


public class ConvenioService implements Service<Integer, Convenio> {
    private ConvenioRepository convenioRepository;

    public ConvenioService() {
        convenioRepository = new ConvenioRepository();
    }

    @Override
    public void adicionar(Convenio convenio) {
        try {
            Convenio enderecoAdicionado = convenioRepository.adicionar(convenio);
            System.out.println("Convênio adicinado com sucesso! " + enderecoAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = convenioRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(Integer id, Convenio convenio) {
        try {
            boolean conseguiuEditar = convenioRepository.editar(id, convenio);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() {
        try {
            convenioRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }


}
