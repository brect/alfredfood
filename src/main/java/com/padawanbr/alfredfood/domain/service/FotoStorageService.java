package com.padawanbr.alfredfood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    default void atualizar(NovaFoto novaFoto, String nomeArquivoExistente) {
        this.armazenar(novaFoto);
        if (nomeArquivoExistente != null) {
            this.remover(nomeArquivoExistente);
        }
    }

    void remover(String nomeArquivo);

    default String getNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString().concat("_").concat(nomeOriginal);
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
