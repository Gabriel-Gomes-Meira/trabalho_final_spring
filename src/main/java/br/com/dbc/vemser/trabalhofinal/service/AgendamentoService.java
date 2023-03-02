package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.Agendamento;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
import com.dbc.exceptions.BancoDeDadosException;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendamentoService implements Service<Integer, Agendamento> {

    private AgendamentoRepository agendamentoRepository;

    public AgendamentoService() {
        this.agendamentoRepository = new AgendamentoRepository();
    }


    @Override
    public void adicionar(Agendamento agendamento) {
        try {
            Agendamento agendamentoAdicionado = agendamentoRepository.adicionar(agendamento);
            System.out.println("Agendamento adicinado com sucesso! " + agendamentoAdicionado);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = agendamentoRepository.remover(id);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void editar(Integer id, Agendamento agendamento) {
        try {
            boolean conseguiuEditar = agendamentoRepository.editar(id, agendamento);
            System.out.println("editado? " + conseguiuEditar + "| com id=" + id);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listar() {
        try {
            agendamentoRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void mostrarAgendamentosUsuario(Usuario usuarioAtivo){
        try {
            List<HashMap<String,String>> agendamentos = agendamentoRepository.mostrarAgendamentosUsuario(usuarioAtivo);
            for (HashMap<String,String> informacoes: agendamentos) {
                for (Map.Entry<String, String> set : informacoes.entrySet()) {
                    System.out.println(set.getKey() + " "
                            + set.getValue());
                }
            }

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}
