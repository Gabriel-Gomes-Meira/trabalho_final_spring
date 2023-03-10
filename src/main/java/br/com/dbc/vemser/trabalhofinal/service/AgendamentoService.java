package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Agendamento;
import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;

import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final ObjectMapper objectMapper;


    public AgendamentoDTO adicionar(AgendamentoDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        try {
            clienteService.getCliente(agendamentoCreateDTO.getIdCliente());
            medicoService.getMedico(agendamentoCreateDTO.getIdMedico());
            return objectMapper.convertValue(agendamentoRepository.adicionar(agendamentoCreateDTO), AgendamentoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Houve algum erro ao adicionar o agendamento.");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            agendamentoRepository.getAgendamento();
            agendamentoRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Houve algum erro ao remover o agendamento.");
        }
    }

    public AgendamentoDTO editar(Integer id, AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        try {
            agendamentoRepository.getAgendamento();
            clienteService.getCliente(agendamentoCreateDTO.getIdCliente());
            medicoService.getMedico(agendamentoCreateDTO.getIdMedico());
            return objectMapper.convertValue(agendamentoRepository.editar(id, agendamentoCreateDTO), AgendamentoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Houve algum erro ao editar o agendamento.");
        }
    }

    public List<AgendamentoDTO> listar() throws RegraDeNegocioException {
        try {
            return agendamentoRepository.listar();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Houve algum erro ao listar os agendamentos.");
        }
    }

    public AgendamentoDTO getAgendamento(Integer id) throws RegraDeNegocioException {
        try {
            return agendamentoRepository.getAgendamento(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Houve algum erro ao listar os agendamentos.");
        }
    }

}
