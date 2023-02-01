package com.padawanbr.alfredfood.core.modelmapper;

import com.padawanbr.alfredfood.api.model.request.ItemPedidoRequest;
import com.padawanbr.alfredfood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoRequest.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
