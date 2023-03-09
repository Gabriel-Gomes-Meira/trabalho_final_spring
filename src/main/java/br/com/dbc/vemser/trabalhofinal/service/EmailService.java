package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.entity.Usuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailService {

    private final Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

//    public void sendSimpleMessage() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        helper.setTo("");
//        message.setSubject("E-mail Simples");
//        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
//        emailSender.send(message);
//    }

//    public void sendWithAttachment() throws MessagingException, FileNotFoundException {
//        MimeMessage message = emailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(message,
//                true);
//
//        helper.setFrom(from);
//        helper.setTo("");
//        helper.setSubject("E-mail com Anexo");
//        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");
//
//        File file1 = ResourceUtils.getFile("classpath:imagem.jpg");
//        //File file1 = new File("imagem.jpg");
//        FileSystemResource file
//                = new FileSystemResource(file1);
//        helper.addAttachment(file1.getName(), file);
//
//        emailSender.send(message);
//    }

    public MimeMessageHelper buildEmail(String email, String subject) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        return mimeMessageHelper;
    }

    public void sendEmail(Usuario usuario) throws MessagingException, TemplateException, IOException {
            MimeMessageHelper mimeMessageHelper = buildEmail(usuario.getEmail(), "Criação de Usuáiro");
            mimeMessageHelper.setText(getUsuarioTemplate(usuario.getNome()), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
    }

//    public void sendEmail(ClienteDTO cliente, MedicoDTO medico) throws IOException {
//        MimeMessageHelper mimeMessageHelper = buildEmail(cliente.getUsuarioDTO().getEmail(), "Agendamento Marcado");
//        mimeMessageHelper.setText(getAgendamentoTemplate(cliente.getUsuarioDTO().getNome(),
//                "Mensagem da marcação"), true);
//        emailSender.send(mimeMessageHelper.getMimeMessage());
//    }
//
//    public void sendEmail(MedicoDTO medico, ClienteDTO cliente) {
//        MimeMessageHelper mimeMessageHelper = buildEmail(medico.getUsuarioDTO().getEmail(), "Novo Agendamento");
//        mimeMessageHelper.setText(getAgendamentoTemplate(medico.getUsuarioDTO().getNome(),
//                "Mensagem da marcação"), true);
//        emailSender.send(mimeMessageHelper.getMimeMessage());
//    }

    public String getUsuarioTemplate(String nome) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", nome);
        dados.put("email", from);

        Template template = fmConfiguration.getTemplate("email-criacao-usuario.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

//    public String getAgendamentoTemplate(String nome, String message) throws IOException, TemplateException {
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", cliente.getUsuarioDTO().getNome());
//        dados.put("message", message);
//        Template template = fmConfiguration.getTemplate("email-agenadmento.ftl");
//        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
//    }

}
