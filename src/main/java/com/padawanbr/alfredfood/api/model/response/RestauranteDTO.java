package com.padawanbr.alfredfood.api.model.response;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteDTO {

    @JsonView({RestauranteView.Resumo.class})
    private Long id;

    @JsonView({RestauranteView.Resumo.class})
    private String nome;

    @JsonView({RestauranteView.Resumo.class})
    private BigDecimal taxaFrete;

    @JsonView({RestauranteView.Resumo.class})
    private CozinhaDTO cozinha;

    private Boolean ativo;

    private Boolean aberto;

    private EnderecoDTO endereco;
}
