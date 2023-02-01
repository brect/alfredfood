package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.GrupoDTO;
import com.padawanbr.alfredfood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GruposModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDTO toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }

    public List<GrupoDTO> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream().map(grupo -> toModel(grupo)).collect(Collectors.toList());
    }

}
