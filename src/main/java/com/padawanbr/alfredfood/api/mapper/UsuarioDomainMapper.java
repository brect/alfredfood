package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.UsuarioRequest;
import com.padawanbr.alfredfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioRequest request) {
        return modelMapper.map(request, Usuario.class);
    }

    public void copyToDomainObject(UsuarioRequest request, Usuario grupo) {
        modelMapper.map(request, grupo);
    }

}
