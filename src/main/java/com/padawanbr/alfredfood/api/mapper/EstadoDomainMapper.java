package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.EstadoRequest;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoRequest request) {
        return modelMapper.map(request, Estado.class);
    }

    public void copyToDomainObject(EstadoRequest request, Estado estado){
        modelMapper.map(request, estado);
    }

}
