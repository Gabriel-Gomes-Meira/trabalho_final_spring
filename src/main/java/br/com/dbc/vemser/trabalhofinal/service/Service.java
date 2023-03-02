package br.com.dbc.vemser.trabalhofinal.service;


public interface Service<CHAVE, OBJETO>{


    void adicionar(OBJETO object);

    void remover(CHAVE id);

    void editar(CHAVE id, OBJETO object);

    void listar();

   // boolean validarEntradas(OBJETO object) throws ValorDeEntradaException;

}
