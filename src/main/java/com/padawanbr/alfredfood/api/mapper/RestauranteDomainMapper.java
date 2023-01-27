package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.RestauranteRequest;
import com.padawanbr.alfredfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteRequest request) {
        return modelMapper.map(request, Restaurante.class);
    }


}
