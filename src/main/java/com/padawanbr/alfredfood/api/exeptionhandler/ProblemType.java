package com.padawanbr.alfredfood.api.exeptionhandler;


import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem Incopreensivel", "/mensagem-incopreensivel"),
    BUSSINESS_EXCEPTION("Violação de regra de negócio", "/erro-negocio"),
    ENTIDADE_EM_USO("Entidade está em uso", "/entidade-em-uso");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://alfredfood.com.br/" + path;
    }

}