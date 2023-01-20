package com.padawanbr.alfredfood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";

    public EstadoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public EstadoNaoEncontradoException(Long idEstado) {
        this(String.format(MSG_ESTADO_NAO_ENCONTRADO, idEstado));
    }
}
