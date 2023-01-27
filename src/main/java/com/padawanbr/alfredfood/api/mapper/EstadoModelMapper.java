package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.EstadoDTO;
import com.padawanbr.alfredfood.api.model.response.RestauranteDTO;
import com.padawanbr.alfredfood.domain.model.Estado;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDTO toModel(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
        return estados.stream().map(estado -> toModel(estado)).collect(Collectors.toList());
    }


}
