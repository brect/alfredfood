package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.CidadeRequset;
import com.padawanbr.alfredfood.api.model.request.GrupoRequest;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.model.Estado;
import com.padawanbr.alfredfood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GruposDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoRequest request) {
        return modelMapper.map(request, Grupo.class);
    }

    public void copyToDomainObject(GrupoRequest request, Grupo grupo) {
        modelMapper.map(request, grupo);
    }

}
