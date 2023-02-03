package com.padawanbr.alfredfood.api.model.response;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Setter
public class PedidoResumidoDTO {
    
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteSimplificadoDTO restaurante;
    private UsuarioDTO cliente;

}
