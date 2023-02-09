package com.padawanbr.alfredfood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    ConsultaFoto recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    class NovaFoto {

        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;

    }

    @Builder
    @Getter
    class ConsultaFoto {

        private String url;
        private InputStream inputStream;

        public boolean containsUrl(){
            return url != null;
        }
        public boolean containsInputStream(){
            return inputStream != null;
        }

    }
}
