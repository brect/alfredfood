package com.padawanbr.alfredfood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {

    CRIADO("criado"),
    CONFIRMADO("confirmado", CRIADO),
    ENTREGUE("entregue", CONFIRMADO),
    CANCELADO("cancelado", CRIADO, CONFIRMADO);

    private String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido ... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean verificaAlteracaoPara(StatusPedido statusPedido){
        return !statusPedido.statusAnteriores.contains(this);
    }
}