<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Agendamento editado</title>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" bgcolor="#80aaff"
            style="background-color: #80aaff;"><br><strong><em><span style="color: #ffffff">Safety Soft</span></em></strong><br>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="top" bgcolor="#ffffff"
                        style="background-color: #ffffff; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 10px 15px;">
                    <h2> Olá ${agendamento.getCliente()}, </h2>
                    <p>
                        Um agendamento seu foi editado. Segue as novas informações: <br>
                        <strong>ID do agendamento: ${agendamento.getIdAgendamento()}</strong><br>
                        <strong>Data e horário: ${agendamento.getDataHorario()} <strong>
                        Médico que lhe atenderá: ${agendamento.getMedico()} <br>
                        Qualquer dúvida é só contatar o suporte pelo e-mail ${email}<br>
                        Att,<br>
                        Safety Corp.<br>
                    </p>
                    </td>
                </tr>
            </table> <br> <br></td>
    </tr>
</table>

</body>
</html>