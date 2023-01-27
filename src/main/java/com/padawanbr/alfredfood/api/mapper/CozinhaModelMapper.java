package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.CozinhaDTO;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream().map(estado -> toModel(estado)).collect(Collectors.toList());
    }
    
}
