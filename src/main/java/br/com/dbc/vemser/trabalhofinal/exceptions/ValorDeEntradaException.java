package com.dbc.exceptions;

import java.util.InputMismatchException;

public class ValorDeEntradaException extends InputMismatchException {

    public ValorDeEntradaException(String mensagem) {
        super(mensagem);
    }
}
