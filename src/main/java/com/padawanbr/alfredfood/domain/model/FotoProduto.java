package com.padawanbr.alfredfood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "produto_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;


    public Long getRestauranteId() {
        if (getProduto() != null) {
           return getProduto().getRestaurante().getId();
        }
        return null;
    }

    public Long getProdutoId() {
        if (getProduto() != null) {
           return getProduto().getId();
        }
        return null;
    }
}