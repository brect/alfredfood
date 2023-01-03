package com.padawanbr.alfredfood.domain.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EntidadeEmUsoException extends RuntimeException {

    public EntidadeEmUsoException(String mensagem) {

    }
}
