INSERT INTO USUARIO (id_usuario, endereco, contatos, nome, cpf, email, senha, tipo) VALUES (seq_usuario.nextval, 'CEP: 64004-805, Rua: Rua Caldeirão, Bairro: Olarias, Cidade: Teresina, Estado: PI', '(33) 99943-5687', 'Yago Miguel João Farias', '01572557788', 'yago_miguel_farias@gsw.com.br', 'yagomiguel7788', '1');

INSERT INTO ADMINISTRATIVO (id_administrativo, id_usuario) VALUES (seq_administrativo.nextval, 1);


INSERT INTO USUARIO (id_usuario, endereco, contatos, nome, cpf, email, senha, tipo) VALUES (seq_usuario.nextval, 'CEP:24474-170, Endereço: Rua Garcia Rezende, Bairro: Trindade, Cidade: São Gonçalo, Estado:RJ', '(31) 99913-5647', 'Luzia Jennifer Fátima Melo', '41624497756', 'luzia_jennifer_melo@mega.com.br', 'luziajennifer7756', '3');

INSERT INTO CONVENIO (id_convenio, cadastro_orgao_regulador, taxa_abatimento) VALUES (seq_convenio.nextval, '357391', 5.2);

INSERT INTO CLIENTE (id_cliente, id_convenio, id_usuario) VALUES (seq_cliente.nextval, 1, 2);


INSERT INTO USUARIO (id_usuario, endereco, contatos, nome, cpf, email, senha, tipo) VALUES (seq_usuario.nextval, 'CEP:75901-133, Endereço: Rua Orozimbo Veloso de Godoy,Bairro: Setor Central,Cidade: Rio Verde, Estado: GO', '(27) 98815-5061', 'Caroline Pietra Melissa Rocha', '56162690776', 'carolinepietrarocha@ferplast.com.br', 'carolinepietrarocha0776', '2');

INSERT INTO ESPECIALIDADE (id_especialidade, nome, valor) VALUES (seq_especialidade.nextval, 'Clínico geral', 500.00);

INSERT INTO MEDICO (id_medico, id_usuario, id_especialidade, crm) VALUES (seq_medico.nextval, 3, 1, 'CRM-ES123456');

INSERT INTO AGENDAMENTO (id_agendamento, id_medico, id_cliente, data_horario) VALUES (seq_agendamento.nextval, 1, 1, TO_DATE('2023-02-28 15:00','yyyy/mm/dd hh24:mi'));

-- Supondo que foi consultado:
UPDATE AGENDAMENTO SET exame = 'Sangue', tratamento = 'Dipirona' WHERE id_agendamento = 1;

