package com.padawanbr.alfredfood.api.model.response;

import com.padawanbr.alfredfood.domain.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private Long id;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    private EnderecoDTO enderecoEntrega;

    private String status;

    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    private FormaPagamentoDTO formaPagamento;

    private RestauranteSimplificadoDTO restaurante;

    private UsuarioDTO cliente;

    private List<ItemPedidoDTO> itens = new ArrayList<>();

}
