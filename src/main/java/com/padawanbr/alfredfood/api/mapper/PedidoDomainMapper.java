package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.PedidoRequest;
import com.padawanbr.alfredfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoRequest request) {
        return modelMapper.map(request, Pedido.class);
    }

    public void copyToDomainObject(PedidoRequest request, Pedido pedido) {
        modelMapper.map(request, pedido);
    }

}
