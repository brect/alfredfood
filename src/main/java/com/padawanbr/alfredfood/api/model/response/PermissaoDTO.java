package com.padawanbr.alfredfood.api.model.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidoFilter")
@Setter
@Getter
public class PermissaoDTO {

    private Long id;
    private String nome;
    private String descricao;
}
