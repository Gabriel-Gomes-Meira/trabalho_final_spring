package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDadosDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Agendamento;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final ObjectMapper objectMapper;


    public AgendamentoDTO adicionar(AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        try {
            clienteService.getCliente(agendamentoCreateDTO.getIdCliente());
            medicoService.getMedico(agendamentoCreateDTO.getIdMedico());
            return objectMapper.convertValue(agendamentoRepository.adicionar(
                    objectMapper.convertValue(agendamentoCreateDTO, Agendamento.class)
            ), AgendamentoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        }
//        catch (Exception e) {
//            throw new RegraDeNegocioException("Houve algum erro ao adicionar o agendamento.");
//        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            agendamentoRepository.getAgendamento(id);
            agendamentoRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        }
//        catch (Exception e) {
//            throw new RegraDeNegocioException("Houve algum erro ao remover o agendamento.");
//        }
    }

    public AgendamentoDTO editar(Integer id, AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        try {
            agendamentoRepository.getAgendamento(id);
            clienteService.getCliente(agendamentoCreateDTO.getIdCliente());
            medicoService.getMedico(agendamentoCreateDTO.getIdMedico());

            return objectMapper.convertValue(agendamentoRepository.editar(id,
                    objectMapper.convertValue(agendamentoCreateDTO, Agendamento.class)
            ), AgendamentoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        }
//        catch (Exception e) {
//            throw new RegraDeNegocioException("Houve algum erro ao editar o agendamento.");
//        }
    }

    public List<AgendamentoDTO> listar() throws RegraDeNegocioException {
        try {
            return agendamentoRepository.listar().stream().map(agendamento ->
                    objectMapper.convertValue(agendamento, AgendamentoDTO.class)).toList();
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        } catch (Exception e) {
            throw new RegraDeNegocioException("Houve algum erro ao listar os agendamentos.");
        }
    }

    public  List<AgendamentoDadosDTO> listarPorMedico(Integer idMedico) throws RegraDeNegocioException {
        try {
            return agendamentoRepository.mostrarAgendamentosUsuario(idMedico, TipoUsuario.MEDICO);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }

    public  List<AgendamentoDadosDTO> listarPorCliente(Integer idCliente) throws RegraDeNegocioException {
        try {
            return agendamentoRepository.mostrarAgendamentosUsuario(idCliente, TipoUsuario.CLIENTE);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }


    public Agendamento getAgendamento(Integer id) throws RegraDeNegocioException {
        try {
            return agendamentoRepository.getAgendamento(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }
    public AgendamentoDTO getAgendamentoDTO(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getAgendamento(id), AgendamentoDTO.class);
    }

}
