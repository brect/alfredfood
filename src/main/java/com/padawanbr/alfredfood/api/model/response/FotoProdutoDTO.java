package com.padawanbr.alfredfood.api.model.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class FotoProdutoDTO {

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

}
