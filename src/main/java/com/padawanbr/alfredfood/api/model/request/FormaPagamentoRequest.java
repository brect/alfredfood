package com.padawanbr.alfredfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FormaPagamentoRequest {

    @NotBlank
    private String descricao;

}
