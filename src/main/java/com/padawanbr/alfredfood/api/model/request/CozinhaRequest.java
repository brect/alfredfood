package com.padawanbr.alfredfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CozinhaRequest {

    @NotBlank
    private String nome;

}
