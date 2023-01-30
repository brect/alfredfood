package com.padawanbr.alfredfood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um cadastro de grupo com código %d";

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format(MSG_GRUPO_NAO_ENCONTRADO, id));
    }
}
