package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.response.PedidoDTO;
import com.padawanbr.alfredfood.api.model.response.PedidoResumidoDTO;
import com.padawanbr.alfredfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumidoModelMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumidoDTO toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumidoDTO.class);
    }

    public List<PedidoResumidoDTO> toCollectionModel(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
