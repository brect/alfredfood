package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.RestauranteRequest;
import com.padawanbr.alfredfood.domain.model.Cozinha;
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

    public void copyToDomainObject(RestauranteRequest request, Restaurante restaurante){
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(request, restaurante);
    }

}
