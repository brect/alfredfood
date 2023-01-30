package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.UsuarioDTO;
import com.padawanbr.alfredfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public List<UsuarioDTO> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
    }

}
