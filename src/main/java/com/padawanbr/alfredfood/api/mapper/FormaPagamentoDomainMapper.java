package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.FormaPagamentoRequest;
import com.padawanbr.alfredfood.api.model.respos.FormaPagamentoDTO;
import com.padawanbr.alfredfood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoDomainMapper {

    @Autowired
    private ModelMapper modelMapper;


    public FormaPagamento toDomainObject(FormaPagamentoRequest request) {
        return modelMapper.map(request, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoRequest request, FormaPagamento formaPagamento) {
        modelMapper.map(request, formaPagamento);
    }
}
