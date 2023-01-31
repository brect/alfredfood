package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.ProdutoRequest;
import com.padawanbr.alfredfood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoRequest produtoRequest) {
        return modelMapper.map(produtoRequest, Produto.class);
    }

    public void copyToDomainObject(ProdutoRequest produtoRequest, Produto produto) {
        modelMapper.map(produtoRequest, produto);
    }

}
