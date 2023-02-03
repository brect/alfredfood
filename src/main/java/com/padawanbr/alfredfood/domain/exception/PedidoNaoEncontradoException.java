package com.padawanbr.alfredfood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String pedidoId) {
        super(String.format("Não existe um pedido com código %d", pedidoId));
    }
}