package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.CidadeDTO;
import com.padawanbr.alfredfood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionModel(List<Cidade> cidade) {
        return cidade.stream().map(estado -> toModel(estado)).collect(Collectors.toList());
    }

}
