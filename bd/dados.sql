INSERT INTO CONTATO (id_contato, telefone1, telefone2, telefone3) VALUES (seq_contato.nextval, '27999556241', '27989037606', '27290250274');

INSERT INTO ENDERECO (id_endereco, logradouro, numero, bairro, cidade, estado, cep, complemento) VALUES (seq_endereco.nextval, 'Avenida Presidente Vargas', '180', 'Centro', 'Domingos Martins', 'Espírito Santo', '29260971', 'Ap 101');

INSERT INTO USUARIO (id_usuario, id_endereco, id_contato, nome, cpf, email, senha, tipo) VALUES (seq_usuario.nextval, 1, 1, 'Yago Miguel João Farias', '01572557788', 'yago_miguel_farias@gsw.com.br', 'yagomiguel7788', '1');

INSERT INTO ADMINISTRATIVO (id_administrativo, id_usuario) VALUES (seq_administrativo.nextval, 1);



INSERT INTO CONTATO (id_contato, telefone1, telefone2, telefone3) VALUES (seq_contato.nextval, '27991529907', '27988437052', '2735180223');

INSERT INTO ENDERECO (id_endereco, logradouro, numero, bairro, cidade, estado, cep, complemento) VALUES (seq_endereco.nextval, 'Praça da Estação', '16', 'Vale da estação', 'Domingos Martins', 'Espírito Santo', '29260971', 'sn');

INSERT INTO USUARIO (id_usuario, id_endereco, id_contato, nome, cpf, email, senha, tipo) VALUES (seq_usuario.nextval, 2, 2, 'Luzia Jennifer Fátima Melo', '41624497756', 'luzia_jennifer_melo@mega.com.br', 'luziajennifer7756', '3');


INSERT INTO CONVENIO (id_convenio, cadastro_orgao_regulador, taxa_abatimento) VALUES (seq_convenio.nextval, '357391', 5.2);


INSERT INTO CLIENTE (id_cliente, id_convenio, id_usuario) VALUES (seq_cliente.nextval, 1, 2);


INSERT INTO CONTATO (id_contato, telefone1, telefone2, telefone3) VALUES (seq_contato.nextval, '27998553412', '27989039765', '27991250213');

INSERT INTO ENDERECO (id_endereco, logradouro, numero, bairro, cidade, estado, cep, complemento) VALUES (seq_endereco.nextval, 'Avenida Presidente Vargas', '181', 'Centro', 'Domingos Martins', 'Espírito Santo', '29260971', 'Casa');

INSERT INTO USUARIO (id_usuario, id_endereco, id_contato, nome, cpf, email, senha, tipo) VALUES (seq_usuario.nextval, 3, 3, 'Caroline Pietra Melissa Rocha', '56162690776', 'carolinepietrarocha@ferplast.com.br', 'carolinepietrarocha0776', '2');


INSERT INTO ESPECIALIDADE (id_especialidade, nome, valor) VALUES (seq_especialidade.nextval, 'Clínico geral', 500.00);

INSERT INTO MEDICO (id_medico, id_usuario, id_especialidade, crm) VALUES (seq_medico.nextval, 3, 1, 'CRM-ES123456');

INSERT INTO AGENDAMENTO (id_agendamento, id_medico, id_cliente, data_horario) VALUES (seq_agendamento.nextval, 1, 1, TO_DATE('2023-02-28 15:00','yyyy/mm/dd hh24:mi'));

-- Supondo que foi consultado:
UPDATE AGENDAMENTO SET exame = 'Sangue', tratamento = 'Dipirona' WHERE id_agendamento = 1;
