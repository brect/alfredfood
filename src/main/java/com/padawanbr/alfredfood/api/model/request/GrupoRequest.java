package com.padawanbr.alfredfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GrupoRequest {

    @NotBlank
    private String nome;

}
