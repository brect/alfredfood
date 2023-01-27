package com.padawanbr.alfredfood.api.model.response;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoDTO estado;

}
