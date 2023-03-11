package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.Agendamento;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final UsuarioService usuarioService;


    public AgendamentoDTO adicionar(AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        try {

            AgendamentoDTO novoAgendamento = objectMapper.convertValue(agendamentoRepository.adicionar(
                    objectMapper.convertValue(agendamentoCreateDTO, Agendamento.class)), AgendamentoDTO.class);

            AgendamentoDadosDTO agendamentoARemover = objectMapper.convertValue(novoAgendamento, AgendamentoDadosDTO.class);
            UsuarioDTO usuarioCliente = usuarioService.getById(clienteService.getById(agendamentoARemover.getIdCliente()).getUsuario().getIdUsuario());
            UsuarioDTO usuarioMedico = usuarioService.getById(medicoService.getMedicoDTOById(agendamentoARemover.getIdMedico()).getUsuario().getIdUsuario());

            AgendamentoDadosDTO agendamentoDadosDTO = objectMapper.convertValue(agendamentoARemover, AgendamentoDadosDTO.class);
            agendamentoDadosDTO.setCliente(usuarioCliente.getNome());
            agendamentoDadosDTO.setMedico(usuarioMedico.getNome());

            try {
                emailService.sendEmailAgendamento(usuarioCliente, agendamentoDadosDTO, TipoEmail.AGENDAMENTO_CRIADO_CLIENTE);
                emailService.sendEmailAgendamento(usuarioMedico, agendamentoDadosDTO, TipoEmail.AGENDAMENTO_CRIADO_MEDICO);
            } catch (MessagingException | TemplateException | IOException e) {
                throw new RegraDeNegocioException("Erro ao enviar o e-mail!");
            }

            return novoAgendamento;

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            AgendamentoDadosDTO agendamentoARemover = objectMapper.convertValue(getAgendamento(id), AgendamentoDadosDTO.class);
            UsuarioDTO usuarioCliente = usuarioService.getById(clienteService.getById(agendamentoARemover.getIdCliente()).getUsuario().getIdUsuario());
            UsuarioDTO usuarioMedico = usuarioService.getById(medicoService.getMedicoDTOById(agendamentoARemover.getIdMedico()).getUsuario().getIdUsuario());

            agendamentoARemover.setCliente(usuarioCliente.getNome());
            agendamentoARemover.setMedico(usuarioMedico.getNome());

            try {
                agendamentoRepository.remover(id);
                emailService.sendEmailAgendamento(usuarioCliente, agendamentoARemover, TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE);
                emailService.sendEmailAgendamento(usuarioMedico, agendamentoARemover, TipoEmail.AGENDAMENTO_CANCELADO_MEDICO);
            } catch (MessagingException | TemplateException | IOException e) {
                throw new RegraDeNegocioException("Erro ao enviar o e-mail!");
            }

        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        }
    }

    public AgendamentoDTO editar(Integer id, AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        try {
            getAgendamento(id);
            AgendamentoDadosDTO agendamentoEditado = objectMapper.convertValue(agendamentoRepository.editar(id, objectMapper.convertValue(agendamentoCreateDTO, Agendamento.class)), AgendamentoDadosDTO.class);
            UsuarioDTO usuarioCliente = usuarioService.getById(clienteService.getById(agendamentoEditado.getIdCliente()).getUsuario().getIdUsuario());
            UsuarioDTO usuarioMedico = usuarioService.getById(medicoService.getMedicoDTOById(agendamentoEditado.getIdMedico()).getUsuario().getIdUsuario());

            agendamentoEditado.setCliente(usuarioCliente.getNome());
            agendamentoEditado.setMedico(usuarioMedico.getNome());

            try {
                emailService.sendEmailAgendamento(usuarioCliente, agendamentoEditado, TipoEmail.AGENDAMENTO_EDITADO_CLIENTE);
                emailService.sendEmailAgendamento(usuarioMedico, agendamentoEditado, TipoEmail.AGENDAMENTO_EDITADO_MEDICO);
            } catch (MessagingException | TemplateException | IOException e) {
                throw new RegraDeNegocioException("Erro ao enviar o e-mail!");
            }

            return objectMapper.convertValue(agendamentoEditado, AgendamentoDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados.");
        }
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
            Agendamento agendamento = agendamentoRepository.getAgendamento(id);
            if(agendamento == null){
                throw new RegraDeNegocioException("Agendamento não encontrado.");
            }
            return agendamento;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }
    public AgendamentoDTO getAgendamentoDTO(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getAgendamento(id), AgendamentoDTO.class);
    }
}
