package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.FotoProdutoDTO;
import com.padawanbr.alfredfood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }

    public List<FotoProdutoDTO> toCollectionModel(Collection<FotoProduto> fotoProdutos) {
        return fotoProdutos.stream()
                .map(fotoProduto -> toModel(fotoProduto))
                .collect(Collectors.toList());
    }

}
