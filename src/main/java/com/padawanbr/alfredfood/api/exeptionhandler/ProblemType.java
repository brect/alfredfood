package com.padawanbr.alfredfood.api.exeptionhandler;


import lombok.Getter;

@Getter
public enum ProblemType {

    DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem Incopreensivel", "/mensagem-incopreensivel"),
    PARAMETRO_INVALIDO("Parametro invalido", "/parametro-invalido"),
    BUSSINESS_EXCEPTION("Violação de regra de negócio", "/erro-negocio"),
    ENTIDADE_EM_USO("Entidade está em uso", "/entidade-em-uso");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://alfredfood.com.br/" + path;
    }

}