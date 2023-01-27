package com.padawanbr.alfredfood.api.mapper;

import com.padawanbr.alfredfood.api.model.request.CidadeRequset;
import com.padawanbr.alfredfood.api.model.request.EstadoRequest;
import com.padawanbr.alfredfood.domain.model.Cidade;
import com.padawanbr.alfredfood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDomainMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeRequset request) {
        return modelMapper.map(request, Cidade.class);
    }

    public void copyToDomainObject(CidadeRequset request, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
        cidade.setEstado(new Estado());

        modelMapper.map(request, cidade);
    }
}
