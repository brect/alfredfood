package com.padawanbr.alfredfood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeRequset {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIDRequest estado;

}
