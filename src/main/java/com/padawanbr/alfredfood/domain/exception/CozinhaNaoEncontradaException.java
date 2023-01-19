package com.padawanbr.alfredfood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final String MSG_COZINHA_NAO_ENCONTRADO = "Não existe um cadastro de cozinha com código %d";

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long idEstado) {
        this(String.format(MSG_COZINHA_NAO_ENCONTRADO, idEstado));
    }
}
