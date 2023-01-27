package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.CozinhaRequest;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import com.padawanbr.alfredfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaRequest request) {
        return modelMapper.map(request, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaRequest request, Cozinha cozinha){
        modelMapper.map(request, cozinha);
    }

}
